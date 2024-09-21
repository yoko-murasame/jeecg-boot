<#if po.fieldDbType=='Blob'>
    private transient java.lang.String ${po.fieldName}String;

    private byte[] ${po.fieldName};

    public byte[] get${po.fieldName?cap_first}(){
        if(${po.fieldName}String==null){
            return null;
        }
        try {
            return ${po.fieldName}String.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String get${po.fieldName?cap_first}String(){
        if(${po.fieldName}==null || ${po.fieldName}.length==0){
            return "";
        }
        try {
            return new String(${po.fieldName},"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
<#else>
    <#--检查实体类型，区分是否使用注解-->
    <#include "checkAnno.ftl">
    <#if po.fieldName == primaryKeyField>
    <#--主键-->
    @ApiModelProperty(value = "${po.filedComment}", name = "${po.fieldName}", notes = "${po.filedComment}"<#if required_flag>, required = true</#if>)
        <#if po.fieldDbType == 'string'>
            <#if required_flag>
    @NotEmpty(message = "${po.filedComment}不能为空")
            </#if>
        <#else>
            <#if required_flag>
    @NotNull(message = "${po.filedComment}不能为空")
            </#if>
        </#if>
    <#else>
    <#--非主键-->
    @ApiModelProperty(value = "${po.filedComment}", name = "${po.fieldName}", notes = "${po.filedComment}"<#if required_flag && validateEnabled>, required = true</#if>)
        <#if po.fieldDbType == 'string'>
            <#if po.fieldLength != 0 && annoSize>
    @Size(max = ${po.fieldLength}, message = "${po.filedComment}长度不能超过${po.fieldLength}")
            </#if>
            <#if po.nullable == 'N' && annoEmpty>
    @NotEmpty(message = "${po.filedComment}不能为空")
            </#if>
        <#else>
            <#if po.nullable == 'N' && annoNull>
    @NotNull(message = "${po.filedComment}不能为空")
            </#if>
        </#if>
    </#if>
    <#if po.fieldDbName == 'del_flag' && annoLogic>
    @TableLogic
    </#if>
    private ${po.fieldType} ${po.fieldName};

</#if>
