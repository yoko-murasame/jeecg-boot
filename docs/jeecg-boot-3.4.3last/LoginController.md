[返回](../)

# LoginController

用户登录接口

组件路径: [org.jeecg.modules.system.controller.LoginController](https://github.com/yoko-murasame/jeecg-boot/blob/yoko-3.4.3last/jeecg-module-system/jeecg-system-biz/src/main/java/org/jeecg/modules/system/controller/LoginController.java)

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
* 2023-07-12: 验证码接口去掉L、I、l、i、0、O、o等易混淆字符
* 2023-06-07: 添加根据角色编码查询用户列表接口 `/sys/user/queryUsersByRoleCode`
