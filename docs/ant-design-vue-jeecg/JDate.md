[返回](../)

# JDate

组件路径: 
* [src/components/dict/JDate.vue](https://github.com/yoko-murasame/ant-design-vue-jeecg/blob/yoko/src/components/jeecg/JDate.vue)

使用示例:
```vue
<a-col :md="8" :sm="8">
<a-form-item label="创建时间" :labelCol="{span: 5}" :wrapperCol="{span: 18, offset: 1}">
  <j-date
      v-model="queryParam.updateTime_begin"
      @change="queryParam.bpmStatus = '3'"
      :showTime="false"
      date-format="YYYY-MM-DD"
      style="width:45%"
      placeholder="请选择开始时间" ></j-date>
  <span style="width: 10px;">~</span>
  <j-date
      v-model="queryParam.updateTime_end"
      @change="queryParam.bpmStatus = '3'"
      :showTime="true"
      date-format="YYYY-MM-DD HH:mm:ss"
      style="width:45%"
      placeholder="请选择结束时间"></j-date>
  <span style="width: 10px;">~</span>
</a-form-item>
</a-col>
```

修改历史:
* 2023-07-21: Add
