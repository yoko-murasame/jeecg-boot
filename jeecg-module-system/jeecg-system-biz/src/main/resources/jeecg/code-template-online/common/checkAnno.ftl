<#--检查实体类型，区分是否使用注解-->
<#assign id_required = true>
<#assign annoSize = true>
<#assign annoEmpty = true>
<#assign annoNull = true>
<#assign annoLogic = true>
<#if entityType??>
    <#--entity 需要Logic注解+校验+无需id必填-->
    <#if entityType == 'entity'>
        <#assign id_required = false>
        <#assign annoSize = true>
        <#assign annoEmpty = true>
        <#assign annoNull = true>
        <#assign annoLogic = true>
    </#if>
    <#--VO 无需Logic注解+校验+无需id必填-->
    <#if entityType == 'vo'>
        <#assign id_required = false>
        <#assign annoSize = true>
        <#assign annoEmpty = true>
        <#assign annoNull = true>
        <#assign annoLogic = false>
    </#if>
    <#--add 无需Logic注解+校验+无需id必填-->
    <#if entityType == 'add'>
        <#assign id_required = false>
        <#assign annoSize = true>
        <#assign annoEmpty = true>
        <#assign annoNull = true>
        <#assign annoLogic = false>
    </#if>
    <#--edit 无需Logic注解+校验+需要id必填-->
    <#if entityType == 'edit'>
        <#assign id_required = true>
        <#assign annoSize = true>
        <#assign annoEmpty = true>
        <#assign annoNull = true>
        <#assign annoLogic = false>
    </#if>
    <#--delete 无需Logic注解+无需校验+需要id必填-->
    <#if entityType == 'delete'>
        <#assign id_required = true>
        <#assign annoSize = false>
        <#assign annoEmpty = false>
        <#assign annoNull = false>
        <#assign annoLogic = false>
    </#if>
    <#--query 无需Logic注解+无需校验+无需id必填-->
    <#if entityType == 'query'>
        <#assign id_required = false>
        <#assign annoSize = false>
        <#assign annoEmpty = false>
        <#assign annoNull = false>
        <#assign annoLogic = false>
    </#if>
</#if>
<#--判断是否启用了校验注解-->
<#assign validateEnabled = annoSize || annoEmpty || annoNull>
<#--@ApiModelProperty的必填判断，主键id是否必填根据上文变量走-->
<#assign required_flag = po.nullable == 'N' && po.fieldName != primaryKeyField>
<#if !required_flag && po.fieldName == primaryKeyField>
    <#assign required_flag = id_required>
</#if>
