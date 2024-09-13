package org.jeecg.modules.online.cgform.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@TableName("onl_cgform_head")
@Data
public class OnlCgformHead implements Serializable {
    private static final long serialVersionUID = 7730149250640283903L;
    @TableId(
            type = IdType.ASSIGN_ID
    )
    private String id;
    private String tableName;
    private Integer tableType;
    private Integer tableVersion;
    private String tableTxt;
    private String isCheckbox;
    private String isDbSynch;
    private String isPage;
    private String isTree;
    private String idSequence;
    private String idType;
    private String queryMode;
    private Integer relationType;
    private String subTableStr;
    private Integer tabOrderNum;
    private String treeParentIdField;
    private String treeIdField;
    private String treeFieldname;
    private String formCategory;
    private String formTemplate;
    private String themeTemplate;
    private String formTemplateMobile;
    private String updateBy;
    @JsonFormat(
            timezone = "GMT+8",
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private Date updateTime;
    private String createBy;
    @JsonFormat(
            timezone = "GMT+8",
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private Date createTime;
    private Integer copyType;
    private Integer copyVersion;
    private String physicId;
    private transient Integer hascopy;
    private Integer scroll;
    private transient String taskId;
    private String isDesForm;
    private String desFormCode;
    /**
     * 数据权限标识，在菜单配置模块，新增按钮/权限标识后，在标识里配置数据权限规则，将自动带入全局数据权限SQL
     *
     * @author Yoko
     * @since 2024/8/15 下午7:36
     */
    private String dataRulePerms;

    /**
     * 数据初始化JS增强
     *
     * @author Yoko
     * @since 2024/8/28 10:12
     */
    private String onlineInitQueryParamGetter;

    /**
     * Vue2监听器JS增强
     *
     * @author Yoko
     * @since 2024/8/29 15:59
     */
    private String onlineVueWatchJsStr;

    /**
     * 是否是视图表
     */
    private Boolean viewTable;

    /**
     * 是否隐藏action按钮
     */
    private Boolean hideActionButton;

}
