[返回](../)

# QueryGenerator

查询条件构造器

组件路径: [后端路径](https://github.com/yoko-murasame/jeecg-boot/blob/yoko-3.4.3last/jeecg-boot-base-core/src/main/java/org/jeecg/common/system/query/QueryGenerator.java)

在`application.yaml`配置中加入即可，如果不设置，默认为：`EQ`

```yaml
jeecg:
  # 默认查询构造器的文本匹配模式: LIKE,EQ,LEFT_LIKE,RIGHT_LIKE
  queryRule: LIKE
```

修改历史:
* 2023.11.22: 根据yaml配置初始化字符类型的查询匹配模式
