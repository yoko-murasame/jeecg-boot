// package org.jeecg.common.system.api.rpc;
//
// import com.alibaba.fastjson.JSONObject;
// import lombok.extern.slf4j.Slf4j;
// import org.apache.dubbo.config.annotation.DubboReference;
// import org.jeecg.common.api.dto.DataLogDTO;
// import org.jeecg.common.api.dto.OnlineAuthDTO;
// import org.jeecg.common.api.dto.message.*;
// import org.jeecg.common.system.api.ISysBaseAPI;
// import org.jeecg.common.system.vo.*;
// import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
// import org.springframework.stereotype.Service;
//
// import java.util.List;
// import java.util.Map;
// import java.util.Set;
//
// /**
//  * Dubbo基础服务消费者
//  */
// @Slf4j
// @Service
// @ConditionalOnMissingClass("org.jeecg.modules.system.service.impl.SysBaseApiImpl")
// public class SysBaseApiImplConsumer implements ISysBaseAPI {
//
// 	@DubboReference(interfaceClass = ISysBaseAPI.class)
// 	private ISysBaseAPI sysBaseApi;
//
// 	@Override
// 	public Set<String> queryUserRoles(String username) {
// 		return sysBaseApi.queryUserRoles(username);
// 	}
//
// 	@Override
// 	public Set<String> queryUserAuths(String username) {
// 		return sysBaseApi.queryUserAuths(username);
// 	}
//
// 	@Override
// 	public DynamicDataSourceModel getDynamicDbSourceById(String dbSourceId) {
// 		return sysBaseApi.getDynamicDbSourceById(dbSourceId);
// 	}
//
// 	@Override
// 	public DynamicDataSourceModel getDynamicDbSourceByCode(String dbSourceCode) {
// 		return sysBaseApi.getDynamicDbSourceByCode(dbSourceCode);
// 	}
//
// 	@Override
// 	public LoginUser getUserByName(String username) {
// 		return sysBaseApi.getUserByName(username);
// 	}
//
// 	@Override
// 	public String translateDictFromTable(String table, String text, String code, String key) {
// 		return sysBaseApi.translateDictFromTable(table, text, code, key);
// 	}
//
// 	@Override
// 	public String translateDict(String code, String key) {
// 		return sysBaseApi.translateDict(code, key);
// 	}
//
// 	@Override
// 	public List<SysPermissionDataRuleModel> queryPermissionDataRule(String component, String requestPath, String username) {
// 		return sysBaseApi.queryPermissionDataRule(component, requestPath, username);
// 	}
//
// 	@Override
// 	public SysUserCacheInfo getCacheUser(String username) {
// 		return sysBaseApi.getCacheUser(username);
// 	}
//
// 	@Override
// 	public List<DictModel> queryDictItemsByCode(String code) {
// 		return sysBaseApi.queryDictItemsByCode(code);
// 	}
//
// 	@Override
// 	public List<DictModel> queryEnableDictItemsByCode(String code) {
// 		return sysBaseApi.queryEnableDictItemsByCode(code);
// 	}
//
// 	@Override
// 	public List<DictModel> queryTableDictItemsByCode(String table, String text, String code) {
// 		return sysBaseApi.queryTableDictItemsByCode(table, text, code);
// 	}
//
// 	@Override
// 	public Map<String, List<DictModel>> translateManyDict(String dictCodes, String keys) {
// 		return sysBaseApi.translateManyDict(dictCodes, keys);
// 	}
//
// 	@Override
// 	public List<DictModel> translateDictFromTableByKeys(String table, String text, String code, String keys) {
// 		return sysBaseApi.translateDictFromTableByKeys(table, text, code, keys);
// 	}
//
// 	@Override
// 	public void sendSysAnnouncement(MessageDTO message) {
// 		sysBaseApi.sendSysAnnouncement(message);
// 	}
//
// 	@Override
// 	public void sendBusAnnouncement(BusMessageDTO message) {
// 		sysBaseApi.sendBusAnnouncement(message);
// 	}
//
// 	@Override
// 	public void sendTemplateAnnouncement(TemplateMessageDTO message) {
// 		sysBaseApi.sendTemplateAnnouncement(message);
// 	}
//
// 	@Override
// 	public void sendBusTemplateAnnouncement(BusTemplateMessageDTO message) {
// 		sysBaseApi.sendBusTemplateAnnouncement(message);
// 	}
//
// 	@Override
// 	public String parseTemplateByCode(TemplateDTO templateDTO) {
// 		return sysBaseApi.parseTemplateByCode(templateDTO);
// 	}
//
// 	@Override
// 	public void sendTemplateMessage(MessageDTO message) {
// 		sysBaseApi.sendTemplateMessage(message);
// 	}
//
// 	@Override
// 	public String getTemplateContent(String templateCode) {
// 		return sysBaseApi.getTemplateContent(templateCode);
// 	}
//
// 	@Override
// 	public LoginUser getUserById(String id) {
// 		return sysBaseApi.getUserById(id);
// 	}
//
// 	@Override
// 	public List<String> getRolesByUsername(String username) {
// 		return sysBaseApi.getRolesByUsername(username);
// 	}
//
// 	@Override
// 	public List<String> getDepartIdsByUsername(String username) {
// 		return sysBaseApi.getDepartIdsByUsername(username);
// 	}
//
// 	@Override
// 	public List<String> getDepartNamesByUsername(String username) {
// 		return sysBaseApi.getDepartNamesByUsername(username);
// 	}
//
// 	@Override
// 	public List<DictModel> queryAllDict() {
// 		return sysBaseApi.queryAllDict();
// 	}
//
// 	@Override
// 	public List<SysCategoryModel> queryAllSysCategory() {
// 		return sysBaseApi.queryAllSysCategory();
// 	}
//
// 	@Override
// 	public List<DictModel> queryAllDepartBackDictModel() {
// 		return sysBaseApi.queryAllDepartBackDictModel();
// 	}
//
// 	@Override
// 	public void updateSysAnnounReadFlag(String busType, String busId) {
// 		sysBaseApi.updateSysAnnounReadFlag(busType, busId);
// 	}
//
// 	@Override
// 	public List<DictModel> queryFilterTableDictInfo(String table, String text, String code, String filterSql) {
// 		return sysBaseApi.queryFilterTableDictInfo(table, text, code, filterSql);
// 	}
//
// 	@Override
// 	public List<String> queryTableDictByKeys(String table, String text, String code, String[] keyArray) {
// 		return sysBaseApi.queryTableDictByKeys(table, text, code, keyArray);
// 	}
//
// 	@Override
// 	public List<ComboModel> queryAllUserBackCombo() {
// 		return sysBaseApi.queryAllUserBackCombo();
// 	}
//
// 	@Override
// 	public JSONObject queryAllUser(String userIds, Integer pageNo, Integer pageSize) {
// 		return sysBaseApi.queryAllUser(userIds, pageNo, pageSize);
// 	}
//
// 	@Override
// 	public List<ComboModel> queryAllRole() {
// 		return sysBaseApi.queryAllRole();
// 	}
//
// 	@Override
// 	public List<ComboModel> queryAllRole(String[] roleIds) {
// 		return sysBaseApi.queryAllRole(roleIds);
// 	}
//
// 	@Override
// 	public List<String> getRoleIdsByUsername(String username) {
// 		return sysBaseApi.getRoleIdsByUsername(username);
// 	}
//
// 	@Override
// 	public String getDepartIdsByOrgCode(String orgCode) {
// 		return sysBaseApi.getDepartIdsByOrgCode(orgCode);
// 	}
//
// 	@Override
// 	public List<SysDepartModel> getAllSysDepart() {
// 		return sysBaseApi.getAllSysDepart();
// 	}
//
// 	@Override
// 	public DictModel getParentDepartId(String departId) {
// 		return sysBaseApi.getParentDepartId(departId);
// 	}
//
// 	@Override
// 	public List<String> getDeptHeadByDepId(String deptId) {
// 		return sysBaseApi.getDeptHeadByDepId(deptId);
// 	}
//
// 	@Override
// 	public void sendWebSocketMsg(String[] userIds, String cmd) {
// 		sysBaseApi.sendWebSocketMsg(userIds, cmd);
// 	}
//
// 	@Override
// 	public List<LoginUser> queryAllUserByIds(String[] userIds) {
// 		return sysBaseApi.queryAllUserByIds(userIds);
// 	}
//
// 	@Override
// 	public void meetingSignWebsocket(String userId) {
// 		sysBaseApi.meetingSignWebsocket(userId);
// 	}
//
// 	@Override
// 	public List<LoginUser> queryUserByNames(String[] userNames) {
// 		return sysBaseApi.queryUserByNames(userNames);
// 	}
//
// 	@Override
// 	public Set<String> getUserRoleSet(String username) {
// 		return sysBaseApi.getUserRoleSet(username);
// 	}
//
// 	@Override
// 	public Set<String> getUserPermissionSet(String username) {
// 		return sysBaseApi.getUserPermissionSet(username);
// 	}
//
// 	@Override
// 	public boolean hasOnlineAuth(OnlineAuthDTO onlineAuthDTO) {
// 		return sysBaseApi.hasOnlineAuth(onlineAuthDTO);
// 	}
//
// 	@Override
// 	public SysDepartModel selectAllById(String id) {
// 		return sysBaseApi.selectAllById(id);
// 	}
//
// 	@Override
// 	public List<String> queryDeptUsersByUserId(String userId) {
// 		return sysBaseApi.queryDeptUsersByUserId(userId);
// 	}
//
// 	@Override
// 	public List<JSONObject> queryUsersByUsernames(String usernames) {
// 		return sysBaseApi.queryUsersByUsernames(usernames);
// 	}
//
// 	@Override
// 	public List<JSONObject> queryUsersByIds(String ids) {
// 		return sysBaseApi.queryUsersByIds(ids);
// 	}
//
// 	@Override
// 	public List<JSONObject> queryDepartsByOrgcodes(String orgCodes) {
// 		return sysBaseApi.queryDepartsByOrgcodes(orgCodes);
// 	}
//
// 	@Override
// 	public List<JSONObject> queryDepartsByIds(String ids) {
// 		return sysBaseApi.queryDepartsByIds(ids);
// 	}
//
// 	@Override
// 	public void sendEmailMsg(String email, String title, String content) {
// 		sysBaseApi.sendEmailMsg(email, title, content);
// 	}
//
// 	@Override
// 	public List<Map> getDeptUserByOrgCode(String orgCode) {
// 		return sysBaseApi.getDeptUserByOrgCode(orgCode);
// 	}
//
// 	@Override
// 	public List<String> loadCategoryDictItem(String ids) {
// 		return sysBaseApi.loadCategoryDictItem(ids);
// 	}
//
// 	@Override
// 	public List<String> loadDictItem(String dictCode, String keys) {
// 		return sysBaseApi.loadDictItem(dictCode, keys);
// 	}
//
// 	@Override
// 	public List<DictModel> getDictItems(String dictCode) {
// 		return sysBaseApi.getDictItems(dictCode);
// 	}
//
// 	@Override
// 	public Map<String, List<DictModel>> getManyDictItems(List<String> dictCodeList) {
// 		return sysBaseApi.getManyDictItems(dictCodeList);
// 	}
//
// 	@Override
// 	public List<DictModel> loadDictItemByKeyword(String dictCode, String keyword, Integer pageSize) {
// 		return sysBaseApi.loadDictItemByKeyword(dictCode, keyword, pageSize);
// 	}
//
// 	@Override
// 	public void saveDataLog(DataLogDTO dataLogDto) {
// 		sysBaseApi.saveDataLog(dataLogDto);
// 	}
//
// 	@Override
// 	public void addSysFiles(SysFilesModel sysFilesModel) {
// 		sysBaseApi.addSysFiles(sysFilesModel);
// 	}
//
// 	@Override
// 	public String getFileUrl(String fileId) {
// 		return sysBaseApi.getFileUrl(fileId);
// 	}
//
// 	@Override
// 	public void updateAvatar(LoginUser loginUser) {
// 		sysBaseApi.updateAvatar(loginUser);
// 	}
//
// 	@Override
// 	public void sendAppChatSocket(String userId) {
// 		sysBaseApi.sendAppChatSocket(userId);
// 	}
//
// 	@Override
// 	public String translateDictReverse(String code, String key) {
// 		return sysBaseApi.translateDictReverse(code, key);
// 	}
//
// 	@Override
// 	public List<SysCategoryModel> queryAllDSysCategory() {
// 		return sysBaseApi.queryAllDSysCategory();
// 	}
//
// }
