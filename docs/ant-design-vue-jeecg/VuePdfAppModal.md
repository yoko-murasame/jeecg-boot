[返回](../)

# VuePdfAppModal

PDF在线预览弹窗组件，基于 [vue-pdf-app](https://github.com/sandanat/vue-pdf-app) 封装。

组件路径: [VuePdfAppModal](https://github.com/yoko-murasame/ant-design-vue-jeecg/blob/yoko/src/components/yoko/VuePdfAppModal.vue)

使用示例:

```vue
<template>
  <div>
    <!--PDF预览，组件已默认全局注册-->
    <vue-pdf-app-modal :pdf.sync="pdfUrl" :title.sync="pdfTitle" :download-permission="pdfDownloadPermission" />
  </div>
</template>
<script>
  export default {
    data() {
      return {
        pdfUrl: '', // pdf地址有值时，自动打开pdf预览弹窗，关闭后自动清空
        pdfTitle: '', // pdf弹窗标题
        pdfDownloadPermission: 'KNOWLEDGE_FILE_DOWNLOAD_BUTTON', // pdf下载按钮权限code
      }
    }
  }
</script>
```

修改历史:
* 2023-11-24: 新增组件。
