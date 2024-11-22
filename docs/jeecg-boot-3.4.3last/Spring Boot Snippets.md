[返回](../)

# Spring Boot Snippets

Spring Boot开发相关代码参考片段

一些工具类：

* [FormatUtil](https://github.com/yoko-murasame/jeecg-boot/blob/yoko-3.4.3last/jeecg-boot-base-core/src/main/java/org/jeecg/common/util/yoko/FormatUtil.java)
  * 格式化 XGB XMB XKB
  * 获取当前JVM内存信息
  * 格式化时间戳差值 x时 x分 x秒
  * 提取下载路径的文件名

### Java Reduce 示例

**根据JSON路径，取出实际String值**

```java
class a {
    void b() {
        String jsonValue = (String) row.get(field);
        if (StringUtils.isEmpty(jsonValue)) {
            return;
        }
        // 路径例如：objA,objB,fieldC
        String jsonPath = jsonPaths.get(idx);
        String realValue = Arrays.stream(jsonPath.split(",")).reduce(jsonValue, (a, b) -> {
            try {
                JSONObject aJson = JSON.parseObject(a);
                Object bJson = aJson.get(b);
                return bJson.getClass().getTypeName().equals("java.lang.String") ? (String) bJson : JSON.toJSONString(bJson);
            } catch (Exception e) {
                return a;
            }
        }, (a, b) -> a);
        log.info("jsonPath: {}, realValue: {}", jsonPath, realValue);
    }
}

```


### 覆写mybatis plus page接口

service

```java
class a{
    <E extends IPage<SzProjectDesign>> E page(E page, @Param(Constants.WRAPPER) Wrapper<SzProjectDesign> queryWrapper);

    @Override
    public <E extends IPage<SzProjectDesign>> E page(E page, Wrapper<SzProjectDesign> queryWrapper) {
        return this.baseMapper.selectPage(page, queryWrapper);
    }

    List<SzProjectDesign> list(Wrapper<SzProjectDesign> queryWrapper);

    @Override
    public List<SzProjectDesign> list(Wrapper<SzProjectDesign> queryWrapper){
        return this.baseMapper.selectList(queryWrapper);
    }
}

```

mapper

```java
interface a {
    <E extends IPage<SzProjectDesign>> E selectPage(E page, @Param(Constants.WRAPPER) Wrapper<SzProjectDesign> wrapper);

    List<T> selectList(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);
}
```


### mybatis plus 返回lambda动态列

```java
class a {
    public List<PipenetworkWsAnalysis> queryAnalysisByCode(PipeAnalysisRequest request) {

        LambdaQueryWrapper<PipenetworkWsAnalysis> wp = Wrappers.lambdaQuery(PipenetworkWsAnalysis.class);

        // 根据参数动态返回列名
        List<SFunction<PipenetworkWsAnalysis, Object>> funcList = new ArrayDeque<>(11);
        funcList.addAll(Arrays.asList(
                PipenetworkWsAnalysis::getIdOne,
                PipenetworkWsAnalysis::getIdTwo,
                PipenetworkWsAnalysis::getStartId,
                PipenetworkWsAnalysis::getEndId,
                PipenetworkWsAnalysis::getFinish,
                PipenetworkWsAnalysis::getSmnodeidCombine,
                PipenetworkWsAnalysis::getNodeIds,
                PipenetworkWsAnalysis::getEdgeIds
        ));

        if (request.getReturnNodeDetail()) {
            funcList.add(PipenetworkWsAnalysis::getNodeFeatures);
        }
        if (request.getReturnEdgeDetail()) {
            funcList.add(PipenetworkWsAnalysis::getEdgeFeatures);
        }
        if (request.getReturnDetail()) {
            funcList.add(PipenetworkWsAnalysis::getData);
        }
        wp.select(funcList.toArray(new SFunction[0]));

        return this.list(wp);
    }
}
```

### 切面、手动事务

```java
package org.jeecg.modules.onemap.project.aop;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.modules.onemap.project.entity.SzProjectAuthInfo;
import org.jeecg.modules.onemap.project.entity.SzProjectRole;
import org.jeecg.modules.onemap.project.service.ISzProjectRoleService;
import org.jeecg.modules.onemap.sz.entity.OmSzCategory;
import org.jeecg.modules.onemap.sz.service.IOmSzCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.jeecg.modules.onemap.project.service.ISzProjectRoleService.*;

/**
 * 项目权限控制-切面增强类
 *
 * @author Yoko
 * @since 2023/1/9 8:53
 */
@Component
@Aspect
@Slf4j
public class ProjectAuthAspect {

    @Resource
    private ISzProjectRoleService roleService;

    @Resource
    private IOmSzCategoryService categoryService;

    @Resource
    private ISysBaseAPI sysBaseApi;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private TransactionDefinition transactionDefinition;

    @Pointcut("execution(public * org.jeecg.modules.onemap.project.service.*Project*.save(..)) && " +
            "!execution(public * org.jeecg.modules.onemap.project.service.*Role*.save(..)) && " +
            "!execution(public * org.jeecg.modules.onemap.project.service.*Stage*.save(..))")
    public void addPointCut() {}

    @Pointcut("execution(public * org.jeecg.modules.onemap.project.service.*Project*.updateById(..)) && " +
            "!execution(public * org.jeecg.modules.onemap.project.service.*Role*.updateById(..)) && " +
            "!execution(public * org.jeecg.modules.onemap.project.service.*Stage*.updateById(..))")
    public void editPointCut() {}

    @Pointcut("execution(public * org.jeecg.modules.onemap.project.service.*Project*.removeById(..)) && " +
            "!execution(public * org.jeecg.modules.onemap.project.service.*Role*.removeById(..)) && " +
            "!execution(public * org.jeecg.modules.onemap.project.service.*Stage*.removeById(..))")
    public void deletePointCut() {}

    @Pointcut("execution(public * org.jeecg.modules.onemap.project.service.*Project*.page(..)) && " +
            "!execution(public * org.jeecg.modules.onemap.project.service.*Role*.page(..)) && " +
            "!execution(public * org.jeecg.modules.onemap.project.service.*Stage*.page(..))")
    public void pagePoint() {}

    @Pointcut("execution(public * org.jeecg.modules.onemap.project.service.*Project*.list(..)) && " +
            "!execution(public * org.jeecg.modules.onemap.project.service.*Role*.list(..)) && " +
            "!execution(public * org.jeecg.modules.onemap.project.service.*Stage*.list(..))")
    public void listPoint() {}

    @Pointcut("pagePoint() || listPoint()")
    public void listPointCut() {}

    @Pointcut("execution(public * org.jeecg.modules.system.service.impl.SysRoleServiceImpl.deleteRole(..))")
    public void roleDeletePointCut() {}

    @Pointcut("" +
            "execution(public * org.jeecg.modules.system.controller.SysUserController.delete*(..)) ||" +
            "execution(public * org.jeecg.modules.system.controller.SysUserController.add*(..)) ||" +
            "execution(public * org.jeecg.modules.system.controller.SysUserController.edit*(..))"
    )
    public void roleUserEditPointCut() {}

    /**
     * 新增成功后，获取当前用户角色->新增后的项目id->新增权限数据（查删改）；若失败，回滚
     * 添加classSimpleName字段用以区分不同类型项目以减少切面接口的搜索量
     */
    @Around("addPointCut()")
    public Object afterAdd(ProceedingJoinPoint point) throws Throwable {
        Object[] args = point.getArgs();
        log.debug("新增接口增强--请求参数{}", args);
        TransactionStatus status = transactionManager.getTransaction(transactionDefinition);
        Object result;
        try {
            result = point.proceed();
            // 保存权限数据
            Method getId = args[0].getClass().getDeclaredMethod(METHOD_GET_ID);
            String id = (String) getId.invoke(args[0]);
            List<String> currentRoleIds = roleService.getCurrentRoleIds();
            List<String> adminRoleIds = sysBaseApi.getRoleIdsByUsername(ROLE_ADMIN);
            Set<String> distinctIds = CollUtil.unionDistinct(currentRoleIds, adminRoleIds);
            if (result instanceof Boolean && ((Boolean) result)) {
                distinctIds.stream()
                        .map(roleId -> SzProjectRole.ofFullAuth(id, roleId, args[0].getClass().getSimpleName()))
                        .forEach(roleService::save);
            }
            log.info("新增接口增强--成功{}", result);
            transactionManager.commit(status);
        } catch (Throwable e) {
            transactionManager.rollback(status);
            log.error("新增接口增强--失败{}", e.getMessage());
            throw e;
        }
        return result;
    }

    /**
     * 编辑操作前，获取当前用户角色->获取权限数据并校验编辑权限
     */
    @Around("editPointCut()")
    public Object beforeEdit(ProceedingJoinPoint point) throws Throwable {
        Object[] args = point.getArgs();
        log.debug("编辑接口增强--请求参数{}", args);
        JSONObject obj = JSON.parseObject(JSON.toJSONString(args[0]));
        String id = obj.getString(COL_ID);
        Boolean permission = roleService.checkEditPermission(id);
        if (!permission) {
            throw new JeecgBootException("您没有编辑权限");
        }
        Object result = point.proceed();
        log.info("编辑接口增强--成功{}", result);
        return result;
    }

    /**
     * 删除操作前，获取当前用户角色->获取权限数据并校验删除权限
     * 删除操作前，校验项目子树，存在子树拒绝删除（需要先清空子树）
     */
    @Around("deletePointCut()")
    public Object aroundDelete(ProceedingJoinPoint point) throws Throwable {
        Object[] args = point.getArgs();
        log.debug("删除接口增强--请求参数{}", args);
        TransactionStatus status = transactionManager.getTransaction(transactionDefinition);
        Object result;
        try {
            String id = (String) args[0];
            Boolean permission = roleService.checkDeletePermission(id);
            if (!permission) {
                throw new JeecgBootException("您没有删除权限");
            }
            // 校验项目子树，存在子树拒绝删除
            int subTreeCount = categoryService.count(Wrappers.lambdaQuery(OmSzCategory.class).eq(OmSzCategory::getProjectId, id));
            Assert.state(subTreeCount == 0, "子菜单配置不为空，请先清空子菜单！");
            // 删除项目和权限
            result = point.proceed();
            if (result instanceof Boolean && ((Boolean) result)) {
                // 删除项目的权限配置数据
                roleService.remove(Wrappers.lambdaQuery(SzProjectRole.class).eq(SzProjectRole::getProjectId, id));
            }
            log.info("删除接口增强--成功{}", result);
            transactionManager.commit(status);
        } catch (Throwable e) {
            transactionManager.rollback(status);
            log.error("删除接口增强--失败{}", e.getMessage());
            throw e;
        }
        return result;
    }

    /**
     * 分页查询前，获取当前用户角色->获取权限数据列表->增强查询语句->执行分页查询->增强查询结果的修改和编辑权限标志；
     * 添加classSimpleName字段用以区分不同类型项目以减少切面接口的搜索量
     */
    @Around("listPointCut()")
    public Object beforeList(ProceedingJoinPoint point) throws Throwable {
        Object[] args = point.getArgs();
        log.debug("分页接口增强--请求参数{}", args);
        Map<String, SzProjectAuthInfo> authInfoMap = null;
        // for (Object arg : args) {
        //     // Object target = arg;
        //     // if (arg instanceof LambdaQueryWrapper) {
        //     //     target = new QueryWrapper().
        //     // }
        //     if (arg instanceof QueryWrapper) {
        //         String classSimpleName = Optional.ofNullable(((QueryWrapper<?>) arg).getEntityClass()).map(Class::getSimpleName).orElse("");
        //         // 检查权限，获取所有角色中的最高权限列表，映射成字典
        //         authInfoMap = roleService.getCurrentAuthInfoMap(new SzProjectRole().setClassSimpleName(classSimpleName));
        //         // 扩展wrapper参数,筛选读权限,需要注意当边界清空下(一条数据没有)需要添加默认的错误条件
        //         ((QueryWrapper<?>) arg)
        //                 .eq(authInfoMap.values().size() == 0, COL_ID, "")
        //                 .in(authInfoMap.values().size() > 0, COL_ID,
        //                         authInfoMap.values().stream()
        //                                 .filter(SzProjectAuthInfo::hasReadAuth)
        //                                 .map(SzProjectAuthInfo::getProjectId)
        //                                 .collect(Collectors.toList()));
        //     }
        // }
        // if (null == authInfoMap) {
        //     authInfoMap = roleService.getCurrentAuthInfoMap();
        // }
        // 执行查询
        Object result = point.proceed(args);
        // 通过反射封装权限数据
        if (result != null) {
            authInfoMap = roleService.getCurrentAuthInfoMap();
            List<?> records = null;
            if (result instanceof IPage) {
                records = ((IPage<?>) result).getRecords();
            }
            if (result instanceof List) {
                records = (List<?>) result;
            }
            if (null != records && records.size() > 0) {
                for (Object record : records) {
                    Class<?> clazz = record.getClass();
                    Method getId = clazz.getDeclaredMethod(METHOD_GET_ID);
                    Method setAuthInfo = clazz.getDeclaredMethod(METHOD_SET_AUTH_INFO, SzProjectAuthInfo.class);
                    String projectId = (String) getId.invoke(record);
                    setAuthInfo.invoke(record, authInfoMap.getOrDefault(projectId, null));
                }
            }
        }
        log.info("分页接口增强--成功");
        return result;
    }

    /**
     * 角色和用户的修改，执行缓存清除
     */
    @Before("roleUserEditPointCut()")
    public void beforeRoleUserEdit() {
        // 清空用户角色缓存
        roleService.clearCacheRoleIds();
        log.info("清空用户角色缓存");
    }

    /**
     * 在删除系统角色后，统一删除关联的项目权限数据
     */
    @Around("roleDeletePointCut()")
    public Object beforeRoleDelete(ProceedingJoinPoint point) throws Throwable {
        Object[] args = point.getArgs();
        log.debug("系统角色删除接口增强--请求参数{}", args);
        TransactionStatus status = transactionManager.getTransaction(transactionDefinition);
        Object result;
        try {
            String id = (String) args[0];
            // 1.先执行角色删除 2.删除项目和权限
            result = point.proceed();
            if (result instanceof Boolean && ((Boolean) result)) {
                // 删除项目的权限配置数据
                roleService.remove(Wrappers.lambdaQuery(SzProjectRole.class).eq(SzProjectRole::getRoleId, id));
            }
            log.info("系统角色删除接口增强--成功{}", result);
            transactionManager.commit(status);
        } catch (Throwable e) {
            transactionManager.rollback(status);
            log.error("系统角色删除接口增强--失败{}", e.getMessage());
            throw e;
        }
        return result;
    }

}

```

## 获取方法的sFunction

**工具类**

```java
    /**
     * 缓存SFunction
     */
    private static final Map<String, SFunction> functionMap = new HashMap<>();

    /**
     * 可序列化
     */
    private static final int FLAG_SERIALIZABLE = 1;

    /**
     * 获取与实体类字段对应的 SFunction 对象。
     * @param entityClass 实体类的 Class 对象。
     * @param fieldName 实体类中的字段名。
     * @return 返回找到的 SFunction 对象。
     */
    public static SFunction getSFunction(Class<?> entityClass, String fieldName) {
        // 检查缓存中是否已经有了对应的 SFunction 对象。
        if (functionMap.containsKey(entityClass.getName() + fieldName)) {
            return functionMap.get(entityClass.getName() + fieldName);
        }
        // 获取实体类中名为 fieldName 的字段。
        Field field = ReflectUtil.getField(entityClass, fieldName);
        if (field == null) {
            //如果字段不存在，使用 ExceptionUtils 抛出一个异常，指出实体类中没有找到该字段。
            throw ExceptionUtils.mpe("This class %s is not have field %s ", entityClass.getName(), fieldName);
        }
        SFunction<T, ?> func = null;
        // 获取 MethodHandles.Lookup 实例，用于反射操作。
        final MethodHandles.Lookup lookup = MethodHandles.lookup();
        // 定义方法类型，表示实体类的实例方法，该方法返回字段的类型。
        MethodType methodType = MethodType.methodType(field.getType(), entityClass);
        // 用于存储 LambdaMetafactory 创建的 CallSite 对象。
        final CallSite site;
        // 构造标准的 Java getter 方法名。
        String getFunName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        try {
            // 使用 LambdaMetafactory 创建一个动态的 SFunction 实例。
            site = LambdaMetafactory.altMetafactory(
                    lookup,
                    "invoke",
                    MethodType.methodType(SFunction.class),
                    methodType,
                    lookup.findVirtual(entityClass, getFunName, MethodType.methodType(field.getType())),
                    methodType,
                    FLAG_SERIALIZABLE
            );
            // 使用 CallSite 来获取 SFunction 实例。
            func = (SFunction) site.getTarget().invokeExact();
            // 将生成的 SFunction 实例存储到缓存中。
            functionMap.put(entityClass.getName() + field.getName(), func);
            return func;
        } catch (Throwable e) {
            // 如果在创建 SFunction 过程中发生异常，抛出异常，指出实体类中没有找到对应的 getter 方法。
            throw ExceptionUtils.mpe("This class %s is not have method %s ", entityClass.getName(), getFunName);
        }
    }
```

**网上搜到的**

https://blog.csdn.net/m0_59084856/article/details/138450913
https://blog.csdn.net/m0_59084856/article/details/138452088
https://gitee.com/baomidou/mybatis-plus-advance/blob/master/src/main/java/com/baomidou/mybatisplus/advance/injector/FuntionTools.java

使用：

```java
//Person类中必须包含"name"字段.
SFunction sFunction = getSFunction(Person.class, "name");
```

代码：

```java
/**
     * 获取与实体类字段对应的 SFunction 对象。
     * @param entityClass 实体类的 Class 对象。
     * @param fieldName 实体类中的字段名。
     * @return 返回找到的 SFunction 对象。
     */
    public static SFunction getSFunction(Class<?> entityClass, String fieldName) {
        // 检查缓存中是否已经有了对应的 SFunction 对象。
        if (functionMap.containsKey(entityClass.getName() + fieldName)) {
            return functionMap.get(entityClass.getName() + fieldName);
        }
        // 获取实体类中名为 fieldName 的字段。
        Field field = getDeclaredField(entityClass, fieldName);
        if (field == null) {
            //如果字段不存在，使用 ExceptionUtils 抛出一个异常，指出实体类中没有找到该字段。
            throw ExceptionUtils.mpe("This class %s is not have field %s ", entityClass.getName(), fieldName);
        }
        SFunction func = null;
        // 获取 MethodHandles.Lookup 实例，用于反射操作。
        final MethodHandles.Lookup lookup = MethodHandles.lookup();
        // 定义方法类型，表示实体类的实例方法，该方法返回字段的类型。
        MethodType methodType = MethodType.methodType(field.getType(), entityClass);
        // 用于存储 LambdaMetafactory 创建的 CallSite 对象。
        final CallSite site;
        // 构造标准的 Java getter 方法名。
        String getFunName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        try {
            // 使用 LambdaMetafactory 创建一个动态的 SFunction 实例。
            site = LambdaMetafactory.altMetafactory(
                    lookup,
                    "invoke",
                    MethodType.methodType(SFunction.class),
                    methodType,
                    lookup.findVirtual(entityClass, getFunName, MethodType.methodType(field.getType())),
                    methodType,
                    FLAG_SERIALIZABLE
            );
            // 使用 CallSite 来获取 SFunction 实例。
            func = (SFunction) site.getTarget().invokeExact();
            // 将生成的 SFunction 实例存储到缓存中。
            functionMap.put(entityClass.getName() + field.getName(), func);
            return func;
        } catch (Throwable e) {
            // 如果在创建 SFunction 过程中发生异常，抛出异常，指出实体类中没有找到对应的 getter 方法。
            throw ExceptionUtils.mpe("This class %s is not have method %s ", entityClass.getName(), getFunName);
        }
    }

    /**
     * 递归获取类中声明的字段，包括私有字段。
     * @param clazz 要检查的类。
     * @param fieldName 要查找的字段名。
     * @return 返回找到的 Field 对象，如果没有找到则返回 null。
     */
    public static Field getDeclaredField(Class<?> clazz, String fieldName) {
        Field field = null;
        // 遍历类及其父类，直到到达 Object 类。
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                // 尝试获取声明的字段。
                field = clazz.getDeclaredField(fieldName);
                // 如果找到字段，返回该字段。
                return field;
            } catch (NoSuchFieldException e) {
                // 如果没有找到字段，继续查找父类。
                // 这里不处理异常，让其继续执行循环。
            }
        }
        // 如果没有找到字段，返回 null。
        return null;
    }

```

**可能没啥用的版本**

```java

/**
 * 设置组织数据权限-查询构造器
 *
 * @author Yoko
 * @since 2024/11/21 20:28
 * @param wrapper 查询构造器
 * @param parameter 参数对象
 * @return com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<T>
 */
public static <T> Wrapper<T> setGroupFilterForWrapper(Wrapper<T> wrapper, T parameter) {
  if (wrapper == null || parameter == null) {
    return null;
  }
  Field[] fields = oConvertUtils.getAllFields(parameter);
  for (Field field : fields) {
    if (field.isAnnotationPresent(GroupField.class)) {
      String groupValue = getGroupValue(field);
      if (StringUtils.hasText(groupValue)) {
        String dbFieldName = field.getAnnotation(GroupField.class).dbFieldName();
        if (field.isAnnotationPresent(TableField.class)) {
          dbFieldName = field.getAnnotation(TableField.class).value();
        }
        // 都没配置时，默认蛇形字段命名
        if (!StringUtils.hasText(dbFieldName)) {
          dbFieldName = StrUtil.toUnderlineCase(field.getName());
        }
        // TODO 右模糊可配置（有无必要？）
        if (wrapper instanceof QueryWrapper) {
          ((QueryWrapper<T>) wrapper).likeRight(dbFieldName, groupValue);
        }
        if (wrapper instanceof LambdaQueryWrapper) {
          ((LambdaQueryWrapper<T>) wrapper).like(getFieldLambda(field.getName()), groupValue);
        }
        log.info("注入类：{}，注入字段：{}，注入组织code值：{}", parameter.getClass().getName(), dbFieldName, groupValue);
      }
    }
  }
  return wrapper;
}

// 动态获取字段的 Lambda 表达式
private static <T> SFunction<T, ?> getFieldLambda(String fieldName) {
    return (root) -> {
        try {
            Field field = root.getClass().getDeclaredField(fieldName);
            return (SFunction<T, ?>) field.get(root);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("无法获取字段", e);
        }
    };
}

```

