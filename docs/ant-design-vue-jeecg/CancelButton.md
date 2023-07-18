[返回](../)

# CancelButton

禁用效果包裹组件, 用于部分业务需求中表单项的控制.

组件路径: [src/components/yoko/CancelButton.vue](https://github.com/yoko-murasame/ant-design-vue-jeecg/blob/yoko/src/components/yoko/CancelButton.vue)

使用示例:
```js
// 在main.js全局注册
import CancelButton from '@/components/yoko/CancelButton'
Vue.use(CancelButton)
```

```vue
<template>
  <j-modal
      :maskClosable="false"
      :title="title"
      :width="width"
      :visible="visible"
      switchFullscreen
      @ok="handleOk"
      :okButtonProps="{ class:{'jee-hidden': disableSubmit} }"
      @cancel="handleCancel"
      cancelText="关闭">
    <template slot="footer">
      <cancel-button :disableSubmit="disableSubmit" key="back" @click="handleCancel"/>
      <a-button type="primary" @click="handleOk" v-if="!disableSubmit">确定</a-button>
    </template>
  </j-modal>
</template>
<script>
import CancelButton from '@/components/yoko/CancelButton'

export default {
  components: {
    CancelButton
  }
}
</script>
```

修改历史:
* 2023-07-18: 新增
