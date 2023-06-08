# SysUserController

[Yoko分支地址](https://github.com/yoko-murasame/jeecg-boot/tree/yoko)

组件说明: 官方SysUserController接口

组件路径: org.jeecg.modules.system.controller.SysUserController

使用示例:
```js
// 根据角色编码查询用户列表接口
getAction('/sys/user/queryUsersByRoleCode', { roleCode: this.roleCode })
    .then(res => {
        this.dictOptions = res.result.map(e => ({ title: e.realname, value: e.username, text: e.realname }))
    })
    .catch(err => this.$message.warning(err))
```

修改历史:
* 2023-06-07: 添加根据角色编码查询用户列表接口 `/sys/user/queryUsersByRoleCode`
