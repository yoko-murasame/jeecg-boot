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
    <#--@ApiModelProperty的必填判断，主键id是否必填根据上文变量走-->
    <#assign required_flag = po.nullable == 'N' && po.fieldName != primaryKeyField>
    <#if !required_flag && po.fieldName == primaryKeyField && id_required??>
      <#assign required_flag = id_required>
    </#if>
    @ApiModelProperty(value = "${po.filedComment}", name = "${po.fieldName}", notes = "${po.filedComment}"<#if required_flag>, required = true</#if>)
    <#if po.fieldDbType == 'string'>
        <#if po.fieldLength != 0 && po.fieldName != primaryKeyField>
    @Size(max = ${po.fieldLength}, message = "${po.filedComment}长度不能超过${po.fieldLength}")
        </#if>
        <#if po.nullable == 'N' && po.fieldName != primaryKeyField>
    @NotEmpty(message = "${po.filedComment}不能为空")
        </#if>
    <#else>
        <#if po.nullable == 'N' && po.fieldName != primaryKeyField>
    @NotNull(message = "${po.filedComment}不能为空")
        </#if>
    </#if>
  <#if po.fieldDbName == 'del_flag'>
    @TableLogic
  </#if>
    private ${po.fieldType} ${po.fieldName};

</#if>
