[返回](../)

# VuePdfAppModal

PDF在线预览弹窗组件，基于 [vue-pdf-app](https://github.com/sandanat/vue-pdf-app) 封装。
NPM：[vue-pdf-app](https://www.npmjs.com/package/vue-pdf-app)
安装: `yarn add vue-pdf-app`

组件路径: [VuePdfAppModal](https://github.com/yoko-murasame/ant-design-vue-jeecg/blob/yoko/src/components/yoko/VuePdfAppModal.vue)

使用示例:

```vue
<template>
  <div>
    <!--PDF预览，组件已默认全局注册-->
    <!--loading-mode开启显示加载中，会等待pdf渲染完成，但是请注意控制台会有不可控制的异常输出，虽然不影响，自己看情况使用！-->
    <vue-pdf-app-modal
      :pdf.sync="pdfUrl"
      :title.sync="pdfTitle"
      :download-permission="KNOWLEDGE_FILE_DOWNLOAD_BUTTON"
      :width="'70vw'"
      :height="'80vh'"
      :loading-mode="false"
    />
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
* 2023-11-25: 优化样式、显示loading模式。
