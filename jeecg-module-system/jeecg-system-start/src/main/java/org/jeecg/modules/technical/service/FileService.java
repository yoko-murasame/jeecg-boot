package org.jeecg.modules.technical.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.technical.entity.File;
import org.jeecg.modules.technical.entity.Folder;
import org.jeecg.modules.technical.entity.enums.Enabled;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface FileService {

    File upload(MultipartFile multipartFile, Folder folderParam, String customFileName) throws Exception;

    List<File> upload(List<MultipartFile> multipartFiles, String folderId) throws Exception;

    List<File> upload(List<MultipartFile> multipartFiles, Folder folder) throws Exception;

    // 真实删除
    int delete(File file);

    // 逻辑删除
    void changeStatus(String id, Enabled enabled);

    List<File> findByFolder(String folderId);

    Page<File> findPageByFolder(String folderId, int page, int pageSize);

    List<File> findByFileNameAndFolder(String fileName, String folderId);

    List<File> findAllVersion(String fileId, String oldName);

    List<File> findAllVersion(String fileId);

    void reVersion(String fileId);

    File findOne(String fileId);

    void update(File file);

    void updateAllVersion(File file, String oldName);

    void deleteAllVersion(String fileId, boolean logic);

    void deleteAllVersion(String fileId);

    void deleteAllByFolder(String folderId);

    void deleteAllByFolder(String folderId, boolean logic);

    void rename(String fileId, String name);

    Map<String, List<File>> queryTreeLastNodeFilesByFolderTreeNames(Folder folderParams, List<String> folderTreeNames);

    void reTags(String fileId, String tags);
}
