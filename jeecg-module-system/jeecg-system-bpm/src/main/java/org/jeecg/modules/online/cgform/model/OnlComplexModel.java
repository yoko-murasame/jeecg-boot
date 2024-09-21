package org.jeecg.modules.online.cgform.model;

import lombok.Data;
import org.jeecg.common.system.vo.DictModel;
import org.jeecg.modules.online.cgform.entity.OnlCgformButton;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class OnlComplexModel implements Serializable {
    private static final long b = 1L;
    private String code;
    private String formTemplate;
    private String description;
    private String currentTableName;
    private Integer tableType;
    private String paginationFlag;
    private String checkboxFlag;
    private Integer scrollFlag;
    private List<OnlColumn> columns;
    private List<String> hideColumns;
    private Map<String, List<DictModel>> dictOptions = new HashMap();
    private List<OnlCgformButton> cgButtonList;
    List<HrefSlots> fieldHrefSlots;
    private String enhanceJs;
    private List<b> foreignKeys;
    private String pidField;
    private String hasChildrenField;
    private String textField;
    private String isDesForm;
    private String desFormCode;
    private Integer relationType;

    // 是否循环发起流程
    private Boolean bpmCirculate;

    // 数据初始化JS增强
    private String onlineInitQueryParamGetter;
    // Vue2监听器JS增强
    private String onlineVueWatchJsStr;

    // 是否是视图表
    private Boolean viewTable;

    // 是否隐藏action按钮
    private Boolean hideActionButton;

}
