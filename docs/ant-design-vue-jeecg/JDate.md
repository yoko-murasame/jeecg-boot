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
  <j-date
      date-mode="date"
      date-format="YYYY-MM-DD HH:mm:ss"
      :show-time="true"
      placeholder="请选择节点时间"
      v-decorator="['stageTime', validatorRules.stageTime]"
      :trigger-change="true"
      style="width: 100%"/>
</a-form-item>
</a-col>
<!--放一下a-date的使用示例-->
<a-date-picker placeholder="请选择统计日期" v-model="queryParam.stageTime" valueFormat="YYYY-MM-DD" format="YYYY-MM-DD"/>
<!--最完整的使用模式-可切换年月日、时间-->
<a-date-picker
    :mode="pickerMode"
    value-format="YYYY-MM-DD HH:mm:ss"
    format="YYYY-MM-DD HH:mm:ss"
    :show-time="true"
    :open="openPicker"
    placeholder="请选择节点时间"
    @change="onDateChange"
    @openChange="e => openPicker = e"
    @panelChange="onPanelChange"
    ref="datePicker"
    v-decorator="['stageTime', validatorRules.stageTime]"
    style="width: 100%"/>
<script>
  export default {
    data() {
      return {
        openPicker: false,
        pickerMode: 'date'
      }
    },
    methods: {
      onDateChange(e) {
        console.log(e)
      },
      onPanelChange(e, mode) {
        console.log(e, mode)
        // this.openPicker = false
        // 修复切换年月后无法主动更新的问题
        this.form.setFieldsValue({ stageTime: e.format(e._f) })
        this.pickerMode = mode
        // this.$nextTick(() => this.openPicker = true)
      }
    }
  }
</script>
```

修改历史:
* 2023-07-21: Add
