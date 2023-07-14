package org.jeecg.modules.activiti.jeecg.commons;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;

public class CommonB {
    public CommonB() {
    }

    public static JSONObject a(String var0, String var1, String var2, String var3) {
        JSONObject var4 = null;
        StringBuffer var5 = new StringBuffer();
        HttpURLConnection var6 = null;

        try {
            URL var7 = new URL(var0);
            var6 = (HttpURLConnection)var7.openConnection();
            var6.setDoOutput(true);
            var6.setDoInput(true);
            var6.setUseCaches(false);
            var6.setConnectTimeout(30000);
            var6.setReadTimeout(30000);
            var6.setRequestProperty("X-JEECG-SIGN", var3);
            var6.setRequestProperty("X-JEECG-DOCKER", "true");
            var6.setRequestMethod(var1);
            if ("GET".equalsIgnoreCase(var1)) {
                var6.connect();
            }

            OutputStream var8;
            if (null != var2) {
                var8 = var6.getOutputStream();
                var8.write(var2.getBytes("UTF-8"));
                var8.close();
            }

            InputStream var25 = var6.getInputStream();
            InputStreamReader var9 = new InputStreamReader(var25, "utf-8");
            BufferedReader var10 = new BufferedReader(var9);
            String var11 = null;

            while((var11 = var10.readLine()) != null) {
                var5.append(var11);
            }

            var10.close();
            var9.close();
            var25.close();
            var8 = null;
            var6.disconnect();
            var4 = JSONObject.parseObject(var5.toString());
        } catch (ConnectException var22) {
            System.err.println("server connection timed out.");
        } catch (Exception var23) {
            System.err.println("https request error.");
        } finally {
            try {
                var6.disconnect();
            } catch (Exception var21) {
                var21.printStackTrace();
                System.err.println("http close error.");
            }

        }

        return var4;
    }
}
