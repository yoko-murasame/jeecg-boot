[返回](../)

# JEditor

富文本表单组件.

组件路径: [src/components/jeecg/JEditor.vue](https://github.com/yoko-murasame/ant-design-vue-jeecg/blob/yoko/src/components/jeecg/JEditor.vue)

使用示例:
```vue
<j-editor
    v-decorator="['xxx']"
    :default-height="500"
    plugins="lists image link media table textcolor wordcount contextmenu fullscreen"
    toolbar="undo redo |  formatselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | lists link unlink image media table | removeformat | fullscreen"
    :trigger-change="true">
</j-editor>
```

修改历史:
* 2023-06-16: Init Doc. 添加默认高度参数.
* 2024-04-30: JEditor富文本组件支持视频上传、预览功能
