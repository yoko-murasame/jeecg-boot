[返回](../)

# JSearchSelectTag

组件路径: [src/components/dict/JSearchSelectTag.vue](https://github.com/yoko-murasame/ant-design-vue-jeecg/blob/yoko/src/components/dict/JSearchSelectTag.vue)

使用示例:
```vue
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
```

修改历史:
* 2023-06-07: 添加无命中item时,保留输入值的功能
