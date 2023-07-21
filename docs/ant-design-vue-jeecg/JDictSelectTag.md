[返回](../)

# JDictSelectTag

组件路径: 
* [src/components/dict/JDictSelectTag.vue](https://github.com/yoko-murasame/ant-design-vue-jeecg/blob/yoko/src/components/dict/JDictSelectTag.vue)
* [src/components/dict/JMultiSelectTag.vue](https://github.com/yoko-murasame/ant-design-vue-jeecg/blob/yoko/src/components/dict/JMultiSelectTag.vue)

类型：
* select
* radio
* radioButton

使用示例:
```vue
<!--字典配置-->
<a-form-item label="合同类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
  <j-dict-select-tag
      allowClear
      v-decorator="['contractType', {rules: [myRules.empty]}]"
      :trigger-change="true"
      dictCode="contract_type"
      type="select"
      placeholder="请选择合同类型" />
</a-form-item>

<!--自定义列表配置-->
<a-form-item label="会议纪要" :labelCol="labelColSingle" :wrapperCol="wrapperColSingle2">
<j-dict-select-tag
    allowClear
    v-decorator="['jsHyjy', validatorRules.jsHyjy]"
    :trigger-change="true"
    :options="[{value: '1', text: '有', title: '有'}, {value: '0', text: '无', title: '无'}]"
    type="radio"
    @change="onJsHyjyChange"
    placeholder="会议纪要" />
</a-form-item>

<!--多选组件也放这里 有字典类型就不需要-->
<a-form-item label="用印类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
  <!-- <a-input v-decorator="['sealType', {rules: [myRules.empty]}]" placeholder="请输入用印类型" ></a-input> -->
  <j-multi-select-tag
      v-decorator="['sealType', {rules: [myRules.empty]}]"
      placeholder="请选择用印类型"
      :options="{}"
      dictCode="constract_print_type"
      type="checkbox" />
</a-form-item>
```

修改历史:
* 2023-07-21: 单选组件改造，支持自定义options配置
