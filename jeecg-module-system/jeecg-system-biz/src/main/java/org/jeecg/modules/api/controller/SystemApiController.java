package org.jeecg.modules.api.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.dto.DataLogDTO;
import org.jeecg.common.api.dto.OnlineAuthDTO;
import org.jeecg.common.api.dto.message.*;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryRuleEnum;
import org.jeecg.common.system.vo.*;
import org.jeecg.common.util.SqlInjectionUtil;
import org.jeecg.modules.system.security.DictQueryBlackListHandler;
import org.jeecg.modules.system.service.ISysUserService;
import org.jeecg.modules.system.service.impl.SysBaseApiImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * 服务化 system模块 对外接口请求类
 * @author: jeecg-boot
 */
@Slf4j
@RestController
@RequestMapping("/sys/api")
public class SystemApiController {

    @Autowired
    private SysBaseApiImpl sysBaseApi;
    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private DictQueryBlackListHandler dictQueryBlackListHandler;


    /**
     * 发送系统消息
     * @param message 使用构造器赋值参数 如果不设置category(消息类型)则默认为2 发送系统消息
     */
    @PostMapping("/sendSysAnnouncement")
    public void sendSysAnnouncement(@RequestBody MessageDTO message){
        sysBaseApi.sendSysAnnouncement(message);
    }

    /**
     * 发送消息 附带业务参数
     * @param message 使用构造器赋值参数
     */
    @PostMapping("/sendBusAnnouncement")
    public void sendBusAnnouncement(@RequestBody BusMessageDTO message){
        sysBaseApi.sendBusAnnouncement(message);
    }

    /**
     * 通过模板发送消息
     * @param message 使用构造器赋值参数
     */
    @PostMapping("/sendTemplateAnnouncement")
    public void sendTemplateAnnouncement(@RequestBody TemplateMessageDTO message){
        sysBaseApi.sendTemplateAnnouncement(message);
    }

    /**
     * 通过模板发送消息 附带业务参数
     * @param message 使用构造器赋值参数
     */
    @PostMapping("/sendBusTemplateAnnouncement")
    public void sendBusTemplateAnnouncement(@RequestBody BusTemplateMessageDTO message){
        sysBaseApi.sendBusTemplateAnnouncement(message);
    }

    /**
     * 通过消息中心模板，生成推送内容
     * @param templateDTO 使用构造器赋值参数
     * @return
     */
    @PostMapping("/parseTemplateByCode")
    public String parseTemplateByCode(@RequestBody TemplateDTO templateDTO){
        return sysBaseApi.parseTemplateByCode(templateDTO);
    }

    /**
     * 根据业务类型busType及业务busId修改消息已读
     */
    @GetMapping("/updateSysAnnounReadFlag")
    public void updateSysAnnounReadFlag(@RequestParam("busType") String busType, @RequestParam("busId")String busId){
        sysBaseApi.updateSysAnnounReadFlag(busType, busId);
    }

    /**
     * 根据用户账号查询用户信息
     * @param username
     * @return
     */
    @GetMapping("/getUserByName")
    public LoginUser getUserByName(@RequestParam("username") String username){
        return sysBaseApi.getUserByName(username);
    }

    /**
     * 根据用户id查询用户信息
     * @param id
     * @return
     */
    @GetMapping("/getUserById")
    LoginUser getUserById(@RequestParam("id") String id){
        return sysBaseApi.getUserById(id);
    }

    /**
     * 通过用户账号查询角色集合
     * @param username
     * @return
     */
    @GetMapping("/getRolesByUsername")
    List<String> getRolesByUsername(@RequestParam("username") String username){
        return sysBaseApi.getRolesByUsername(username);
    }

    /**
     * 通过用户账号查询部门集合
     * @param username
     * @return 部门 id
     */
    @GetMapping("/getDepartIdsByUsername")
    List<String> getDepartIdsByUsername(@RequestParam("username") String username){
        return sysBaseApi.getDepartIdsByUsername(username);
    }

    /**
     * 通过用户账号查询部门 name
     * @param username
     * @return 部门 name
     */
    @GetMapping("/getDepartNamesByUsername")
    List<String> getDepartNamesByUsername(@RequestParam("username") String username){
        return sysBaseApi.getDepartNamesByUsername(username);
    }


    /**
     * 获取数据字典
     * @param code
     * @return
     */
    @GetMapping("/queryDictItemsByCode")
    List<DictModel> queryDictItemsByCode(@RequestParam("code") String code){
        return sysBaseApi.queryDictItemsByCode(code);
    }

    /**
     * 获取有效的数据字典
     * @param code
     * @return
     */
    @GetMapping("/queryEnableDictItemsByCode")
    List<DictModel> queryEnableDictItemsByCode(@RequestParam("code") String code){
        return sysBaseApi.queryEnableDictItemsByCode(code);
    }


    /** 查询所有的父级字典，按照create_time排序 */
    @GetMapping("/queryAllDict")
    List<DictModel> queryAllDict(){
//        try{
//            //睡10秒，gateway网关5秒超时，会触发熔断降级操作
//            Thread.sleep(10000);
//        }catch (Exception e){
//            e.printStackTrace();
//        }

        log.info("--我是jeecg-system服务节点，微服务接口queryAllDict被调用--");
        return sysBaseApi.queryAllDict();
    }

    /**
     * 查询所有分类字典
     * @return
     */
    @GetMapping("/queryAllSysCategory")
    List<SysCategoryModel> queryAllSysCategory(){
        return sysBaseApi.queryAllSysCategory();
    }


    /**
     * 查询所有部门 作为字典信息 id -->value,departName -->text
     * @return
     */
    @GetMapping("/queryAllDepartBackDictModel")
    List<DictModel> queryAllDepartBackDictModel(){
        return sysBaseApi.queryAllDepartBackDictModel();
    }

    /**
     * 获取所有角色 带参
     * roleIds 默认选中角色
     * @return
     */
    @GetMapping("/queryAllRole")
    public List<ComboModel> queryAllRole(@RequestParam(name = "roleIds",required = false)String[] roleIds){
        if(roleIds==null || roleIds.length==0){
            return sysBaseApi.queryAllRole();
        }else{
            return sysBaseApi.queryAllRole(roleIds);
        }
    }

    /**
     * 通过用户账号查询角色Id集合
     * @param username
     * @return
     */
    @GetMapping("/getRoleIdsByUsername")
    public List<String> getRoleIdsByUsername(@RequestParam("username")String username){
        return sysBaseApi.getRoleIdsByUsername(username);
    }

    /**
     * 通过用户账号Id查询角色Id集合
     * @param userId
     * @return
     */
    @GetMapping("/getRoleIdsByUserId")
    public List<String> getRoleIdsByUserId(@RequestParam("userId")String userId){
        return sysBaseApi.getRoleIdsByUserId(userId);
    }

    /**
     * 通过部门编号查询部门id
     * @param orgCode
     * @return
     */
    @GetMapping("/getDepartIdsByOrgCode")
    public String getDepartIdsByOrgCode(@RequestParam("orgCode")String orgCode){
        return sysBaseApi.getDepartIdsByOrgCode(orgCode);
    }

    /**
     * 根据 id 查询数据库中存储的 DynamicDataSourceModel
     *
     * @param dbSourceId
     * @return
     */
    @GetMapping("/getDynamicDbSourceById")
    DynamicDataSourceModel getDynamicDbSourceById(@RequestParam("dbSourceId")String dbSourceId){
        return sysBaseApi.getDynamicDbSourceById(dbSourceId);
    }



    /**
     * 根据部门Id获取部门负责人
     * @param deptId
     * @return
     */
    @GetMapping("/getDeptHeadByDepId")
    public List<String> getDeptHeadByDepId(@RequestParam("deptId") String deptId){
        return sysBaseApi.getDeptHeadByDepId(deptId);
    }

    /**
     * 查找父级部门
     * @param departId
     * @return
     */
    @GetMapping("/getParentDepartId")
    public DictModel getParentDepartId(@RequestParam("departId")String departId){
        return sysBaseApi.getParentDepartId(departId);
    }

    /**
     * 根据 code 查询数据库中存储的 DynamicDataSourceModel
     *
     * @param dbSourceCode
     * @return
     */
    @GetMapping("/getDynamicDbSourceByCode")
    public DynamicDataSourceModel getDynamicDbSourceByCode(@RequestParam("dbSourceCode") String dbSourceCode){
        return sysBaseApi.getDynamicDbSourceByCode(dbSourceCode);
    }

    /**
     * 给指定用户发消息
     * @param userIds
     * @param cmd
     */
    @GetMapping("/sendWebSocketMsg")
    public void sendWebSocketMsg(String[] userIds, String cmd){
        sysBaseApi.sendWebSocketMsg(userIds, cmd);
    }


    /**
     * 根据id获取所有参与用户
     * userIds
     * @return
     */
    @GetMapping("/queryAllUserByIds")
    public List<LoginUser> queryAllUserByIds(@RequestParam("userIds") String[] userIds){
        return sysBaseApi.queryAllUserByIds(userIds);
    }

    /**
     * 查询所有用户 返回ComboModel
     * @return
     */
    @GetMapping("/queryAllUserBackCombo")
    public List<ComboModel> queryAllUserBackCombo(){
        return sysBaseApi.queryAllUserBackCombo();
    }

    /**
     * 分页查询用户 返回JSONObject
     * @return
     */
    @GetMapping("/queryAllUser")
    public JSONObject queryAllUser(@RequestParam(name="userIds",required=false)String userIds, @RequestParam(name="pageNo",required=false) Integer pageNo,@RequestParam(name="pageSize",required=false) int pageSize){
        return sysBaseApi.queryAllUser(userIds, pageNo, pageSize);
    }



    /**
     * 将会议签到信息推动到预览
     * userIds
     * @return
     * @param userId
     */
    @GetMapping("/meetingSignWebsocket")
    public void meetingSignWebsocket(@RequestParam("userId")String userId){
        sysBaseApi.meetingSignWebsocket(userId);
    }

    /**
     * 根据name获取所有参与用户
     * userNames
     * @return
     */
    @GetMapping("/queryUserByNames")
    public List<LoginUser> queryUserByNames(@RequestParam("userNames")String[] userNames){
        return sysBaseApi.queryUserByNames(userNames);
    }

    /**
     * 获取用户的角色集合
     * @param username
     * @return
     */
    @GetMapping("/getUserRoleSet")
    public Set<String> getUserRoleSet(@RequestParam("username")String username){
        return sysBaseApi.getUserRoleSet(username);
    }

    /**
     * 获取用户的权限集合
     * @param username
     * @return
     */
    @GetMapping("/getUserPermissionSet")
    public Set<String> getUserPermissionSet(@RequestParam("username") String username){
        return sysBaseApi.getUserPermissionSet(username);
    }

    //-----

    /**
     * 判断是否有online访问的权限
     * @param onlineAuthDTO
     * @return
     */
    @PostMapping("/hasOnlineAuth")
    public boolean hasOnlineAuth(@RequestBody OnlineAuthDTO onlineAuthDTO){
        return sysBaseApi.hasOnlineAuth(onlineAuthDTO);
    }

    /**
     * 查询用户角色信息
     * @param username
     * @return
     */
    @GetMapping("/queryUserRoles")
    public Set<String> queryUserRoles(@RequestParam("username") String username){
        return sysUserService.getUserRolesSet(username);
    }


    /**
     * 查询用户权限信息
     * @param username
     * @return
     */
    @GetMapping("/queryUserAuths")
    public Set<String> queryUserAuths(@RequestParam("username") String username){
        return sysUserService.getUserPermissionsSet(username);
    }

    /**
     * 通过部门id获取部门全部信息
     */
    @GetMapping("/selectAllById")
    public SysDepartModel selectAllById(@RequestParam("id") String id){
        return sysBaseApi.selectAllById(id);
    }

    /**
     * 根据用户id查询用户所属公司下所有用户ids
     * @param userId
     * @return
     */
    @GetMapping("/queryDeptUsersByUserId")
    public List<String> queryDeptUsersByUserId(@RequestParam("userId") String userId){
        return sysBaseApi.queryDeptUsersByUserId(userId);
    }


    /**
     * 查询数据权限
     * @return
     */
    @GetMapping("/queryPermissionDataRule")
    public List<SysPermissionDataRuleModel> queryPermissionDataRule(@RequestParam("component") String component, @RequestParam("requestPath")String requestPath, @RequestParam("username") String username){
        return sysBaseApi.queryPermissionDataRule(component, requestPath, username);
    }

    /**
     * 查询数据权限
     * @return
     */
    @GetMapping("/queryPermissionDataRuleByPerms")
    public List<SysPermissionDataRuleModel> queryPermissionDataRuleByPerms(@RequestParam("perms") String perms, @RequestParam("username") String username, @RequestParam("queryMode") QueryRuleEnum queryMode){
        return sysBaseApi.queryPermissionDataRuleByPerms(perms, username, queryMode);
    }

    /**
     * 查询用户信息
     * @param username
     * @return
     */
    @GetMapping("/getCacheUser")
    public SysUserCacheInfo getCacheUser(@RequestParam("username") String username){
        return sysBaseApi.getCacheUser(username);
    }

    /**
     * 普通字典的翻译
     * @param code
     * @param key
     * @return
     */
    @GetMapping("/translateDict")
    public String translateDict(@RequestParam("code") String code, @RequestParam("key") String key){
        return sysBaseApi.translateDict(code, key);
    }


    /**
     * 36根据多个用户账号(逗号分隔)，查询返回多个用户信息
     * @param usernames
     * @return
     */
    @RequestMapping("/queryUsersByUsernames")
    List<JSONObject> queryUsersByUsernames(@RequestParam("usernames") String usernames){
        return this.sysBaseApi.queryUsersByUsernames(usernames);
    }

    /**
     * 37根据多个用户id(逗号分隔)，查询返回多个用户信息
     * @param ids
     * @return
     */
    @RequestMapping("/queryUsersByIds")
    List<JSONObject> queryUsersByIds(@RequestParam("ids") String ids){
        return this.sysBaseApi.queryUsersByIds(ids);
    }

    /**
     * 38根据多个部门编码(逗号分隔)，查询返回多个部门信息
     * @param orgCodes
     * @return
     */
    @GetMapping("/queryDepartsByOrgcodes")
    List<JSONObject> queryDepartsByOrgcodes(@RequestParam("orgCodes") String orgCodes){
        return this.sysBaseApi.queryDepartsByOrgcodes(orgCodes);
    }

    /**
     * 39根据多个部门ID(逗号分隔)，查询返回多个部门信息
     * @param ids
     * @return
     */
    @GetMapping("/queryDepartsByIds")
    List<JSONObject> queryDepartsByIds(@RequestParam("ids") String ids){
        return this.sysBaseApi.queryDepartsByIds(ids);
    }

    /**
     * 40发送邮件消息
     * @param email
     * @param title
     * @param content
     */
    @GetMapping("/sendEmailMsg")
    public void sendEmailMsg(@RequestParam("email")String email,@RequestParam("title")String title,@RequestParam("content")String content){
         this.sysBaseApi.sendEmailMsg(email,title,content);
    };
    /**
     * 41 获取公司下级部门和公司下所有用户信息
     * @param orgCode
     */
    @GetMapping("/getDeptUserByOrgCode")
    List<Map> getDeptUserByOrgCode(@RequestParam("orgCode")String orgCode){
       return this.sysBaseApi.getDeptUserByOrgCode(orgCode);
    }

    /**
     * 查询分类字典翻译
     *
     * @param ids 分类字典表id
     * @return
     */
    @GetMapping("/loadCategoryDictItem")
    public List<String> loadCategoryDictItem(@RequestParam("ids") String ids) {
        return sysBaseApi.loadCategoryDictItem(ids);
    }

    /**
     * 根据字典code加载字典text
     *
     * @param dictCode 顺序：tableName,text,code
     * @param keys     要查询的key
     * @return
     */
    @GetMapping("/loadDictItem")
    public List<String> loadDictItem(@RequestParam("dictCode") String dictCode, @RequestParam("keys") String keys) {
        if(!dictQueryBlackListHandler.isPass(dictCode)){
            log.error(dictQueryBlackListHandler.getError());
            return null;
        }
        return sysBaseApi.loadDictItem(dictCode, keys);
    }

    /**
     * 根据字典code查询字典项
     *
     * @param dictCode 顺序：tableName,text,code
     * @param dictCode 要查询的key
     * @return
     */
    @GetMapping("/getDictItems")
    public List<DictModel> getDictItems(@RequestParam("dictCode") String dictCode) {
        if(!dictQueryBlackListHandler.isPass(dictCode)){
            log.error(dictQueryBlackListHandler.getError());
            return null;
        }
        return sysBaseApi.getDictItems(dictCode);
    }

    /**
     * 根据多个字典code查询多个字典项
     *
     * @param dictCodeList
     * @return key = dictCode ； value=对应的字典项
     */
    @RequestMapping("/getManyDictItems")
    public Map<String, List<DictModel>> getManyDictItems(@RequestParam("dictCodeList") List<String> dictCodeList) {
        return sysBaseApi.getManyDictItems(dictCodeList);
    }

    /**
     * 【下拉搜索】
     * 大数据量的字典表 走异步加载，即前端输入内容过滤数据
     *
     * @param dictCode 字典code格式：table,text,code
     * @param keyword  过滤关键字
     * @return
     */
    @GetMapping("/loadDictItemByKeyword")
    public List<DictModel> loadDictItemByKeyword(@RequestParam("dictCode") String dictCode, @RequestParam("keyword") String keyword, @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        if(!dictQueryBlackListHandler.isPass(dictCode)){
            log.error(dictQueryBlackListHandler.getError());
            return null;
        }
        return sysBaseApi.loadDictItemByKeyword(dictCode, keyword, pageSize);
    }

    /**
     * 48 普通字典的翻译，根据多个dictCode和多条数据，多个以逗号分割
     * @param dictCodes
     * @param keys
     * @return
     */
    @GetMapping("/translateManyDict")
    public Map<String, List<DictModel>> translateManyDict(@RequestParam("dictCodes") String dictCodes, @RequestParam("keys") String keys){
        return this.sysBaseApi.translateManyDict(dictCodes, keys);
    }


    /**
     * 获取表数据字典 【接口签名验证】
     * @param table
     * @param text
     * @param code
     * @return
     */
    @GetMapping("/queryTableDictItemsByCode")
    List<DictModel> queryTableDictItemsByCode(@RequestParam("table") String table, @RequestParam("text") String text, @RequestParam("code") String code){
        String str = table+","+text+","+code;
        if(!dictQueryBlackListHandler.isPass(str)){
            log.error(dictQueryBlackListHandler.getError());
            return null;
        }
        return sysBaseApi.queryTableDictItemsByCode(table, text, code);
    }

    /**
     * 查询表字典 支持过滤数据 【接口签名验证】
     * @param table
     * @param text
     * @param code
     * @param filterSql
     * @return
     */
    @GetMapping("/queryFilterTableDictInfo")
    List<DictModel> queryFilterTableDictInfo(@RequestParam("table") String table, @RequestParam("text") String text, @RequestParam("code") String code, @RequestParam("filterSql") String filterSql){
        String str = table+","+text+","+code;
        if(!dictQueryBlackListHandler.isPass(str)){
            log.error(dictQueryBlackListHandler.getError());
            return null;
        }
        String[] arr = new String[]{table, text, code};
        SqlInjectionUtil.filterContent(arr);
        SqlInjectionUtil.specialFilterContentForDictSql(filterSql);
        return sysBaseApi.queryFilterTableDictInfo(table, text, code, filterSql);
    }

    /**
     * 【接口签名验证】
     * 查询指定table的 text code 获取字典，包含text和value
     * @param table
     * @param text
     * @param code
     * @param keyArray
     * @return
     */
    @Deprecated
    @GetMapping("/queryTableDictByKeys")
    public List<String> queryTableDictByKeys(@RequestParam("table") String table, @RequestParam("text") String text, @RequestParam("code") String code, @RequestParam("keyArray") String[] keyArray){
        String str = table+","+text+","+code;
        if(!dictQueryBlackListHandler.isPass(str)){
            log.error(dictQueryBlackListHandler.getError());
            return null;
        }
        return sysBaseApi.queryTableDictByKeys(table, text, code, keyArray);
    }


    /**
     * 字典表的 翻译【接口签名验证】
     * @param table
     * @param text
     * @param code
     * @param key
     * @return
     */
    @GetMapping("/translateDictFromTable")
    public String translateDictFromTable(@RequestParam("table") String table, @RequestParam("text") String text, @RequestParam("code") String code, @RequestParam("key") String key){
        String str = table+","+text+","+code;
        if(!dictQueryBlackListHandler.isPass(str)){
            log.error(dictQueryBlackListHandler.getError());
            return null;
        }
        String[] arr = new String[]{table, text, code, key};
        SqlInjectionUtil.filterContent(arr);
        return sysBaseApi.translateDictFromTable(table, text, code, key);
    }


    /**
     * 【接口签名验证】
     * 49 字典表的 翻译，可批量
     *
     * @param table
     * @param text
     * @param code
     * @param keys  多个用逗号分割
     * @return
     */
    @GetMapping("/translateDictFromTableByKeys")
    public List<DictModel> translateDictFromTableByKeys(@RequestParam("table") String table, @RequestParam("text") String text, @RequestParam("code") String code, @RequestParam("keys") String keys) {
        String str = table+","+text+","+code;
        if(!dictQueryBlackListHandler.isPass(str)){
            log.error(dictQueryBlackListHandler.getError());
            return null;
        }
        return this.sysBaseApi.translateDictFromTableByKeys(table, text, code, keys);
    }

    /**
     * 发送模板信息
     * @param message
     */
    @PostMapping("/sendTemplateMessage")
    public void sendTemplateMessage(@RequestBody MessageDTO message){
        sysBaseApi.sendTemplateMessage(message);
    }

    /**
     * 获取消息模板内容
     * @param code
     * @return
     */
    @GetMapping("/getTemplateContent")
    public String getTemplateContent(@RequestParam("code") String code){
        return this.sysBaseApi.getTemplateContent(code);
    }

    /**
     * 保存数据日志
     * @param dataLogDto
     */
    @PostMapping("/saveDataLog")
    public void saveDataLog(@RequestBody DataLogDTO dataLogDto){
        this.sysBaseApi.saveDataLog(dataLogDto);
    }

    @PostMapping("/addSysFiles")
    public void addSysFiles(@RequestBody SysFilesModel sysFilesModel){this.sysBaseApi.addSysFiles(sysFilesModel);}

    @GetMapping("/getFileUrl")
    public String getFileUrl(@RequestParam(name="fileId") String fileId){
        return this.sysBaseApi.getFileUrl(fileId);
    }

    /**
     * 更新头像
     * @param loginUser
     * @return
     */
    @PutMapping("/updateAvatar")
    public void updateAvatar(@RequestBody LoginUser loginUser){
        this.sysBaseApi.updateAvatar(loginUser);
    }

    /**
     * 向app端 websocket推送聊天刷新消息
     * @param userId
     * @return
     */
    @GetMapping("/sendAppChatSocket")
    public void sendAppChatSocket(@RequestParam(name="userId") String userId){
        this.sysBaseApi.sendAppChatSocket(userId);
    }

    /**
     * 打包登录用户信息
     *
     * @author Yoko
     * @since 2024/8/6 上午9:41
     * @param sysUserModel 登录用户信息
     * @return com.alibaba.fastjson.JSONObject
     */
    @PostMapping("/packageUserInfo")
    public JSONObject packageUserInfo(@RequestBody SysUserModel sysUserModel){
        return this.sysBaseApi.packageUserInfo(sysUserModel);
    }

    /**
     * 查询所有部门
     */
    @GetMapping("/getAllSysDepart")
    public List<SysDepartModel> getAllSysDepart(@RequestParam(required = false, value = "id") String id,
                                                @RequestParam(required = false, value = "delFlag") String delFlag){
        return sysBaseApi.getAllSysDepart(id, delFlag);
    }

    /**
     * 添加部门
     *
     * @author Yoko
     * @since 2024/8/6 上午10:25
     * @param model 部门信息
     * @return org.jeecg.common.system.vo.SysDepartModel
     */
    @PostMapping("/addSysDepart")
    public SysDepartModel addSysDepart(@RequestBody SysDepartModel model) {
        return sysBaseApi.addSysDepart(model);
    }

    @PostMapping("/editSysDepart")
    public SysDepartModel editSysDepart(@RequestBody SysDepartModel model) {
        return sysBaseApi.editSysDepart(model);
    }

    @PostMapping("/deleteSysDepart")
    public SysDepartModel deleteSysDepart(@RequestBody SysDepartModel model) {
        return sysBaseApi.deleteSysDepart(model);
    }

    /**
     * 查询所有用户
     */
    @GetMapping("/getAllSysUser")
    public List<SysUserModel> getAllSysUser(@RequestParam(required = false, value = "id")String id){
        return sysBaseApi.getAllSysUser(id);
    }

    @PostMapping("/addSysUser")
    public SysUserModel addSysUser(@RequestBody SysUserModel model,
                                   @RequestParam("roleIds") String roleIds,
                                   @RequestParam("departIds") String departIds) {
        return sysBaseApi.addSysUser(model, roleIds, departIds);
    }

    @PostMapping("/editSysUser")
    public SysUserModel editSysUser(@RequestBody SysUserModel model,
                                    @RequestParam("roleIds") String roleIds,
                                    @RequestParam("departIds") String departIds) {
        return sysBaseApi.editSysUser(model, roleIds, departIds);
    }

    @PostMapping("/deleteSysUser")
    public SysUserModel deleteSysUser(@RequestBody SysUserModel model) {
        return sysBaseApi.deleteSysUser(model);
    }

    /**
     * 为用户设置默认的orgCode字段
     */
    @PostMapping("/updateSysUserWithDefaultOrgCode")
    public void updateSysUserWithDefaultOrgCode() {
        sysBaseApi.updateSysUserWithDefaultOrgCode();
    }

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
    @GetMapping("/queryCurrentUserPerms")
    List<String> queryCurrentUserPerms(@RequestParam(value = "username", required = false)String username,
                                       @RequestParam(value = "userid", required = false)String userid,
                                       @RequestParam(value = "permsLimitPrefix", required = false)String permsLimitPrefix) {
        return sysBaseApi.queryCurrentUserPerms(username, userid, permsLimitPrefix);
    }

    /**
     * 获取指定角色下的所有用户
     *
     * @author Yoko
     * @since 2024/8/20 10:05
     * @param roleCode 角色编码
     * @return java.util.List<org.jeecg.common.system.vo.SysUserModel>
     */
    @GetMapping("/getUserModelByRoleCodes")
    List<SysUserModel> getUserModelByRoleCodes(@RequestParam(value = "roleCode")String roleCode) {
        return sysBaseApi.getUserModelByRoleCodes(roleCode);
    }

    /**
     * 获取指定用户名的所有用户
     *
     * @author Yoko
     * @param usernames 用户名数组，逗号分隔
     * @return java.util.List<org.jeecg.common.system.vo.SysUserModel>
     */
    @GetMapping("/getUserModelByUsername")
    List<SysUserModel> getUserModelByUsername(@RequestParam(value = "usernames")String usernames) {
        return sysBaseApi.getUserModelByUsername(usernames);
    }

    /**
     * VUEN-2584【issue】平台sql注入漏洞几个问题
     * 部分特殊函数 可以将查询结果混夹在错误信息中，导致数据库的信息暴露
     * @param e
     * @return
     */
    @ExceptionHandler(java.sql.SQLException.class)
    public Result<?> handleSQLException(Exception e){
        String msg = e.getMessage();
        String extractvalue = "extractvalue";
        String updatexml = "updatexml";
        if(msg!=null && (msg.toLowerCase().indexOf(extractvalue)>=0 || msg.toLowerCase().indexOf(updatexml)>=0)){
            return Result.error("校验失败，sql解析异常！");
        }
        return Result.error("校验失败，sql解析异常！" + msg);
    }

}
