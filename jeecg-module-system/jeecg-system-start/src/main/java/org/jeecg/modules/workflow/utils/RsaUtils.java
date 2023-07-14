package org.jeecg.modules.workflow.utils;

import cn.hutool.core.codec.Base64Decoder;
import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.core.text.UnicodeUtil;
import cn.hutool.crypto.KeyUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.jeecg.common.api.vo.Result;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.*;
import java.util.stream.Collectors;

/**
 * RSA加解密工具类
 * public和private密钥仅支持DER格式
 * 需要从PEM格式转换：https://www.ssleye.com/ssltool/der_pem.html
 *
 * @author Yoko
 */
@Getter
@Slf4j
public class RsaUtils {

    private RSA rsa;
    private PublicKey publicKey;
    private PrivateKey privateKey;
    //系统密钥 业务中的自定义校验
    @Setter
    private String systemKey;
    @Setter
    private String systemName;
    @Setter
    private Integer expired;
    @Setter
    private String baseApi = "https://xxx:88";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Deprecated
    public RsaUtils() {

    }

    private RsaUtils(RSA rsa, PublicKey publicKey, PrivateKey privateKey) {
        this.rsa = rsa;
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    public static RsaUtils createInstance(String publicKeyResourcePath, String privateKeyResourcePath) {
        Assert.hasText(publicKeyResourcePath, "publicKey路径为空");
        Assert.hasText(privateKeyResourcePath, "privateKey路径为空");
        try {
            byte[] pubDer = Files.readAllBytes(Paths.get(RsaUtils.class.getClassLoader().getResource(publicKeyResourcePath).toURI()));
            byte[] priDer = Files.readAllBytes(Paths.get(RsaUtils.class.getClassLoader().getResource(privateKeyResourcePath).toURI()));
            PublicKey publicKey = KeyUtil.generateRSAPublicKey(pubDer);
            PrivateKey privateKey = KeyUtil.generateRSAPrivateKey(priDer);
            RSA rsa = new RSA(privateKey, publicKey);
            return new RsaUtils(rsa, publicKey, privateKey);
        } catch (IOException e) {
            log.error(e.getMessage());
        } catch (URISyntaxException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    public static RsaUtils createInstance(byte[] publicKeyBytes, byte[] privateKeyBytes) {
        try {
            PublicKey publicKey = KeyUtil.generateRSAPublicKey(publicKeyBytes);
            PrivateKey privateKey = KeyUtil.generateRSAPrivateKey(privateKeyBytes);
            RSA rsa = new RSA(privateKey, publicKey);
            return new RsaUtils(rsa, publicKey, privateKey);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    /**
     * token校验核心方法，未通过将抛出异常
     * 通过后将返回唯一码：手机号
     *
     * @param token
     * @return phone
     */
    public String checkToken(String token) {
        String data = null;
        try {
            data = this.decryptBase64(token);
        } catch (Exception e) {
            throw new RuntimeException("RSA解密错误！");
        }
        Assert.hasText(data, "token解析错误！");
        String[] blocks = data.split(",");
        Assert.state(blocks.length == 3, "数据报文长度不正确！");
        if (StringUtils.hasText(this.systemKey)) {
            Assert.state(this.systemKey.equals(blocks[1]), "系统密钥不正确！");
        }
        if (this.expired != null && this.expired > 0) {
            double timestamp = Double.parseDouble(blocks[2]);
            // Assert.state(timestamp + this.expired >= System.currentTimeMillis(), "token已过期！");
            if (timestamp + this.expired < System.currentTimeMillis()) {
                throw new RuntimeException("token已过期！请重新登录！");
            }
        }
        return blocks[0];
    }

    /**
     * @param customKey
     * @return java.lang.String
     * @author Yoko
     * @date 2021/12/17 16:22
     * @description 生成请求oa系统的密钥，链接组成:https://127.0.0.1?sysName=第三方系统名称&encode_str=密钥
     */
    public String generateOaSecret(String customKey) {
        if (StringUtils.isEmpty(customKey)) {
            customKey = this.systemKey;
        }
        Assert.state(StringUtils.hasText(customKey), "请配置systemKey");
        return encryptBase64(customKey + "," + System.currentTimeMillis());
    }

    public String generateOaSecret() {
        return this.generateOaSecret(null);
    }

    public String generateOaSecretWithPhone(String customKey, String phone) {
        if (StringUtils.isEmpty(customKey)) {
            customKey = this.systemKey;
        }
        Assert.state(StringUtils.hasText(customKey), "请配置systemKey");
        Assert.state(StringUtils.hasText(phone), "手机号为空");
        return encryptBase64(phone + "," + customKey + "," + System.currentTimeMillis());
    }

    public String generateOaSecretWithPhone(String phone) {
        return this.generateOaSecretWithPhone(null, phone);
    }

    /**
     * @param title 事务名称
     * @param username OA用户姓名
     * @param phone OA手机号
     * @return OA代办创建成功后的ID
     * @author Yoko
     * @date 2022/3/23 15:34
     * @description 发送代办消息
     */
    public Result<?> sendTodo(String title, String username, String phone) {

        Map<String, Object> params = new HashMap<>(6);
        params.put("sys_name", this.systemName);
        params.put("encode_str", this.generateOaSecretWithPhone(phone));
        params.put("title", title);
        params.put("time", Math.round(System.currentTimeMillis() / 1000));
        params.put("user_name", username);
        params.put("mobile_no", phone);
        String paramsStr = JSON.toJSONString(params);

        // 这个是form形式的数据
        // String response = HttpUtil.post(this.baseApi + "/cminfo/RSA/receive.php", params);
        // 这个是body形式的json传输
        String responseStr = HttpUtil.post(this.baseApi + "/cminfo/RSA/receive.php", paramsStr);
        JSONObject response = JSON.parseObject(UnicodeUtil.toString(responseStr));
        Integer success = response.getInteger("success");
        String id = response.getString("id");
        String msg = response.getString("msg");

        log.info("OA代办发送成功，title：{}，todoId：{}，phone：{}，success：{}。", title, id, phone, success);

        return success == 1 ? Result.OK(msg, id) : Result.error(msg);
    }

    /**
     * @param title 消息标题
     * @param usernames 用户名列表（对应系统里的真实姓名）
     * @param phones 手机号列表
     * @param tableName 表名，默认为：ext_act_flow_data
     * @param fieldName 消息回存字段，默认为：oa_todo_id
     * @param uniqueField 唯一键，默认为：process_inst_id（流程实例id）
     * @param uniqueValue 键值，默认为：流程实例id
     * @return 发送成功的所有手机号和消息的id字符串拼接（phone:id,phone:id），过程中如果消息发送失败，直接返回错误消息
     * @author Yoko
     * @date 2022/3/24 9:51
     * @description 发送消息给所有OA的用户
     */
    public Result<String> sendTodoAndSave(String title, List<String> usernames, List<String> phones, String tableName, String fieldName, String uniqueField, String uniqueValue) {
        List<String> msgIds = new ArrayList<>(usernames.size());
        List<String> sentPhones = new ArrayList<>(phones.size());
        List<String> sentTodoIds = new ArrayList<>(phones.size());
        for (int i = 0; i < usernames.size(); i++) {
            String username = usernames.get(i);
            String phone = phones.get(i);
            Result result = this.sendTodo(title, username, phone);
            // Result result = Result.OK("test_id_" + i);
            // Todo 消息发送失败也继续发送
            if (!result.isSuccess()) {
                log.error(String.format("OA代办添加失败，标题：%s，用户真实姓名：%s，手机号：%s，错误信息：", title, username, phone, result.getMessage()));
                // result.setMessage(String.format("消息发送失败，标题：%s，用户名：%s，手机号：%s，错误信息：", title, username, phone, result.getMessage()));
                // OK 消息发送失败后，将所有原先的消息状态置为2（已完成）
                // this.finishTodo(sentTodoIds, sentPhones);
                // return result;
            }
            if (result.isSuccess()) {
                String todoId = (String) result.getResult();
                msgIds.add(phone + ":" + todoId);
                // 保存已发送信息关联项
                sentPhones.add(phone);
                sentTodoIds.add(todoId);
                log.info("OA代办添加成功，title：{}，todoId：{}，phone：{}。", title, todoId, phone);
            }
        }
        // 保存到流程相关关联表
        try {
            String phoneAndIds = msgIds.stream().collect(Collectors.joining(","));
            String sqlPre = "UPDATE %s SET %s='%s' WHERE %s='%s'";
            String sql = String.format(sqlPre, tableName, fieldName, phoneAndIds, uniqueField, uniqueValue);
            int update = jdbcTemplate.update(sql);
            return update > 0 ? Result.OK("发送成功，保存成功！", phoneAndIds) : Result.OK("发送成功，保存失败！", phoneAndIds);
        } catch (Exception e) {
            Result<String> result = new Result();
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            result.setCode(0);
            return result;
        }
    }

    /**
     * @param title 消息标题
     * @param usernames 用户名列表（对应系统里的真实姓名）
     * @param phones 手机号列表
     * @return 发送成功的所有手机号和消息的id字符串拼接（phone:id,phone:id），过程中如果消息发送失败，直接返回错误消息
     * @author Yoko
     * @date 2022/3/24 9:51
     * @description 发送消息给所有OA的用户，注：硬性要求创建表“ext_act_flow_data”、字段“oa_todo_id”、字段“process_inst_id”
     */
    public Result<String> sendTodoAndSave(String title, List<String> usernames, List<String> phones, String processInstId) {
        return this.sendTodoAndSave(title, usernames, phones, "ext_act_flow_data", "oa_todo_id", "process_inst_id", processInstId);
    }

    /**
     * @param processInstId
     * @return List<String> 格式：phone:id,phone:id
     * @author Yoko
     * @date 2022/3/24 9:56
     * @description 获取指定流程实例的所有OA消息id
     */
    public List<String> getTodoIds(String processInstId) {
        String sqlPre = "SELECT %s FROM %s WHERE %s='%s'";
        String sql = String.format(sqlPre, "oa_todo_id", "ext_act_flow_data", "process_inst_id", processInstId);
        Map<String, Object> map = jdbcTemplate.queryForMap(sql);
        String phoneAndIds = Optional.ofNullable((String) map.get("oa_todo_id")).orElse("");
        return Arrays.asList(phoneAndIds.split(","));
    }

    /**
     * @param processInstId 流程实例id
     * @return Result
     * @author Yoko
     * @date 2022/3/24 10:48
     * @description 完成OA代办
     */
    public Result finishTodo(String processInstId) {
        List<String> phoneAndIds = this.getTodoIds(processInstId);
        List<String> todoIds = new ArrayList<>(phoneAndIds.size());
        List<String> phones = new ArrayList<>(phoneAndIds.size());
        phoneAndIds.forEach(str -> {
            if (StringUtils.isEmpty(str)) {
                return;
            }
            String[] arr = str.split(":");
            phones.add(arr[0]);
            todoIds.add(arr[1]);
        });
        // return Result.OK();
        return this.finishTodo(todoIds, phones);
    }

    /**
     * @param todoIds 消息ids
     * @param phones 手机号列表
     * @return Result
     * @author Yoko
     * @date 2022/3/24 10:43
     * @description 完成OA代办
     */
    public Result finishTodo(List<String> todoIds, List<String> phones) {
        for (int i = 0; i < todoIds.size(); i++) {
            String todoId = todoIds.get(i);
            String phone = phones.get(i);
            Map<String, Object> params = new HashMap<>(6);
            params.put("sys_name", this.systemName);
            params.put("encode_str", this.generateOaSecretWithPhone(phone));
            params.put("status", 2);
            params.put("id", todoId);
            String paramsStr = JSON.toJSONString(params);
            String responseStr = HttpUtil.post(this.baseApi + "/cminfo/RSA/update.php", paramsStr);
            JSONObject response = JSON.parseObject(UnicodeUtil.toString(responseStr));
            Integer success = response.getInteger("success");
            String msg = response.getString("msg");
            // Todo 执行失败也需要继续执行
            // if (success != 1) {
            //     return Result.error(msg);
            // }
            log.info("完成OA代办，todoId：{}，phone：{}，success：{}，msg：{}。", todoId, phone, success, msg);
        }
        return Result.OK();
    }

    /**
     * @param
     * @return java.lang.String
     * @author Yoko
     * @date 2021/12/17 16:49
     * @description 生成部门信息请求url，https://xx:88/cminfo/getOrg/getDept.php?sysName=第三方系统名称&encode_str=密钥
     */
    @SneakyThrows
    public String getDepartInfoUrl() {
        Assert.hasText(this.systemName, "请配置systemName");
        String encode_str = this.generateOaSecret();
        return String.format(this.baseApi + "/cminfo/getOrg/getDept.php?sysName=%s&encode_str=%s", URLEncoder.encode(systemName, "UTF-8"), URLEncoder.encode(encode_str, "UTF-8"));
    }

    /**
     * @param
     * @return java.lang.String
     * @author Yoko
     * @date 2021/12/17 16:49
     * @description 生成用户信息请求url，https://xx:88/cminfo/getOrg/getUser.php?sysName=第三方系统名称&encode_str=密钥
     */
    @SneakyThrows
    public String getUserInfoUrl() {
        Assert.hasText(this.systemName, "请配置systemName");
        String encode_str = this.generateOaSecret();
        return String.format(this.baseApi + "/cminfo/getOrg/getUser.php?sysName=%s&encode_str=%s", URLEncoder.encode(systemName, "UTF-8"), URLEncoder.encode(encode_str, "UTF-8"));
    }

    /**
     * @param
     * @return java.lang.String
     * @author Yoko
     * @date 2021/12/17 16:49
     * @description 生成部门更新信息url，https://xx:88/cminfo/getOrg/getDeptUp.php?sysName=第三方系统名称&encode_str=密钥
     */
    @SneakyThrows
    public String getDepartInfoUpdateUrl() {
        Assert.hasText(this.systemName, "请配置systemName");
        String encode_str = this.generateOaSecret();
        return String.format(this.baseApi + "/cminfo/getOrg/getDeptUp.php?sysName=%s&encode_str=%s", URLEncoder.encode(systemName, "UTF-8"), URLEncoder.encode(encode_str, "UTF-8"));
    }

    /**
     * @param
     * @return java.lang.String
     * @author Yoko
     * @date 2021/12/17 16:49
     * @description 生成用户更新信息url，https://xx:88/cminfo/getOrg/getUserUp.php?sysName=第三方系统名称&encode_str=密钥
     */
    @SneakyThrows
    public String getUserInfoUpdateUrl() {
        Assert.hasText(this.systemName, "请配置systemName");
        String encode_str = this.generateOaSecret();
        return String.format(this.baseApi + "/cminfo/getOrg/getUserUp.php?sysName=%s&encode_str=%s", URLEncoder.encode(systemName, "UTF-8"), URLEncoder.encode(encode_str, "UTF-8"));
    }

    public String encryptBase64(String payload) {
        return this.encodeBase64(this.encrypt(payload));
    }

    public String encryptSafeBase64(String payload) {
        byte[] encrypt = this.encrypt(payload);
        return this.encodeSafeBase64(encrypt);
    }

    public String decryptBase64(String encryptBase64) {
        return new String(decrypt(this.decodeBase64(encryptBase64)));
    }

    public String decryptSafeBase64(String encryptBase64) {
        byte[] bytes = this.decodeSafeBase64(encryptBase64);
        return new String(decrypt(bytes));
    }

    public byte[] encrypt(String payload) {
        return rsa.encrypt(payload, KeyType.PublicKey);
    }

    public byte[] decrypt(byte[] encryptBytes) {
        return rsa.decrypt(encryptBytes, KeyType.PrivateKey);
    }


    public byte[] decodeBase64(String encodeBase64) {
        return Base64Decoder.decode(encodeBase64);
    }

    public byte[] decodeSafeBase64(String encodeBase64) {
        return Base64.decodeBase64(encodeBase64);
    }

    public String encodeBase64(byte[] bytes) {
        return Base64Encoder.encode(bytes);
    }

    public String encodeSafeBase64(byte[] bytes) {
        return Base64.encodeBase64URLSafeString(bytes);
    }

    /**
     * classloader需要以spring boot方式启动才有，使用第二种构造函数创建实例不会报错
     */
    @Test
    public void test() {
        //初始化
        String publicPath = "/rsa/ssleye_public_xx.der";
        String privatePath = "/rsa/ssleye_private_xx.key";
        RsaUtils rsaUtils = RsaUtils.createInstance(publicPath, privatePath);
        rsaUtils.setSystemKey("FR001");
        rsaUtils.setExpired(30 * 1000);

        String data = "18755555555,FR001," + System.currentTimeMillis();
        // 加密测试
        String token = rsaUtils.encryptBase64(data);

        // 校验测试
        System.out.println(rsaUtils.checkToken(token));
    }

    @SneakyThrows
    @Test
    public void testUtils() {
        byte[] pubDer = Files.readAllBytes(Paths.get(this.getClass().getResource("/rsa/ssleye_public_xx.der").toURI()));
        byte[] priDer = Files.readAllBytes(Paths.get(this.getClass().getResource("/rsa/ssleye_private_xx.key").toURI()));

        RsaUtils rsaUtils = RsaUtils.createInstance(pubDer, priDer);
        rsaUtils.setSystemKey("FR001"); // 校验系统密钥
        rsaUtils.setExpired(2 * 1000); // 校验过期时间

        String data = "18755555555,FR001," + System.currentTimeMillis();
        Thread.sleep(1500);

        // 加密测试
        String token = rsaUtils.encryptBase64(data);

        // 校验token测试，校验失败抛出异常，成功后返回手机号
        String phone = rsaUtils.checkToken(token);
        System.out.println(phone);
    }

    @SneakyThrows
    @Test
    public void testDec() {

        //初始化
        String publicPath = "/rsa/ssleye_public_chengfa.der";
        String privatePath = "/rsa/ssleye_private_chengfa.key";
        RsaUtils rsaUtils = RsaUtils.createInstance(publicPath, privatePath);
        rsaUtils.setSystemKey("Gc$#78");
        rsaUtils.setExpired(30 * 1000);

        // 解密密文
        String result = rsaUtils.decryptBase64("hbiXJTpduPX7m9/HVI5RilqIrLjjluWJpVwHmInBbP3zg4dAKQkO7Nn/fDoLS77OJtHwD/jOsXdAEowc6vgD6Q==");

        System.out.println(result);
    }
}
