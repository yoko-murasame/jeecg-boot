[返回](./)

组件说明: 官方JUpload组件

组件路径: [src/components/jeecg/JUpload.vue](https://github.com/yoko-murasame/ant-design-vue-jeecg/blob/yoko/src/components/jeecg/JUpload.vue)

使用示例:
```vue
<j-upload 
  bizPath="业务路径"
  text="按钮名称"
  file-type="all|image|file"
  accept=".jpg, .jpeg, .png, .gif, .bmp, .svg, .tiff, .webp"
  :disabled="false"
  :buttonVisible="true"
  :trigger-change="true"
  :returnUrl="true"
  :number="10"
  @change="e => onChange(e)"
  v-decorator="['xxx', validatorRules.xxx]"
></j-upload>
```

修改历史:
* 2023-06-09: 上传按钮不展示&列表为空时,显示"暂无数据".
