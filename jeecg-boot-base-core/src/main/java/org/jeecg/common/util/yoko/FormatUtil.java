package org.jeecg.common.util.yoko;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 各种格式化输出方法
 *
 * @author Yoko
 * @since 2023/10/9 9:35
 */
public class FormatUtil {

    /**
     * 格式化 XGB XMB XKB
     *
     * @param bytes
     * @return java.lang.String
     * @author Yoko
     * @since 2023/10/9 9:36
     */
    public static String formatByteSize(long bytes) {
        if (bytes == 0) {
            return "0";
        }

        final long kiloBytes = 1024;
        final long megaBytes = kiloBytes * 1024;
        final long gigaBytes = megaBytes * 1024;

        double value;
        String unit;

        if (bytes >= gigaBytes) {
            value = (double) bytes / gigaBytes;
            unit = "GB";
        } else if (bytes >= megaBytes) {
            value = (double) bytes / megaBytes;
            unit = "MB";
        } else if (bytes >= kiloBytes) {
            value = (double) bytes / kiloBytes;
            unit = "KB";
        } else {
            return String.valueOf(bytes);
        }

        // 只保留非零的最大单位和相应的值
        if (value >= 10 || unit.equals("GB")) {
            return String.format("%.0f%s", value, unit);
        } else if (value >= 1) {
            return String.format("%.1f%s", value, unit);
        } else {
            return String.format("%.2f%s", value, unit);
        }
    }

    /**
     * 获取当前JVM内存信息
     *
     * @return java.lang.String
     * @author Yoko
     * @since 2023/10/9 9:37
     */
    public static String getCurrentMemoryInfo() {
        // 获取初始内存信息
        long initialMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        long initialFreeMemory = Runtime.getRuntime().freeMemory();

        // 转换成 MB
        initialMemory = initialMemory / 1024 / 1024;
        initialFreeMemory = initialFreeMemory / 1024 / 1024;

        return String.format("当前内存：%s MB，剩余内存：%s MB", initialMemory, initialFreeMemory);
    }

    /**
     * 格式化时间戳差值 x时 x分 x秒
     *
     * @param duration 时间戳差值
     * @return java.lang.String
     * @author Yoko
     * @since 2023/10/9 9:38
     */
    public static String formatTimeDuration(long duration) {
        long hours = duration / 3600;
        long minutes = (duration % 3600) / 60;
        long seconds = duration % 60;

        StringBuilder sb = new StringBuilder();

        if (hours > 0) {
            sb.append(hours).append("时");
        }
        if (minutes > 0) {
            sb.append(minutes).append("分");
        }
        if (seconds > 0 || sb.length() == 0) {
            sb.append(seconds).append("秒");
        }
        return sb.toString();
    }

    /**
     * 提取文件名
     *
     * @param str 输入：branch/unsorted/202310/高清大图01_1697715592396.jpg，输出：高清大图01.jpg
     * @return java.lang.String
     * @author Yoko
     * @since 2023/10/24 10:41
     */
    public static String extractFileName(String str) {
        int lastSlashIndex = str.lastIndexOf("/");
        if (lastSlashIndex != -1 && lastSlashIndex < str.length() - 1) {
            String substring = str.substring(lastSlashIndex + 1);
            int underscoreIndex = substring.lastIndexOf("_");
            int dotIndex = substring.lastIndexOf(".");
            if (underscoreIndex != -1 && dotIndex != -1 && underscoreIndex < dotIndex) {
                return substring.substring(0, underscoreIndex) + substring.substring(dotIndex);
            }
        }
        return str;
    }

    /**
     * 格式化日期 yyyy-MM-dd
     *
     * @param dateStrList 日期字符串列表
     * @param split       分隔符
     * @return java.util.List<java.util.Date>
     * @author Yoko
     * @since 2023/11/8 10:24
     */
    public static List<Date> toStanderDate(List<String> dateStrList, String split) throws Exception {
        List<Date> result = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (String dateStr : dateStrList) {
            String[] splitArr = dateStr.split(split);
            if (splitArr.length == 3) {
                String year = splitArr[0];
                String month = splitArr[1];
                String day = splitArr[2];
                if (month.length() == 1) {
                    month = "0" + month;
                }
                if (day.length() == 1) {
                    day = "0" + day;
                }
                // 转换成Date
                result.add(sdf.parse(year + "-" + month + "-" + day));
            } else {
                throw new Exception("日期格式不正确：" + dateStr);
            }
        }
        return result;
    }

    /**
     * 格式化日期 yyyy-MM-dd
     *
     * @param dateStrList 日期字符串列表
     * @param split       分隔符
     * @return java.util.List<java.lang.String>
     * @author Yoko
     * @since 2023/11/8 10:25
     */
    public static List<String> toStanderDateStr(List<String> dateStrList, String split) throws Exception {
        List<String> result = new ArrayList<>();
        for (String dateStr : dateStrList) {
            // 排除split的正则异常
            String[] splitArr = dateStr.split(split);
            if (splitArr.length == 3) {
                String year = splitArr[0];
                String month = splitArr[1];
                String day = splitArr[2];
                if (month.length() == 1) {
                    month = "0" + month;
                }
                if (day.length() == 1) {
                    day = "0" + day;
                }
                result.add(year + "-" + month + "-" + day);
            } else {
                throw new Exception("日期格式不正确：" + dateStr);
            }
        }
        return result;
    }
}
