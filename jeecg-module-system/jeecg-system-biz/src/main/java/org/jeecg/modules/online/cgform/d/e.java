package org.jeecg.modules.online.cgform.d;

import cn.hutool.core.io.FileUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/* compiled from: GenerateCodeFileToZip.java */
/* loaded from: hibernate-common-ol-5.4.74(2).jar:org/jeecg/modules/online/cgform/d/e.class */
public class e {
    public static File a(List<String> list, String str) throws RuntimeException {
        String substring;
        File file = FileUtil.touch(str);
        if (file == null || !file.getName().endsWith(".zip")) {
            return null;
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);
            for (String str2 : list) {
                File file2 = new File(URLDecoder.decode(str2, "UTF-8").replace("生成成功：", ""));
                if (file2 != null && file2.exists()) {
                    byte[] bArr = new byte[4096];
                    if (file2.getAbsolutePath().indexOf("src\\") != -1) {
                        substring = file2.getAbsolutePath().substring(file2.getAbsolutePath().indexOf("src\\") - 1);
                    } else {
                        substring = file2.getAbsolutePath().substring(file2.getAbsolutePath().indexOf("src/") - 1);
                    }
                    zipOutputStream.putNextEntry(new ZipEntry(substring));
                    FileInputStream fileInputStream = new FileInputStream(file2);
                    while (true) {
                        int read = fileInputStream.read(bArr);
                        if (read == -1) {
                            break;
                        }
                        zipOutputStream.write(bArr, 0, read);
                    }
                    fileInputStream.close();
                    zipOutputStream.closeEntry();
                }
            }
            if (zipOutputStream != null) {
                try {
                    zipOutputStream.close();
                } catch (IOException e) {
                    System.out.println("ZipUtil toZip close exception" + e);
                }
            }
            fileOutputStream.close();
            return file;
        } catch (Exception e2) {
            throw new RuntimeException("zipFile error from ZipUtils", e2);
        }
    }
}