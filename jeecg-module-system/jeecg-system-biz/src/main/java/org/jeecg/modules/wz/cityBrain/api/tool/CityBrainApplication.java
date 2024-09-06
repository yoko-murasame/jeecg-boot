package org.jeecg.modules.wz.cityBrain.api.tool;

import org.apache.shiro.util.Assert;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.*;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.function.Function;

/**
 * @Author: -Circle
 * @Date: 2021/7/21 15:08
 * @Description:
 * 重构项目启动器，环境
 */
public class CityBrainApplication {
    /**
     * Create an application context
     * java -jar app.jar --spring.profiles.active=prod --server.port=2333
     * @param appName
     * @param source
     * @param args
     * @return
     */
    public static ConfigurableApplicationContext run (String appName,Class source,String... args){
        SpringApplicationBuilder builder = createSpringApplicationBuilder(appName,source,args);
        return builder.run(args);
    }

    private static SpringApplicationBuilder createSpringApplicationBuilder(String appName, Class source, String... args) {
        Assert.hasText(appName,"[appName]不能为空");
        // 读取环境变量
        ConfigurableEnvironment environment = new StandardEnvironment();
        MutablePropertySources propertySources = environment.getPropertySources();
        propertySources.addFirst(new SimpleCommandLinePropertySource(args));
        propertySources.addLast(new MapPropertySource(StandardEnvironment.SYSTEM_PROPERTIES_PROPERTY_SOURCE_NAME, environment.getSystemProperties()));
        propertySources.addLast(new SystemEnvironmentPropertySource(StandardEnvironment.SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME, environment.getSystemEnvironment()));
        // 获取配置的环境变量
        String[] activeProfiles = environment.getActiveProfiles();
        //判断环境
        List<String> profiles = Arrays.asList(activeProfiles);
        //预设的环境
        List<String> presetProfiles = new ArrayList<>(Arrays.asList("dev","test","prod"));
        presetProfiles.retainAll(profiles);
        //当前使用
        List<String> activeProfileList = new ArrayList<>(profiles);
        Function<Object[],String> joinFun = StringUtils::arrayToCommaDelimitedString;
        SpringApplicationBuilder builder = new SpringApplicationBuilder(source);
        String profile;
        if(activeProfileList.isEmpty()){
            //默认为开发环境
            profile = "dev";
            activeProfileList.add(profile);
            builder.properties(profile);
        } else if (activeProfileList.size() == 1) {
            profile = activeProfileList.get(0);
        } else {
            throw new RuntimeException("同时存在环境变量:[" + StringUtils.arrayToCommaDelimitedString(activeProfiles) + "]");
        }
        String startJarPath = CityBrainApplication.class.getResource("/").getPath().split("!")[0];
        String activePros = joinFun.apply(activeProfileList.toArray());
        System.out.printf("----启动中，读取到的环境变量:[%s]，jar地址:[%s]----%n", activePros, startJarPath);
        Properties props = System.getProperties();
        props.setProperty("spring.application.name", appName);
        props.setProperty("spring.profiles.active", profile);
        props.setProperty("info.version", "1.0");
        props.setProperty("info.desc", appName);
        props.setProperty("file.encoding", StandardCharsets.UTF_8.name());
        Properties defaultProperties = new Properties();
        defaultProperties.setProperty("spring.main.allow-bean-definition-overriding", "true");
        builder.properties(defaultProperties);
        // 加载自定义组件
//        List<LauncherService> launcherList = new ArrayList<>();
//        ServiceLoader.load(LauncherService.class).forEach(launcherList::add);
//        launcherList.stream().sorted(Comparator.comparing(LauncherService::getOrder)).collect(Collectors.toList())
//                .forEach(launcherService -> launcherService.launcher(builder, appName, profile));
        return builder;
    }
    public static boolean isLocalDev() {
        String osName = System.getProperty("os.name");
        return StringUtils.hasText(osName) && !"LINUX".equals(osName.toUpperCase());
    }
}
