package org.jeecg.modules.technical.controller;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.technical.entity.File;
import org.jeecg.modules.technical.entity.Folder;
import org.jeecg.modules.technical.entity.enums.Enabled;
import org.jeecg.modules.technical.service.FileService;
import org.jeecg.modules.technical.vo.FileRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.jeecg.modules.system.service.impl.SysUploadServiceImpl.FILE_SPLIT;

// import org.jeecg.common.wps.service.WpsFileService;
// import org.jeecg.common.wps.util.Token;

@RestController("technicalFileController")
@RequestMapping("/technical/file")
@Slf4j
@Api(tags = "技术管理-文件")
public class FileController {

    @Autowired
    @Qualifier("TechnicalFileService")
    private FileService fileService;

    @Value("${jeecg.oss.staticDomain}")
    private String staticDomain;

    @Value(value = "${jeecg.path.upload}")
    private String uploadpath;

    @Value(value = "${jeecg.uploadType}")
    private String uploadType;

    // @Autowired
    // private WpsFileService wpsFileService;

    @CrossOrigin
    @ApiOperation("根据树型层级目录名称查询目录的所有文件,树型用','分割,多个树型用';'分割e.g. aaa,bbb;ccc,ddd")
    @RequestMapping(value = "queryTreeLastNodeFilesByFolderTreeNames", method = {RequestMethod.POST, RequestMethod.GET})
    public Result queryTreeLastNodeFilesByFolderTreeNames(@RequestBody Folder folderParams) {
        String[] split = Optional.ofNullable(folderParams.getFolderTreeNames()).orElse("").split(";");
        Map<String, List<File>> folders = fileService.queryTreeLastNodeFilesByFolderTreeNames(folderParams, Arrays.asList(split));
        return Result.OK(folders);
    }


    @PostMapping("upload")
    @ApiOperation("图纸/文档上传")
    public Result upload(@RequestParam(required = true) @ApiParam("文件，支持多个") List<MultipartFile> multipartFiles,
                         @RequestParam(required = true) @ApiParam("关联目录id") String folderId) throws Exception {
        List<File> files = fileService.upload(multipartFiles, folderId);
        return Result.OK(files);
    }

    @PostMapping("files")
    @ApiOperation("按目录查询")
    public Result files(@RequestBody FileRequest request) {
        List<File> files = fileService.findByParams(request);
        return Result.OK(files);
    }

    @GetMapping("history")
    @ApiOperation("查询历史版本")
    public Result history(@RequestParam(required = true) @ApiParam("文件id") String fileId) {
        List<File> files = fileService.findAllVersion(fileId);
        return Result.OK(files);
    }

    @GetMapping("reVersion")
    @ApiOperation("恢复历史版本")
    public Result reVersion(@RequestParam(required = true) @ApiParam("文件id") String fileId) {
        fileService.reVersion(fileId);
        return Result.OK();
    }

    @DeleteMapping("{fileId}")
    @ApiOperation("删除文件所有版本")
    public Result deleteAllVersion(@PathVariable(value = "fileId") @ApiParam("文件id") String fileId) {
        fileService.deleteAllVersion(fileId);
        return Result.OK();
    }

    @DeleteMapping("/version/{fileId}")
    @ApiOperation("删除文件单个版本")
    public Result delete(@PathVariable(value = "fileId") @ApiParam("文件id") String fileId) {
        fileService.changeStatus(fileId, Enabled.DISABLED);
        return Result.OK();
    }

    @DeleteMapping("/deleteAll")
    @ApiOperation("删除多个文件")
    public Result deleteAll(@RequestParam(required = true) @ApiParam("文件id列表") List<String> fileIds) {
        Assert.state(null != fileIds && fileIds.size() > 0, "待删除列表为空");
        for (String fileId : fileIds) {
            fileService.deleteAllVersion(fileId);
        }
        return Result.OK();
    }

    @PutMapping("/rename")
    @ApiOperation("更新文件名")
    public Result rename(@RequestParam(required = true) @ApiParam("文件id") String fileId,
                         @RequestParam(required = true) @ApiParam("新文件名") String name) {
        fileService.rename(fileId, name);
        return Result.OK();
    }

    @PutMapping("/reTags")
    @ApiOperation("更新文件标签")
    public Result reTags(@RequestParam(required = true) @ApiParam("文件id") String fileId,
                         @RequestParam(required = true) @ApiParam("新tags") String tags) {
        fileService.reTags(fileId, tags);
        return Result.OK();
    }

    @GetMapping("/download/{fileId}")
    @ApiOperation("下载")
    public void download(@PathVariable @ApiParam("文件id，请结合<a target='_blank'>标签使用") String fileId,
                         HttpServletResponse response, HttpServletRequest request) throws Exception {
        File file = fileService.findOne(fileId);
        Assert.notNull(file, "文件不存在");
        String url = file.getOssFile().getUrl();
        Assert.state(StringUtils.hasText(url), "文件外链不存在");
        // 处理文件中文字符
        url = getDownloadUrl(url);
        if (url.contains("http")) {
            // 重定向到aliyun下载
            response.sendRedirect(url);
        } else {
            // 转发到本地图片预览&下载接口
            request.getRequestDispatcher("/technical/file/download/static/" + url).forward(request, response);
        }
    }

    /**
     * 预览图片&下载文件
     * 请求地址：/technical/file/download/static/{user/20190119/e1fe9925bc315c60addea1b98eb1cb1349547719_1547866868179.jpg}
     *
     * @param request request
     * @param response response
     * @deprecated 用系统自带的
     */
    @GetMapping(value = "/download/static/**")
    @Deprecated
    public void view(HttpServletRequest request, HttpServletResponse response) {
        // ISO-8859-1 ==> UTF-8 进行编码转换
        String imgPath = extractPathFromPattern(request);
        if (oConvertUtils.isEmpty(imgPath) || "null".equals(imgPath)) {
            return;
        }
        // 其余处理略
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            imgPath = imgPath.replace("..", "");
            if (imgPath.endsWith(",")) {
                imgPath = imgPath.substring(0, imgPath.length() - 1);
            }
            String filePath = uploadpath + FILE_SPLIT + imgPath;
            java.io.File file = new java.io.File(filePath);
            if (!file.exists()) {
                throw new RuntimeException("文件不存在..路径: " + imgPath);
            }
            // 视频流输出
            if (imgPath.contains(".mp4")) {
                // 获取从那个字节开始读取文件
                String rangeString = request.getHeader("Range");
                RandomAccessFile targetFile = new RandomAccessFile(file, "r");
                long fileLength = targetFile.length();
                // 播放
                if (rangeString != null) {
                    long range = Long.parseLong(rangeString.substring(rangeString.indexOf("=") + 1,
                            rangeString.indexOf("-")));
                    // 设置内容类型
                    response.setHeader("Content-Type", "video/mp4");
                    // 设置此次相应返回的数据长度
                    response.setHeader("Content-Length", String.valueOf(fileLength - range));
                    // 设置此次相应返回的数据范围
                    response.setHeader("Content-Range", "bytes " + range + "-" + (fileLength - 1) + "/" + fileLength);
                    // 返回码需要为206，而不是200
                    response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
                    // 设定文件读取开始位置（以字节为单位）
                    targetFile.seek(range);
                } else {// 下载
                    // 设置响应头，把文件名字设置好
                    response.addHeader("Content-Disposition",
                            "attachment;fileName=" + new String(file.getName().getBytes(
                                    StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
                    // 设置文件长度
                    response.setHeader("Content-Length", String.valueOf(fileLength));
                    // 解决编码问题
                    response.setHeader("Content-Type", "application/octet-stream");
                }
                outputStream = response.getOutputStream();
                byte[] cache = new byte[1024 * 300];
                int flag;
                while ((flag = targetFile.read(cache)) != -1) {
                    outputStream.write(cache, 0, flag);
                }
                response.flushBuffer();
            } else {
                // response.setContentType("application/force-download");// 设置强制下载不打开
                // response.addHeader("Content-Disposition", "attachment;fileName=" + new String(file.getName().getBytes(
                //         StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
                inputStream = new BufferedInputStream(Files.newInputStream(Paths.get(filePath)));
                outputStream = response.getOutputStream();
                byte[] buf = new byte[1024];
                int len;
                while ((len = inputStream.read(buf)) > 0) {
                    outputStream.write(buf, 0, len);
                }
                response.flushBuffer();
            }
        } catch (RuntimeException e) {
            response.setStatus(404);
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json; charset=utf-8");
            PrintWriter writer = null;
            try {
                writer = response.getWriter();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            writer.write(JSON.toJSONString(Result.error(e.getMessage())));
            writer.close();
            log.error(e.getMessage());
        } catch (IOException e) {
            response.setStatus(404);
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json; charset=utf-8");
            PrintWriter writer = null;
            try {
                writer = response.getWriter();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            writer.write(JSON.toJSONString(Result.error("预览文件失败" + e.getMessage())));
            writer.close();
            log.error("预览文件失败" + e.getMessage());
            // e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }

    @NotNull
    private String getDownloadUrl(String url) throws UnsupportedEncodingException {
        String fileName = url.substring(url.lastIndexOf("/") + 1, url.length());
        url = url.replace(fileName, "");
        if (!url.contains(this.staticDomain)) {
            // if (StringUtils.hasText(this.staticDomain) && this.staticDomain.contains("http")) {
            //     url = this.staticDomain + url + URLEncoder.encode(fileName, "UTF-8");
            if (CommonConstant.UPLOAD_TYPE_OSS.equals(uploadType) && StringUtils.hasText(this.staticDomain) && this.staticDomain.contains("http")) {
                url = this.staticDomain + (this.staticDomain.lastIndexOf('/') != -1 ? "/" : "") + url + URLEncoder.encode(fileName, "UTF-8");
            } else {
                url = url + URLEncoder.encode(fileName, "UTF-8");
            }
        } else {
            url += URLEncoder.encode(fileName, "UTF-8");
        }
        return url;
    }

    /**
     * wps获取网络文件预览URL
     *
     * @param fileId fileUrl
     * @return t
     */
    /*@GetMapping("getWpsOnlineUrl")
    @ApiOperation(value = "获取wps在线预览链接", notes = "文件大小20M限制，超出时wpsUrl将是下载外链")
    public Result getWpsOnlineUrl(String fileId) throws UnsupportedEncodingException {
        File file = fileService.findOne(fileId);
        Assert.state(file != null && null != file.getOssFile(), "文件不存在");
        //文件大小判断
        boolean isCorrect = OssBootUtil.judgeLimitSize(file.getSize(), 20.0, "MB");
        if (isCorrect) {
            Token t = wpsFileService.getViewUrl(file.getId(), file.getOssFile().getUrl(), true);
            return Result.OK(t);
        } else {
            Token download = new Token();
            download.setToken("文件太大，将直接下载");
            download.setExpires_in(0);
            String downloadUrl = getDownloadUrl(file.getOssFile().getUrl());
            download.setWpsUrl(downloadUrl);
            return Result.OK(download);
        }
    }*/

    /**
     * oss获取网络文件预览URL
     *
     * @param fileId fileUrl
     * @return t
     */
    /*@GetMapping("getALiOSSOnlineUrl")
    @ApiOperation(value = "oss获取网络文件预览URL", notes = "文件大小20M限制，超出时wpsUrl将是下载外链")
    public Result getALiOSSOnlineUrl(String fileId, HttpServletRequest request) throws UnsupportedEncodingException {
        File file = fileService.findOne(fileId);
        Assert.state(file != null && null != file.getOssFile(), "文件不存在");
        Assert.state(StringUtils.hasText(file.getOssFile().getUrl()), "文件外链不存在");
        //文件大小判断
        boolean isCorrect = OssBootUtil.judgeLimitSize(file.getSize(), 20.0, "MB");
        if (isCorrect) {
            Token t = wpsFileService.getViewUrl(file.getId(), file.getOssFile().getUrl(), true);
            return Result.OK(t);
        } else {
            Token download = new Token();
            download.setToken("文件太大，将直接下载");
            download.setExpires_in(0);
            String downloadUrl = getDownloadUrl(file.getOssFile().getUrl());
            download.setWpsUrl(downloadUrl);
            return Result.OK(download);
        }
    }*/

    /*@PostMapping("uploadDirectToAliyunAndGetWpsUrls")
    @ApiOperation("直传aliyun后获取wps预览链接")
    public Result uploadDirectToAliyunAndGetWpsUrls(List<MultipartFile> multipartFiles) throws IOException {
        List<TechnicalUpload> ossFiles = technicalUploadService.uploadAll(multipartFiles, "yoko");
        Result<Object> result = Result.OK();
        if (ossFiles.size() > 0) {
            List<Map<String, Object>> wpsFiles = ossFiles.stream().map(ossFile -> {
                UUID randomUUID = UUID.randomUUID();
                String uuid = randomUUID.toString().replace("-", "");
                Token t = wpsFileService.getViewUrl(uuid, ossFile.getUrl(), true);
                Map<String, Object> wpsFile = new HashMap<String, Object>() {{
                    put("fileName", ossFile.getFileName());
                    put("wpsUrl", t.getWpsUrl());
                    put("token", t.getToken());
                    put("expires_in", t.getExpires_in());
                }};
                return wpsFile;
            }).collect(Collectors.toList());
            result.setResult(wpsFiles);
        }
        return result;
    }*/

    /**
     * 把指定URL后的字符串全部截断当成参数
     * 这么做是为了防止URL中包含中文或者特殊字符（/等）时，匹配不了的问题
     *
     * @param request
     * @return
     */
    private static String extractPathFromPattern(final HttpServletRequest request) {
        String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String bestMatchPattern = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        return new AntPathMatcher().extractPathWithinPattern(bestMatchPattern, path);
    }

}
