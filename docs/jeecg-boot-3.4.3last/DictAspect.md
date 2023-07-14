[返回](../)

# DictAspect

字典切面增强类.

组件路径: [DictAspect](https://github.com/yoko-murasame/jeecg-boot/blob/yoko-3.4.3last/jeecg-boot-base-core/src/main/java/org/jeecg/common/aspect/DictAspect.java)

使用示例:
```java
class test {
    // 反向翻译对象
    @AutoTransDict(type = TransDictType.OBJECT, reverseValue = true)
    public Result<TestVo> queryByCode(Test param, HttpServletRequest req) {
        QueryWrapper<TestVo> queryWrapper = QueryGenerator.initQueryWrapper(param, req.getParameterMap());
        TestVo vo = service.info(queryWrapper);
        return Result.OK(vo);
    }
}
```

修改历史:
* 2023-07-14: 添加反向翻译字典功能；添加自定义翻译注解 @AutoTransDict。
