package org.jeecg.common.system;

import com.alibaba.fastjson.JSONObject;
import org.apache.dubbo.config.annotation.DubboService;
import org.jeecg.common.api.CommonAPI;
import org.jeecg.common.api.dto.DataLogDTO;
import org.jeecg.common.api.dto.OnlineAuthDTO;
import org.jeecg.common.api.dto.message.*;
import org.jeecg.common.system.vo.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
@DubboService(
        interfaceClass = ISysBaseAPI.class,
        version = "${dubbo.rpc.test.version:2.0.0}",
        timeout = 3000
)
@ConditionalOnMissingClass("org.jeecg.modules.system.service.impl.SysBaseApiImpl")
public interface ISysBaseAPI extends CommonAPI {

    void sendSysAnnouncement(MessageDTO message);

    void sendBusAnnouncement(BusMessageDTO message);

    void sendTemplateAnnouncement(TemplateMessageDTO message);

    void sendBusTemplateAnnouncement(BusTemplateMessageDTO message);

    String parseTemplateByCode(TemplateDTO templateDTO);

    LoginUser getUserById(String id);

    List<String> getRolesByUsername(String username);

    List<String> getDepartIdsByUsername(String username);

    List<String> getDepartNamesByUsername(String username);

    List<DictModel> queryDictItemsByCode(String code);

    List<DictModel> queryEnableDictItemsByCode(String code);

    List<DictModel> queryAllDict();

    List<SysCategoryModel> queryAllSysCategory();

    List<DictModel> queryTableDictItemsByCode(String table, String text, String code);

    List<DictModel> queryAllDepartBackDictModel();

    void updateSysAnnounReadFlag(String busType, String busId);

    List<DictModel> queryFilterTableDictInfo(String table, String text, String code, String filterSql);

    List<String> queryTableDictByKeys(String table, String text, String code, String[] keyArray);

    List<ComboModel> queryAllUserBackCombo();

    JSONObject queryAllUser(String userIds, Integer pageNo, int pageSize);

    List<ComboModel> queryAllRole(String[] roleIds);

    List<String> getRoleIdsByUsername(String username);

    String getDepartIdsByOrgCode(String orgCode);

    List<SysDepartModel> getAllSysDepart();

    DictModel getParentDepartId(String departId);

    List<String> getDeptHeadByDepId(String deptId);

    void sendWebSocketMsg(String[] userIds, String cmd);

    List<LoginUser> queryAllUserByIds(String[] userIds);

    void meetingSignWebsocket(String userId);

    List<LoginUser> queryUserByNames(String[] userNames);

    Set<String> getUserRoleSet(String username);

    Set<String> getUserPermissionSet(String username);

    boolean hasOnlineAuth(OnlineAuthDTO onlineAuthDTO);

    SysDepartModel selectAllById(String id);

    List<String> queryDeptUsersByUserId(String userId);

    // ...

    SysUserCacheInfo getCacheUser(String username);

    List<JSONObject> queryUsersByUsernames(String usernames);

    List<JSONObject> queryUsersByIds(String ids);

    List<JSONObject> queryDepartsByOrgcodes(String orgCodes);

    List<JSONObject> queryDepartsByOrgIds(String ids);

    void sendEmailMsg(String email, String title, String content);

    List<Map> getDeptUserByOrgCode(String orgCode);

    List<String> loadCategoryDictItem(String ids);

    List<String> loadDictItem(String dictCode, String keys);

    List<DictModel> getDictItems(String dictCode);

    Map<String, List<DictModel>> getManyDictItems(List<String> dictCodeList);

    List<DictModel> loadDictItemByKeyword(String dictCode, String keyword, Integer pageSize);

    List<JSONObject> queryDepartsByIds(String ids);

    Map<String, List<DictModel>> translateManyDict(String dictCodes, String keys);

    List<DictModel> translateDictFromTableByKeys(String table, String text, String code, String keys);

    void sendTemplateMessage(MessageDTO message);

    String getTemplateContent(String code);

    void saveDataLog(DataLogDTO dataLogDto);

    void addSysFiles(SysFilesModel sysFilesModel);

    String getFileUrl(String fileId);

    void updateAvatar(LoginUser loginUser);

    void sendAppChatSocket(String userId);

}
