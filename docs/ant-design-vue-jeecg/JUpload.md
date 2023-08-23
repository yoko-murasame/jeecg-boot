[返回](../)

# JUpload

组件路径: [src/components/jeecg/JUpload.vue](https://github.com/yoko-murasame/ant-design-vue-jeecg/blob/yoko/src/components/jeecg/JUpload.vue)

其他说明:

* 视频播放的使用可参考组件: [SakugaContentForm.vue](https://github.com/yoko-murasame/ant-design-vue-jeecg/blob/yoko/src/views/sakuga/modules/SakugaContentForm.vue)

使用示例:
```vue
<j-upload 
  biz-path="上传的路径"
  text="按钮名称"
  file-type="all|image|file"
  accept=".jpg, .jpeg, .png, .gif, .bmp, .svg, .tiff, .webp, image/*"
  split-char=","
  custom-upload-action="自定义上传地址,留空则使用默认接口"
  :do-compress="true"
  :zip-percent="0.7"
  :zip-enable-size="2"
  :disabled="false"
  :button-visible="true"
  :trigger-change="true"
  :return-url="true"
  :number="10"
  @change="e => onChange(e)"
  @showVideo="e => showVideo(e)"
  v-decorator="['xxx', validatorRules.xxx]"
></j-upload>
```

修改历史:
* 2023-06-09: 上传按钮不展示&列表为空时,显示"暂无数据".
* 2023-07-20: 添加多文件上传后自动拼接符号可配置（默认为,）、添加v-viewer插件支持
* 2023-07-21: 添加图片压缩功能
* 2023-08-23: 添加视频播放功能
