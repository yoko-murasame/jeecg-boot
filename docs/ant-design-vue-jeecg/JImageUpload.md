[返回](../)

# JImageUpload

图片上传组件
* 优化图片张数限制
* 支持限制图片格式
* 支持限制图片大小
* 支持图片压缩，默认超过3M自动压缩，可自定义

组件路径: [src/components/jeecg/JImageUpload.vue](https://github.com/yoko-murasame/ant-design-vue-jeecg/blob/yoko/src/components/jeecg/JImageUpload.vue)

使用示例:
```vue
<j-image-upload v-model="form.pic" :number="1" :limitWidth='0' :limitHeight='0' :isMultiple='false' ></j-image-upload>
```

修改历史:
* 2023-06-16: Merge Pull Request From [Link](https://github.com/jeecgboot/ant-design-vue-jeecg/pull/2).
