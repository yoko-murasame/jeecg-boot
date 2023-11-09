[返回](../)

# JSearchSelectTag

组件路径: [src/components/dict/JSearchSelectTag.vue](https://github.com/yoko-murasame/ant-design-vue-jeecg/blob/yoko/src/components/dict/JSearchSelectTag.vue)

使用示例:
```vue
<!--标签模式-->
<j-search-select-tag
    v-model="queryParam.tags"
    placeholder="请输入标签"
    dict="technical_file,tags,tags"
    :async="true"
    :pageSize="50"
    mode="tags"
/>
<!--多选模式-->
<j-search-select-tag
    keep-input
    v-model="queryParam.tags"
    placeholder="请输入标签"
    dict="technical_file,tags,tags"
    :async="true"
    :pageSize="50"
    mode="multiple"
/>
<!--keep-input参数表示未命中时也保留值-->
<j-search-select-tag
  keep-input
  v-decorator="['xxx', validatorRules.xxx]"
  placeholder="xxx"
  dict="table_name,text_field,code_field"
  @change="() => {}"
  :async="true"
  :pageSize="50"
/>
<!--手动配置选项数组-->
<a-col :md="4" :sm="4" style="padding-right: 0">
<a-form-item :label="`修改状态`">
  <j-search-select-tag
    keep-input
    v-model="queryParam.bpmStatus"
    placeholder="修改状态"
    :dictOptions="[
                  {text: '负责人审核', value: '2'},
                  {text: '修改已通过', value: '3'},
                  {text: '修改未通过', value: '4'},
                  {text: '负责人更新', value: '5'}
                ]"
    @change="searchQuery"
    :async="false"
    :pageSize="0"
  />
</a-form-item>
</a-col>

<!--其他示例-->
<!--需要指定最大的宽度样式，否则文字过长时会撑开整个组件导致变形-->
<j-search-select-tag
    v-model="queryParam.id"
    placeholder="搜索合同编号"
    dict="deal_service_htgl,contract_code,id"
    :async="true"
    :pageSize="10"
    style="max-width: 300px"
/>

<!--简单的数据项可以不需要系统字典，直接传配置-->
<j-search-select-tag
    v-model="queryParam.isGsMoneySubContractMoney"
    placeholder="请选择"
    :dictOptions="[{value: '1', text: '是'},{value: '0', text: '否'}]"
    :async="false"
    :pageSize="2"
/>

<!--带搜索参数的示例-->
<a-form-item label="机构名称" :labelCol="labelCol2" :wrapperCol="wrapperCol2">
<!--                  <j-search-select-tag placeholder="请搜索机构名称" v-decorator="['jgmc', validatorRules.jgmc]" dict="pgjg_jgmc,bmmc,id"/>-->
<j-search-select-tag :async="true" placeholder="请搜索机构名称" v-decorator="['jgmc', validatorRules.jgmc]" dict="sys_depart,depart_name,org_code,parent_id = '8083bd4568be4a4490f52c2dd3c73509'"/>
</a-form-item>

<!---早期示例->
<a-form-item label="签发人">
		<j-search-select-tag
				placeholder="搜索签发人"
				v-model="queryParam.signBy"
				dict="sys_user,realname,username"
				:async="true"
				:pageSize="5"
		/>
		<!-- <j-select-multi-user v-model="queryParam.signBy" placeholder="请选择签发人" :multi="false" /> -->
<!-- <j-select-user-by-dep
    placeholder="请选择签发人"
    buttontext="选择"
    v-model="queryParam.signBy"
    :multi="false"
/> -->
</a-form-item>

<a-form-item
    class="resetformitem"
    label="你项目部"
    :labelCol="labelCol"
    :wrapperCol="wrapperCol"
    prop="projectCode"
>
<j-search-select-tag
    placeholder="项目名称（下拉选择，可搜索）"
    v-decorator="['projectCode', validatorRules.notEmpty]"
    dict="project,xmmc,project_setup_code"
    :async="true"
/>
</a-form-item>

<template>
  <a-form>
    <a-form-item label="下拉搜索" style="width: 300px">
      <j-search-select-tag
          placeholder="请做出你的选择"
          v-model="selectValue"
          :dictOptions="dictOptions">
      </j-search-select-tag>
      {{ selectValue }}
    </a-form-item>

    <a-form-item label="异步加载" style="width: 300px">
      <j-search-select-tag
          placeholder="请做出你的选择"
          v-model="asyncSelectValue"
          dict="sys_depart,depart_name,id"
          :async="true">
      </j-search-select-tag>
      {{ asyncSelectValue }}
    </a-form-item>
  </a-form >
</template>

<script>
import JSearchSelectTag from '@/components/dict/JSearchSelectTag'
export default {
  components: {JSearchSelectTag},
  data() {
    return {
      selectValue:"",
      asyncSelectValue:"",
      dictOptions:[{
        text:"选项一",
        value:"1"
      },{
        text:"选项二",
        value:"2"
      },{
        text:"选项三",
        value:"3"
      }]
    }
  }
}
</script>
```

| 参数         | 类型    | 必填 | 说明                                                         |
| :----------- | :------ | :--- | :----------------------------------------------------------- |
| placeholder  | string  |      | placeholder                                                  |
| disabled     | Boolean |      | 是否禁用                                                     |
| dict         | string  |      | 表名,显示字段名,存储字段名拼接而成的字符串,如果提供了dictOptions参数 则此参数可不填 |
| dictOptions  | Array   |      | 多选项,如果dict参数未提供,可以设置此参数加载多选项           |
| async        | Boolean |      | 是否支持异步加载,设置成true,则通过输入的内容加载远程数据,否则在本地过滤数据,默认false |
| popContainer | string  |      | 父节点对应的CSS 选择器,内部使用`document.querySelector`选择父节点，如设置`.pnode`,则找到有class为pnode的节点然后渲染下拉框 |
| pageSize     | number  |      | 当async设置为true时有效，表示异步查询时，每次获取数据的数量，默认10 |

修改历史:
* 2023-06-07: 添加无命中item时,保留输入值的功能
