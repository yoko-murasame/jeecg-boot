# JSearchSelectTagWithRoleCode

搜索字典扩展组件: 根据用户角色code获取字典选项

路径: src/components/dict/JSearchSelectTag.vue

修改历史:
* 2023-06-07: 新增

使用示例:
```vue
<j-search-select-tag-with-role-code
    role-code="角色编码"
    v-decorator="['xxx', validatorRules.xxx]"
    placeholder="xxx"
/>

<j-search-select-tag-with-role-code
    role-code="角色编码"
    v-model="xxx"
    placeholder="xxx"
/>

import JSearchSelectTagWithRoleCode from '@comp/dict/JSearchSelectTagWithRoleCode.vue';
components: { JSearchSelectTagWithRoleCode }
```
