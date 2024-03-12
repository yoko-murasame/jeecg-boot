[返回](../)

# ESign

Canvas签名组件
安装: `yarn add vue-esign`

组件路径: [ESign](https://github.com/yoko-murasame/ant-design-vue-jeecg/blob/yoko/src/components/yoko/ESign.vue)

使用示例:

```vue
<template>
  <div>
    <!--默认已全局注册-->
    <e-sign :disabled="false" v-decorator="['signByQm', validatorRules.signByQm]"></e-sign>
  </div>
</template>
<script>
  export default {
    data() {
      return {
      }
    }
  }
</script>
```

修改历史:
* 2024-02-19: 新增组件。
