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

## 单独使用QueryGenerator能力

1. 打包工程，将core包拿出来放到别的项目/lib目录中

2. 引入core包

```xml
<projecg>
    <!-- jeecg-core核心包 BEGIN -->
    <dependency>
        <groupId>org.jeecgframework.boot</groupId>
        <artifactId>jeecg-boot-base-core</artifactId>
        <version>3.4.3</version>
        <scope>system</scope>
        <systemPath>${project.basedir}/lib/jeecg-boot-base-core-3.4.3.jar</systemPath>
    </dependency>
    <dependency>
        <groupId>commons-beanutils</groupId>
        <artifactId>commons-beanutils</artifactId>
        <version>1.9.4</version>
    </dependency>
    <!-- jeecg-core核心包 END -->
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <!--打包带上core-->
                    <includeSystemScope>true</includeSystemScope>
                </configuration>
            </plugin>
        </plugins>
    </build>
</projecg>

```

3. 在启动类或配置类中为静态方法启用独立模式

```java
import org.jeecg.common.system.query.QueryGenerator;
import org.springframework.context.annotation.Configuration;

/**
 * Jeecg配置
 *
 * @author Yoko
 */
@Configuration
public class JeecgConfig {

    static {
        // 条件构造器独立模式
        QueryGenerator.standaloneLikeMode();
    }

}

```

修改历史:
* 2023.11.22: 根据yaml配置初始化字符类型的查询匹配模式
* 2024.04.19: 改造QueryGenerator工具类-以支持core包独立引用使用
