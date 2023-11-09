package org.jeecg.modules.technical.sample.cache;

import cn.hutool.core.collection.IterUtil;
import org.jeecg.common.api.CommonAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 中间缓存件
 *
 * @author Yoko
 * @date 2021/12/8 9:37
 */
@RestController("TechnicalSampleProjectCacheService")
@RequestMapping("/project/cache")
@Service
@CacheConfig(cacheNames = "ProjectCacheService#")
public class ProjectCacheService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CommonAPI commonAPI;


    /**
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @description 查询指定表的数据，用于项目预警列表查询
     */
    @Cacheable(key = "'queryForList#' + #tableName + '#' + #projectId")
    public List<Map<String, Object>> queryForList(String tableName, String projectId) {
        try {
            String listSql = "SELECT * FROM " + tableName + " WHERE project_id='" + projectId + "'" + " ORDER BY create_time DESC";
            return jdbcTemplate.queryForList(listSql);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @Cacheable(key = "'queryForList#' + #sql")
    public List<Map<String, Object>> queryForList(String sql) {
        return jdbcTemplate.queryForList(sql);
    }

    /**
     * @author Yoko
     * @date 2022/1/24 9:50
     * @param projectId
     * @description 获取与业务id绑定的附件数量信息
     * @return java.util.Map<java.lang.String,java.util.Map<java.lang.String,java.lang.Object>>
     */
    @Cacheable(key = "'queryAttachmentStatistic#' + #projectId")
    public Map<String, Map<String, Object>> queryAttachmentStatistic(String projectId) {
        Map<String, Map<String, Object>> result = new HashMap<>(0);
        try {
            String listSql = "SELECT fd.business_id, fd.project_id, fd.project_name, COUNT(fl.id) AS attach_num " +
                    "FROM technical_file fl " +
                    "LEFT JOIN technical_folder fd " +
                    "ON fl.folder_id = fd.id " +
                    "WHERE fd.business_id IS NOT NULL AND fl.enabled = 1 AND fd.enabled = 1 " +
                    (StringUtils.hasText(projectId) ? String.format("AND fd.project_id=%s ", projectId) : "") +
                    "GROUP BY fd.business_id,fd.project_id, fd.project_name ORDER BY fd.business_id DESC";
            List<Map<String, Object>> list = jdbcTemplate.queryForList(listSql);
            // 转换成business_id的map
            // Map<String, List<Map<String, Object>>> result = IterUtil.toListMap(list, item -> (String) item.get("business_id"), item -> item);
            result = IterUtil.toMap(list, item -> (String) item.get("business_id"));
            return result;
        } catch (Exception e) {
            return result;
        }
    }

    /**
     * @return java.lang.String
     * @description 翻译系统字典
     */
    @Cacheable
    public String translateDict(String table, String text, String code, String key, String defaultValue) {
        if (!StringUtils.hasText(key)) {
            return defaultValue;
        }
        if (StringUtils.hasText(table)) {
            defaultValue = commonAPI.translateDictFromTable(table, text, code, key);
        } else {
            defaultValue = commonAPI.translateDict(code, key);
        }
        return defaultValue;
    }

    @Cacheable
    public String translateDict(String code, String key, String defaultValue) {
        return translateDict(null, null, code, key, defaultValue);
    }

}
