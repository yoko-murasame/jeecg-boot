package org.jeecg.modules.online.config.a;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/* compiled from: DmDataBaseConfig.java */
@ConfigurationProperties(prefix = "spring.datasource.druid")
@Component("dmDataBaseConfig")
/* loaded from: hibernate-common-ol-5.4.74(2).jar:org/jeecg/modules/online/config/a/c.class */
public class c {
    private String a;
    private String b;
    private String c;
    private String d;
    private d e;

    public d getDruid() {
        return this.e;
    }

    public void setDruid(d druid) {
        this.e = druid;
    }

    public String getUrl() {
        return this.a;
    }

    public void setUrl(String url) {
        this.a = url;
    }

    public String getUsername() {
        return this.b;
    }

    public void setUsername(String username) {
        this.b = username;
    }

    public String getPassword() {
        return this.c;
    }

    public void setPassword(String password) {
        this.c = password;
    }

    public String getDriverClassName() {
        return this.d;
    }

    public void setDriverClassName(String driverClassName) {
        this.d = driverClassName;
    }
}