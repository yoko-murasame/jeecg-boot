package org.jeecg.modules.technical.service;


import com.alibaba.fastjson.JSONArray;
import org.jeecg.modules.technical.entity.Folder;
import org.jeecg.modules.technical.entity.enums.Enabled;
import org.jeecg.modules.technical.sample.entity.Project;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface FolderService {

    String NULL = "null";
    String BUSINESS_ID = "businessId";
    String PROJECT_ID = "id";
    String BUSINESS_NAME = "businessName";
    String PROJECT_NAME = "name";
    String DEFAULT_FOLDER_NAME = "默认目录";

    List<Map<String, Object>> findFolderIdsByFolderName(String folderName, String folderId, String businessId, String projectId);

    List<Map<String, Object>> findFolderIdsByFileName(String fileName, String fileId, String tags, String businessId, String projectId);

    Folder findOne(String id);

    Folder refreshAllChild(String folderId);

    Folder refreshAllChild(Folder folder);

    Folder refreshAllChild(Folder folder, String folderId);

    List<Folder> findByLevel(Folder folderParam);

    @Transactional(rollbackFor = Exception.class)
    List<Folder> initialStringSubFolder(Folder folderParam, List<String> subFolders);

    @Transactional(rollbackFor = Exception.class)
    void initialJsonSubFolders(Folder folderParam, JSONArray initialFolders, Folder parent, List<Folder> rootFolders);

    List<Folder> findRoot(Folder folderParam);

    List<Folder> findRoot(Folder folderParam, JSONArray initialFolders, List<String> subFolders);

    List<Folder> findChild(String parentId);

    List<Folder> reOrder(String sourceId, String targetId);

    List<Folder> moveFolder(List<String> ids, String targetId);

    @Transactional
    @CacheEvict(cacheNames = {"FolderServiceImpl", "TechnicalCacheService"}, allEntries = true)
    Folder saveAndReturnExistIfSameName(Folder folder, Boolean returnExistIfSameName);

    Folder save(Folder folder);

    //真实删除
    int delete(Folder folder);

    int delete(String id);

    int delete(String id, boolean logic);

    // 逻辑删除
    void changeStatus(String id, Enabled enabled);

    void deleteByBusinessId(String businessId);

    void deleteByBusinessIds(List<String> ids);

    Project existProject(Folder folderParam);

    List<Folder> queryTreeListByFolderTreeName(Folder folderParam, String folderTreeName);

    Folder queryTreeLastNodeByFolderTreeName(Folder folderParam, String folderTreeName);

    List<Folder> queryTreeLastNodeByFolderTreeNames(Folder folderParam, List<String> folderTreeNames);

    List<String> findPath(String folderId);
}
