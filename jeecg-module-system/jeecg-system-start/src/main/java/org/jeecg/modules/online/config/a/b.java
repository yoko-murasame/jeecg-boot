package org.jeecg.modules.online.config.a;

import org.jeecg.common.util.oConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/* compiled from: DataBaseConfig.java */
@ConfigurationProperties(prefix = "spring.datasource.dynamic.datasource.master")
@Component("dataBaseConfig")
/* loaded from: hibernate-common-ol-5.4.74(2).jar:org/jeecg/modules/online/config/a/b.class */
public class b {
    @Autowired
    private c dmDataBaseConfig;
    private String a;
    private String b;
    private String c;
    private String d;
    private d e;

    public d getDruid() {
        if (this.e == null) {
            return this.dmDataBaseConfig.getDruid();
        }
        return this.e;
    }

    public void setDruid(d druid) {
        this.e = druid;
    }

    public String getUrl() {
        return oConvertUtils.getString(this.a, this.dmDataBaseConfig.getUrl());
    }

    public void setUrl(String url) {
        this.a = url;
    }

    public String getUsername() {
        return oConvertUtils.getString(this.b, this.dmDataBaseConfig.getUsername());
    }

    public void setUsername(String username) {
        this.b = username;
    }

    public String getPassword() {
        return oConvertUtils.getString(this.c, this.dmDataBaseConfig.getPassword());
    }

    public void setPassword(String password) {
        this.c = password;
    }

    public String getDriverClassName() {
        return oConvertUtils.getString(this.d, this.dmDataBaseConfig.getDriverClassName());
    }

    public void setDriverClassName(String driverClassName) {
        this.d = driverClassName;
    }
}