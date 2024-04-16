<#--检查实际的ID主键值，规则：1.等于或以ID结尾（默认为id）2.数据库字段名不以外键命名开头-->
<#assign relKeyPrefix = "rel_">
<#--默认初始化为jeecg_config.properties中配置的id-->
<#assign primaryKeyField = tableVo.extendParams.defaultPrimaryKeyDbField>
<#assign primaryKeyDbField = tableVo.extendParams.defaultPrimaryKeyDbField>
<#if po.fieldDbName?lower_case?ends_with(primaryKeyDbField?lower_case) && !po.fieldDbName?lower_case?starts_with(relKeyPrefix?lower_case)>
    <#if po.isKey == "Y">
        <#assign primaryKeyField = po.fieldName>
        <#assign primaryKeyDbField = po.fieldDbName>
        <#if relKeyCount??>
            <#assign relKeyCount = relKeyCount + 1>
        </#if>
    </#if>
</#if>
