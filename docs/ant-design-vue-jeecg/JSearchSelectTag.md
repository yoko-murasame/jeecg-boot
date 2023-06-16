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
```

修改历史:
* 2023-06-07: 添加无命中item时,保留输入值的功能
