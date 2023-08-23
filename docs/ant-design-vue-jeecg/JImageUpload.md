[返回](../)

# JImageUpload

图片上传组件
* 优化图片张数限制
* 支持限制图片格式
* 支持限制图片大小
* 支持图片压缩，默认超过3M自动压缩，可自定义

组件路径:
* [src/components/jeecg/JImageUpload.vue](https://github.com/yoko-murasame/ant-design-vue-jeecg/blob/yoko/src/components/jeecg/JImageUpload.vue)
* [src/components/online/autoform/view/ImageWidget.vue](https://github.com/yoko-murasame/ant-design-vue-jeecg/blob/yoko/src/components/online/autoform/view/ImageWidget.vue)

使用示例:
```vue
<j-image-upload
    biz-path="上传的路径"
    text="按钮名称"
    split-char=","
    custom-upload-action="自定义上传地址,留空则使用默认接口"
    v-model="form.pic"
    :do-compress="true"
    :zip-percent="0.7"
    :zip-enable-size="2"
    :number="0"
    :limit-size="10"
    :limitWidth="0"
    :limitHeight="0"
    :isMultiple="false">
</j-image-upload>
<!--在列表中作为展示组件时，隐藏上传按钮，并配置oss图片压缩，限制最大宽度90vh，限制最大展示数量20个-->
<j-image-upload
    :disabled="true"
    :value="text"
    :button-visible="false"
    :visible-width="'90vh'"
    :visible-number="20"
    url-suffix="x-oss-process=image/resize,w_120">
</j-image-upload>
```

修改历史:
* 2023-06-16: Merge Pull Request From [Link](https://github.com/jeecgboot/ant-design-vue-jeecg/pull/2).
* 2023-07-20: 添加v-viewer插件支持、图片压缩测试
* 2023-07-21: 封装图片压缩功能，online表单组件（ImageWidget）相关Bug处理
* 2023-08-23: 添加隐藏上传按钮功能，添加oss图片压缩后缀配置功能，添加限制展示时最大宽度、最大数量配置，添加oss压缩参数限制后预览中自动去除
