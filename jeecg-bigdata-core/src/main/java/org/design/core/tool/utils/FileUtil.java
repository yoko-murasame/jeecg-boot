package org.design.core.tool.utils;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.util.ArrayList;
import java.util.List;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.multipart.MultipartFile;

/* loaded from: bigdata-core-tool-3.1.0.jar:org/design/core/tool/utils/FileUtil.class */
public final class FileUtil extends FileCopyUtils {
    private FileUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /* loaded from: bigdata-core-tool-3.1.0.jar:org/design/core/tool/utils/FileUtil$TrueFilter.class */
    public static class TrueFilter implements FileFilter, Serializable {
        private static final long serialVersionUID = -6420452043795072619L;
        public static final TrueFilter TRUE = new TrueFilter();

        @Override // java.io.FileFilter
        public boolean accept(File pathname) {
            return true;
        }
    }

    public static List<File> list(String path) {
        return list(new File(path), TrueFilter.TRUE);
    }

    public static List<File> list(String path, String fileNamePattern) {
        return list(new File(path), pathname -> {
            return PatternMatchUtils.simpleMatch(fileNamePattern, pathname.getName());
        });
    }

    public static List<File> list(String path, FileFilter filter) {
        return list(new File(path), filter);
    }

    public static List<File> list(File file) {
        return list(file, new ArrayList<>(), TrueFilter.TRUE);
    }

    public static List<File> list(File file, String fileNamePattern) {
        return list(file, new ArrayList<>(), pathname -> {
            return PatternMatchUtils.simpleMatch(fileNamePattern, pathname.getName());
        });
    }

    public static List<File> list(File file, FileFilter filter) {
        return list(file, new ArrayList<>(), filter);
    }

    private static List<File> list(File file, List<File> fileList, FileFilter filter) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    list(f, fileList, filter);
                }
            }
        } else {
            boolean accept = filter.accept(file);
            if (file.exists() && accept) {
                fileList.add(file);
            }
        }
        return fileList;
    }

    public static String getFileExtension(String fullName) {
        Assert.notNull(fullName, "file fullName is null.");
        String fileName = new File(fullName).getName();
        int dotIndex = fileName.lastIndexOf(46);
        return dotIndex == -1 ? StringPool.EMPTY : fileName.substring(dotIndex + 1);
    }

    public static String getNameWithoutExtension(String file) {
        Assert.notNull(file, "file is null.");
        String fileName = new File(file).getName();
        int dotIndex = fileName.lastIndexOf(46);
        return dotIndex == -1 ? fileName : fileName.substring(0, dotIndex);
    }

    public static String getTempDirPath() {
        return System.getProperty("java.io.tmpdir");
    }

    public static File getTempDir() {
        return new File(getTempDirPath());
    }

    public static String readToString(File file) {
        return readToString(file, Charsets.UTF_8);
    }

    public static String readToString(File file, Charset encoding) {
        try {
            InputStream in = Files.newInputStream(file.toPath(), new OpenOption[0]);
            String ioUtil = IoUtil.toString(in, encoding);
            if (in != null) {
                if (0 != 0) {
                    in.close();
                } else {
                    in.close();
                }
            }
            return ioUtil;
        } catch (IOException e) {
            throw Exceptions.unchecked(e);
        }
    }

    public static byte[] readToByteArray(File file) {
        try {
            InputStream in = Files.newInputStream(file.toPath(), new OpenOption[0]);
            byte[] byteArray = IoUtil.toByteArray(in);
            if (in != null) {
                if (0 != 0) {
                    in.close();
                } else {
                    in.close();
                }
            }
            return byteArray;
        } catch (IOException e) {
            throw Exceptions.unchecked(e);
        }
    }

    public static void writeToFile(File file, String data) {
        writeToFile(file, data, Charsets.UTF_8, false);
    }

    public static void writeToFile(File file, String data, boolean append) {
        writeToFile(file, data, Charsets.UTF_8, append);
    }

    public static void writeToFile(File file, String data, Charset encoding) {
        writeToFile(file, data, encoding, false);
    }

    public static void writeToFile(File file, String data, Charset encoding, boolean append) {
        try {
            OutputStream out = new FileOutputStream(file, append);
            IoUtil.write(data, out, encoding);
            if (out != null) {
                if (0 != 0) {
                    out.close();
                } else {
                    out.close();
                }
            }
        } catch (IOException e) {
            throw Exceptions.unchecked(e);
        }
    }

    public static void toFile(MultipartFile multipartFile, File file) {
        try {
            toFile(multipartFile.getInputStream(), file);
        } catch (IOException e) {
            throw Exceptions.unchecked(e);
        }
    }

    public static void toFile(InputStream in, File file) {
        try {
            OutputStream out = new FileOutputStream(file);
            copy(in, out);
            if (out != null) {
                if (0 != 0) {
                    out.close();
                } else {
                    out.close();
                }
            }
        } catch (IOException e) {
            throw Exceptions.unchecked(e);
        }
    }

    public static void moveFile(File srcFile, File destFile) throws IOException {
        Assert.notNull(srcFile, "Source must not be null");
        Assert.notNull(destFile, "Destination must not be null");
        if (!srcFile.exists()) {
            throw new FileNotFoundException("Source '" + srcFile + "' does not exist");
        } else if (srcFile.isDirectory()) {
            throw new IOException("Source '" + srcFile + "' is a directory");
        } else if (destFile.exists()) {
            throw new IOException("Destination '" + destFile + "' already exists");
        } else if (destFile.isDirectory()) {
            throw new IOException("Destination '" + destFile + "' is a directory");
        } else if (!srcFile.renameTo(destFile)) {
            copy(srcFile, destFile);
            if (!srcFile.delete()) {
                deleteQuietly(destFile);
                throw new IOException("Failed to delete original file '" + srcFile + "' after copy to '" + destFile + StringPool.SINGLE_QUOTE);
            }
        }
    }

    public static boolean deleteQuietly(@Nullable File file) {
        if (file == null) {
            return false;
        }
        try {
            if (file.isDirectory()) {
                FileSystemUtils.deleteRecursively(file);
            }
        } catch (Exception e) {
        }
        try {
            return file.delete();
        } catch (Exception e2) {
            return false;
        }
    }
}
