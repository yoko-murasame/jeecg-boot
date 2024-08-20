package org.jeecg.common.api;

import com.alibaba.fastjson.JSONObject;
import org.jeecg.common.api.dto.message.*;
import org.jeecg.common.system.query.QueryRuleEnum;
import org.jeecg.common.system.vo.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 通用api
 * @author: jeecg-boot
 */
public interface CommonAPI {

    /**
     * 1发送系统消息
     * @param message 使用构造器赋值参数 如果不设置category(消息类型)则默认为2 发送系统消息
     */
    void sendSysAnnouncement(MessageDTO message);

    /**
     * 2发送消息 附带业务参数
     * @param message 使用构造器赋值参数
     */
    void sendBusAnnouncement(BusMessageDTO message);

    /**
     * 3通过模板发送消息
     * @param message 使用构造器赋值参数
     */
    void sendTemplateAnnouncement(TemplateMessageDTO message);

    /**
     * 4通过模板发送消息 附带业务参数
     * @param message 使用构造器赋值参数
     */
    void sendBusTemplateAnnouncement(BusTemplateMessageDTO message);

    /**
     * 5通过消息中心模板，生成推送内容
     * @param templateDTO 使用构造器赋值参数
     * @return
     */
    String parseTemplateByCode(TemplateDTO templateDTO);

    //update-begin---author:taoyan ---date:20220705  for：支持自定义推送类型，邮件、钉钉、企业微信、系统消息-----------
    /**
     * 发送模板消息【新，支持自定义推送类型】
     * @param message
     */
    void sendTemplateMessage(MessageDTO message);

    /**
     * 根据模板编码获取模板内容【新，支持自定义推送类型】
     * @param templateCode
     * @return
     */
    String getTemplateContent(String templateCode);
    //update-begin---author:taoyan ---date:20220705  for：支持自定义推送类型，邮件、钉钉、企业微信、系统消息-----------

    /**
     * 1查询用户角色信息
     * @param username
     * @return
     */
    Set<String> queryUserRoles(String username);


    /**
     * 2查询用户权限信息
     * @param username
     * @return
     */
    Set<String> queryUserAuths(String username);

    /**
     * 3根据 id 查询数据库中存储的 DynamicDataSourceModel
     *
     * @param dbSourceId
     * @return
     */
    DynamicDataSourceModel getDynamicDbSourceById(String dbSourceId);

    /**
     * 4根据 code 查询数据库中存储的 DynamicDataSourceModel
     *
     * @param dbSourceCode
     * @return
     */
    DynamicDataSourceModel getDynamicDbSourceByCode(String dbSourceCode);

    /**
     * 5根据用户账号查询用户信息
     * @param username
     * @return
     */
    public LoginUser getUserByName(String username);


    /**
     * 6字典表的 翻译
     * @param table
     * @param text
     * @param code
     * @param key
     * @return
     */
    String translateDictFromTable(String table, String text, String code, String key);

    /**
     * 7普通字典的翻译
     * @param code
     * @param key
     * @return
     */
    String translateDict(String code, String key);
    String translateDictReverse(String code, String key);

    /**
     * 8查询数据权限
     * @param component 组件
     * @param username 用户名
     * @param requestPath 前段请求地址
     * @return
     */
    List<SysPermissionDataRuleModel> queryPermissionDataRule(String component, String requestPath, String username);

    /**
     * 8.1查询数据权限
     * @param perms 授权标识，多个逗号分割
     * @param username 用户名
     * @return
     */
    List<SysPermissionDataRuleModel> queryPermissionDataRuleByPerms(String perms, String username, QueryRuleEnum queryMode);

    /**
     * 9查询用户信息
     * @param username
     * @return
     */
    SysUserCacheInfo getCacheUser(String username);

    /**
     * 10获取数据字典
     * @param code
     * @return
     */
    public List<DictModel> queryDictItemsByCode(String code);

    /**
     * 获取有效的数据字典项
     * @param code
     * @return
     */
    public List<DictModel> queryEnableDictItemsByCode(String code);

    /**
     * 13获取表数据字典
     * @param table
     * @param text
     * @param code
     * @return
     */
    List<DictModel> queryTableDictItemsByCode(String table, String text, String code);

    /**
     * 14 普通字典的翻译，根据多个dictCode和多条数据，多个以逗号分割
     * @param dictCodes 例如：user_status,sex
     * @param keys 例如：1,2,0
     * @return
     */
    Map<String, List<DictModel>> translateManyDict(String dictCodes, String keys);

    /**
     * 15 字典表的 翻译，可批量
     * @param table
     * @param text
     * @param code
     * @param keys 多个用逗号分割
     * @return
     */
    List<DictModel> translateDictFromTableByKeys(String table, String text, String code, String keys);

    /**
     * 打包登录用户信息
     *
     * @author Yoko
     * @param sysUserModel 用户登陆实体
     */
    JSONObject packageUserInfo(SysUserModel sysUserModel);

    /**
     * 23查询所有部门
     */
    public List<SysDepartModel> getAllSysDepart(String id, String delFlag);

    /**
     * 添加部门
     *
     * @author Yoko
     * @param model 部门实体
     */
    SysDepartModel addSysDepart(SysDepartModel model);

    /**
     * 更新部门
     *
     * @author Yoko
     * @param model 部门实体
     */
    SysDepartModel editSysDepart(SysDepartModel model);

    /**
     * 删除部门
     *
     * @author Yoko
     * @param model 部门实体
     */
    SysDepartModel deleteSysDepart(SysDepartModel model);

    /**
     * 获取所有用户列表
     *
     * @author Yoko
     * @param id 用户id
     */
    List<SysUserModel> getAllSysUser(String id);

    /**
     * 添加用户
     *
     * @author Yoko
     * @param model 用户实体
     */
    SysUserModel addSysUser(SysUserModel model, String roleIds, String departIds);

    /**
     * 更新用户
     *
     * @author Yoko
     * @param model 用户实体
     */
    SysUserModel editSysUser(SysUserModel model, String roleIds, String departIds);

    /**
     * 删除用户
     *
     * @author Yoko
     * @param model 用户实体
     */
    SysUserModel deleteSysUser(SysUserModel model);

    /**
     * 为用户设置默认的orgCode字段
     */
    void updateSysUserWithDefaultOrgCode();

    /**
     * 获取当前用户的所有权限标识
     *
     * @author Yoko
     * @since 2024/8/16 上午11:00
     * @param username 用户名
     * @param userid 用户id
     * @param permsLimitPrefix 权限前缀
     * @return java.util.List<java.lang.String>
     */
    List<String> queryCurrentUserPerms(String username, String userid, String permsLimitPrefix);

    /**
     * 获取指定角色下的所有用户
     *
     * @author Yoko
     * @since 2024/8/20 09:53
     * @param roleCode 角色编码
     * @return java.util.List<org.jeecg.common.system.vo.SysUserModel>
     */
    List<SysUserModel> getUserModelByRoleCodes(String roleCode);
}
