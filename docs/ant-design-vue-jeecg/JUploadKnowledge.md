[返回](../)

# JUploadKnowledge

组件路径: [src/components/yoko/JUploadKnowledge.vue](https://github.com/yoko-murasame/ant-design-vue-jeecg/blob/yoko/src/components/yoko/JUploadKnowledge.vue)

功能说明:

* 向下兼容所有上传组件功能
* 支持任意层级知识库路径配置
* 自动上传文件至对应知识库目录
* 打标签功能
* 支持管理包含的、新上传的知识库文件
* 支持文件去重，业务表中上传同样的文件将自动覆盖，仅保存一份
* todo 支持知识库回选
* 组件的示例使用可以参考这个模块: [SakugaContentForm.vue](https://github.com/yoko-murasame/ant-design-vue-jeecg/blob/yoko/src/views/sakuga/modules/SakugaContentForm.vue)

特殊参数:

* knowledge-path: 知识库对应的目录层级, 以逗号分隔，如：默认目录,测试1,ceshi3,ceshi3-1,ceshi3-1-1
* show-tags-dialog: 是否在上传完成后提示用户打标签，默认为true，在设置knowledge-path后且存在路径时生效。

使用示例:

```vue
<j-upload-knowledge
  knowledge-path="默认目录,测试1,ceshi3,ceshi3-1,ceshi3-1-1"
  :knowledge-path-auto-init="'当关联知识库路径不存在时，是否自动初始化，默认为false'"
  :business-id="'业务主键，可选，用于筛选关联目录'"
  :project-id="'项目主键，可选，用于筛选关联目录'"
  :show-tags-dialog="'是否打开标签管理弹窗，默认为true'"
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
></j-upload-knowledge>
```

修改历史:
* 2023-10-24: 新增知识库联动上传组件
* 2024-02-19: 添加关联目录双主键筛选
