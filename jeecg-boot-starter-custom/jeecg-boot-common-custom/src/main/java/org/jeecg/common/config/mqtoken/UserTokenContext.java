package org.jeecg.common.config.mqtoken;

public class UserTokenContext {
    private static ThreadLocal<String> userToken = new ThreadLocal();

    public UserTokenContext() {
    }

    public static String getToken() {
        return (String)userToken.get();
    }

    public static void setToken(String token) {
        userToken.set(token);
    }

    public static void remove() {
        userToken.remove();
    }
}
