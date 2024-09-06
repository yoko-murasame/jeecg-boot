package org.design.core.tool.constant;

import org.design.core.tool.utils.StringPool;

/* loaded from: bigdata-core-tool-3.1.0.jar:org/design/core/tool/constant/SystemConstant.class */
public class SystemConstant {
    private boolean devMode = false;
    private boolean remoteMode = false;
    private String domain = "http://localhost:8888";
    private String remotePath = System.getProperty("user.dir") + "/target/blade";
    private String uploadPath = "/upload";
    private String downloadPath = "/download";
    private boolean compress = false;
    private Double compressScale = Double.valueOf(2.0d);
    private boolean compressFlag = false;
    private String realPath = System.getProperty("user.dir");
    private String contextPath = StringPool.SLASH;
    private static final SystemConstant ME = new SystemConstant();

    public void setDevMode(boolean devMode) {
        this.devMode = devMode;
    }

    public void setRemoteMode(boolean remoteMode) {
        this.remoteMode = remoteMode;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public void setRemotePath(String remotePath) {
        this.remotePath = remotePath;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }

    public void setDownloadPath(String downloadPath) {
        this.downloadPath = downloadPath;
    }

    public void setCompress(boolean compress) {
        this.compress = compress;
    }

    public void setCompressScale(Double compressScale) {
        this.compressScale = compressScale;
    }

    public void setCompressFlag(boolean compressFlag) {
        this.compressFlag = compressFlag;
    }

    public void setRealPath(String realPath) {
        this.realPath = realPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof SystemConstant)) {
            return false;
        }
        SystemConstant other = (SystemConstant) o;
        if (!other.canEqual(this) || isDevMode() != other.isDevMode() || isRemoteMode() != other.isRemoteMode() || isCompress() != other.isCompress() || isCompressFlag() != other.isCompressFlag()) {
            return false;
        }
        Object this$compressScale = getCompressScale();
        Object other$compressScale = other.getCompressScale();
        if (this$compressScale == null) {
            if (other$compressScale != null) {
                return false;
            }
        } else if (!this$compressScale.equals(other$compressScale)) {
            return false;
        }
        Object this$domain = getDomain();
        Object other$domain = other.getDomain();
        if (this$domain == null) {
            if (other$domain != null) {
                return false;
            }
        } else if (!this$domain.equals(other$domain)) {
            return false;
        }
        Object this$remotePath = getRemotePath();
        Object other$remotePath = other.getRemotePath();
        if (this$remotePath == null) {
            if (other$remotePath != null) {
                return false;
            }
        } else if (!this$remotePath.equals(other$remotePath)) {
            return false;
        }
        Object this$uploadPath = getUploadPath();
        Object other$uploadPath = other.getUploadPath();
        if (this$uploadPath == null) {
            if (other$uploadPath != null) {
                return false;
            }
        } else if (!this$uploadPath.equals(other$uploadPath)) {
            return false;
        }
        Object this$downloadPath = getDownloadPath();
        Object other$downloadPath = other.getDownloadPath();
        if (this$downloadPath == null) {
            if (other$downloadPath != null) {
                return false;
            }
        } else if (!this$downloadPath.equals(other$downloadPath)) {
            return false;
        }
        Object this$realPath = getRealPath();
        Object other$realPath = other.getRealPath();
        if (this$realPath == null) {
            if (other$realPath != null) {
                return false;
            }
        } else if (!this$realPath.equals(other$realPath)) {
            return false;
        }
        Object this$contextPath = getContextPath();
        Object other$contextPath = other.getContextPath();
        return this$contextPath == null ? other$contextPath == null : this$contextPath.equals(other$contextPath);
    }

    protected boolean canEqual(Object other) {
        return other instanceof SystemConstant;
    }

    public int hashCode() {
        int result = (((((((1 * 59) + (isDevMode() ? 79 : 97)) * 59) + (isRemoteMode() ? 79 : 97)) * 59) + (isCompress() ? 79 : 97)) * 59) + (isCompressFlag() ? 79 : 97);
        Object $compressScale = getCompressScale();
        int result2 = (result * 59) + ($compressScale == null ? 43 : $compressScale.hashCode());
        Object $domain = getDomain();
        int result3 = (result2 * 59) + ($domain == null ? 43 : $domain.hashCode());
        Object $remotePath = getRemotePath();
        int result4 = (result3 * 59) + ($remotePath == null ? 43 : $remotePath.hashCode());
        Object $uploadPath = getUploadPath();
        int result5 = (result4 * 59) + ($uploadPath == null ? 43 : $uploadPath.hashCode());
        Object $downloadPath = getDownloadPath();
        int result6 = (result5 * 59) + ($downloadPath == null ? 43 : $downloadPath.hashCode());
        Object $realPath = getRealPath();
        int result7 = (result6 * 59) + ($realPath == null ? 43 : $realPath.hashCode());
        Object $contextPath = getContextPath();
        return (result7 * 59) + ($contextPath == null ? 43 : $contextPath.hashCode());
    }

    public String toString() {
        return "SystemConstant(devMode=" + isDevMode() + ", remoteMode=" + isRemoteMode() + ", domain=" + getDomain() + ", remotePath=" + getRemotePath() + ", uploadPath=" + getUploadPath() + ", downloadPath=" + getDownloadPath() + ", compress=" + isCompress() + ", compressScale=" + getCompressScale() + ", compressFlag=" + isCompressFlag() + ", realPath=" + getRealPath() + ", contextPath=" + getContextPath() + StringPool.RIGHT_BRACKET;
    }

    public boolean isDevMode() {
        return this.devMode;
    }

    public boolean isRemoteMode() {
        return this.remoteMode;
    }

    public String getDomain() {
        return this.domain;
    }

    public String getRemotePath() {
        return this.remotePath;
    }

    public String getUploadPath() {
        return this.uploadPath;
    }

    public String getDownloadPath() {
        return this.downloadPath;
    }

    public boolean isCompress() {
        return this.compress;
    }

    public Double getCompressScale() {
        return this.compressScale;
    }

    public boolean isCompressFlag() {
        return this.compressFlag;
    }

    public String getRealPath() {
        return this.realPath;
    }

    public String getContextPath() {
        return this.contextPath;
    }

    private SystemConstant() {
    }

    public static SystemConstant me() {
        return ME;
    }

    public String getUploadRealPath() {
        return (this.remoteMode ? this.remotePath : this.realPath) + this.uploadPath;
    }

    public String getUploadCtxPath() {
        return this.contextPath + this.uploadPath;
    }
}
