package org.jeecg.modules.technical.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static org.jeecg.modules.technical.entity.enums.Type.*;

@Getter
@AllArgsConstructor
public enum Suffix {

    DWG("dwg", ".dwg", "CAD图纸 dwg格式", new Type[]{BLUEPRINT}, true),
    PDF("pdf", ".pdf", "CAD图纸/文档 pdf格式", new Type[]{BLUEPRINT, DOCUMENT}, false),

    RVT("rvt", ".rvt", "BIM模型 rvt格式", new Type[]{MODEL}, true),
    LVT("lvt", ".lvt", "BIM模型 lvt格式", new Type[]{MODEL}, true),
    RFA("rfa", ".rfa", "BIM模型 rfa格式", new Type[]{MODEL}, true),
    SKP("skp", ".skp", "BIM模型 skp格式", new Type[]{MODEL}, true),
    IFC("ifc", ".ifc", "BIM模型 ifc格式", new Type[]{MODEL}, true),
    DGN("dgn", ".dgn", "BIM模型 dgn格式", new Type[]{MODEL}, true),
    OBJ("obj", ".obj", "BIM模型 obj格式", new Type[]{MODEL}, true),
    ThreeDS("3ds", ".3ds", "BIM模型 3ds格式", new Type[]{MODEL}, true),
    DAE("dae", ".dae", "BIM模型 dae格式", new Type[]{MODEL}, true),
    DWF("dwf", ".dwf", "BIM模型 dwf格式", new Type[]{MODEL}, true),
    NWD("nwd", ".nwd", "BIM模型 nwd格式", new Type[]{MODEL}, true),

    DOC("doc", ".doc", "文档 doc格式/word 2003", new Type[]{DOCUMENT}, false),
    DOCX("docx", ".docx", "文档 docx格式/word 2007", new Type[]{DOCUMENT}, false),
    XLS("xls", ".xls", "文档 xls格式/excel 2003", new Type[]{DOCUMENT}, false),
    XLSX("xlsx", ".xlsx", "文档 xlsx格式/excel 2007", new Type[]{DOCUMENT}, false),
    PPT("ppt", ".ppt", "文档 ppt格式", new Type[]{DOCUMENT}, false),

    // 图片相关的格式，由于文档管理也需要，因此加上文档类型，视频类型也加上
    BMP("bmp", ".bmp", "图片 bmp格式", new Type[]{PICTURE, DOCUMENT, VIDEO}, false),
    JPG("jpg", ".jpg", "图片 jpg格式", new Type[]{PICTURE, DOCUMENT, VIDEO}, false),
    JPEG("jpeg", ".jpeg", "图片 jpeg格式", new Type[]{PICTURE, DOCUMENT, VIDEO}, false),
    PNG("png", ".png", "图片 png格式", new Type[]{PICTURE, DOCUMENT, VIDEO}, false),
    TIF("tif", ".tif", "图片 tif格式", new Type[]{PICTURE, DOCUMENT, VIDEO}, false),
    GIF("gif", ".gif", "图片 gif格式", new Type[]{PICTURE, DOCUMENT, VIDEO}, false),
    SVG("svg", ".svg", "图片 svg格式", new Type[]{PICTURE, DOCUMENT, VIDEO}, false),
    PSD("psd", ".psd", "图片 psd格式", new Type[]{PICTURE, DOCUMENT, VIDEO}, false),
    WEBP("webp", ".webp", "图片 webp格式", new Type[]{PICTURE, DOCUMENT, VIDEO}, false),

    // 视频相关格式
    MP_FOUR("mp4", ".mp4", "视频 mp4格式", new Type[]{VIDEO, PICTURE, DOCUMENT}, false),
    AVI("avi", ".avi", "视频 avi格式", new Type[]{VIDEO, PICTURE, DOCUMENT}, false),
    RMVB("rmvb", ".rmvb", "视频 rmvb格式", new Type[]{VIDEO, PICTURE, DOCUMENT}, false),
    MKV("mkv", ".mkv", "视频 mkv格式", new Type[]{VIDEO, PICTURE, DOCUMENT}, false),
    MOV("mov", ".mov", "视频 mov格式", new Type[]{VIDEO, PICTURE, DOCUMENT}, false),


    // 压缩文件格式，也算入文档类型
    RAR("rar", ".rar", "压缩文件 rar格式", new Type[]{DOCUMENT}, false),
    ZIP("zip", ".zip", "压缩文件 zip格式", new Type[]{DOCUMENT}, false),
    SevenZ("7z", ".7z", "压缩文件 7z格式", new Type[]{DOCUMENT}, false),
    TAR("tar", ".tar", "压缩文件 tar格式", new Type[]{DOCUMENT}, false),
    GZ("gz", ".gz", "压缩文件 gz格式", new Type[]{DOCUMENT}, false);

    @EnumValue
    private final String name;
    private final String dotName;
    private final String description;
    private final Type[] type;
    private final Boolean bimfaceSupport;

    public static Suffix getSuffix(String fileName) {
        if (null == fileName) {fileName = "";}
        for (Suffix suffix : Suffix.values()) {
            if (fileName.toLowerCase().contains(suffix.dotName)) {
                return suffix;
            }
        }
        return null;
    }

    public static List<String> getSupportSuffix(Type type) {
        Suffix[] suffixes = Suffix.values();
        List<String> supportTypeList = new ArrayList<>(suffixes.length);
        for (Suffix suffix : suffixes) {
            for (Type supportType : suffix.getType()) {
                if (type == supportType) {
                    supportTypeList.add(suffix.name);
                    break;
                }
            }
        }
        return supportTypeList;
    }

    public static List<String> getSupportSuffixWithDot(Type type) {
        Suffix[] suffixes = Suffix.values();
        List<String> supportTypeList = new ArrayList<>(suffixes.length);
        for (Suffix suffix : suffixes) {
            for (Type supportType : suffix.getType()) {
                if (type == supportType) {
                    supportTypeList.add(suffix.dotName);
                    break;
                }
            }
        }
        return supportTypeList;
    }

    /*public static void main(String[] args) {
        System.out.println(Suffix.getSupportSuffix(MODEL));
        System.out.println(Suffix.getSuffix("asadas.3ds"));
    }*/
}
