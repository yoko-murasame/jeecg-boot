[返回](../)

# Shiro鉴权扩展

实现功能:
* 根据配置开放第三方接口简单鉴权，目前仅通过name和key鉴权，可自行扩展

组件路径: [shiro.third](https://github.com/yoko-murasame/jeecg-boot/blob/yoko-3.4.3last/jeecg-boot-base-core/src/main/java/org/jeecg/config/shiro/third)

使用示例:
```yaml
# 配置文件添加
jeecg:
  shiro:
    # 开放给第三方系统的接口简单鉴权配置,多个url用逗号分隔,name和key协商即可
    thirdSystem:
      - name: SysName1 # 由于放在header中鉴权的原因,不支持中文
        key: xq2#29wq.Iooquerrt
        urls: /third/xx/**
      - name: SysName2
        key: xq2#29wq.Iooqusqq
        urls: /third/ps/**,/third/aaaa/**
```

测试Controller:
```java
@RestController
@RequestMapping("/third/ps")
public class TestApi {

    @GetMapping("/test")
    public String test() {
        return "Hello World";
    }

}
```

修改历史:
* 2023-07-25: 新增功能
