package org.jeecg.config.shiro.third;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Data
@Configuration
@DependsOn("shiroConfig") // 需要依赖默认配置
@ConfigurationProperties(prefix = "jeecg.shiro")
@Slf4j
public class ThirdSystemConfig implements InitializingBean {

    // 读取第三方系统配置
    private List<ThirdSystemProperty> thirdSystem;

    @Autowired
    private ShiroFilterFactoryBean shiroFilterFactoryBean;

    @Autowired
    private ThirdSystemRealm thirdSystemRealm;

    public static final String THIRD_SYSTEM_FILTER = "THIRD_SYSTEM_FILTER";
    /**
     * header命名中，不允许_存在，因此sys_name这样的header是无法通过nginx传递的
     * header传递中不区分大小写
     * 一般X开头的header用以区分自定义header
     */
    public static final String THIRD_SYSTEM_NAME = "x-sys-name";
    public static final String THIRD_SYSTEM_KEY = "x-sys-key";

    // 初始化方法
    public void init() {
        if (thirdSystem != null && thirdSystem.size() > 0) {
            // 组装自定义过滤器链
            LinkedHashMap<String, String> thirdSystemMap = new LinkedHashMap<>();
            for (ThirdSystemProperty property : thirdSystem) {
                String[] urls = property.getUrls().split(",");
                for (String url : urls) {
                    thirdSystemMap.put(url, THIRD_SYSTEM_FILTER);
                }
            }
            // 放到过滤链队首
            if (!thirdSystemMap.isEmpty()) {
                // 自定义过滤器
                ThirdSystemFilter thirdSystemFilter = new ThirdSystemFilter();
                LinkedHashMap<String, Filter> thirdSystemFilters = new LinkedHashMap<>();
                thirdSystemFilters.put(THIRD_SYSTEM_FILTER, thirdSystemFilter);

                // 过滤链
                Map<String, String> sourceMap = shiroFilterFactoryBean.getFilterChainDefinitionMap();
                thirdSystemMap.putAll(sourceMap);

                // 刷新过滤工厂
                this.updatePermission(shiroFilterFactoryBean, thirdSystemFilters, thirdSystemMap, true);

                // 添加自定义realm
                thirdSystemRealm.setThirdSystem(thirdSystem);
                DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager) shiroFilterFactoryBean.getSecurityManager();
                securityManager.getRealms().add(thirdSystemRealm);
            }
            log.info("第三方系统过滤器链注册成功");
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        init();
    }

    /**
     * 动态更新权限
     *
     * @author Yoko
     * @since 2023/7/25 17:11
     * @param shiroFilterFactoryBean ShiroFilterFactoryBean
     * @param newFilters 新的过滤器map
     * @param newFilterChains 新的过滤链map
     * @param clearPermission 清空旧的过滤链
     */
    public void updatePermission(ShiroFilterFactoryBean shiroFilterFactoryBean,
                                 Map<String, Filter> newFilters,
                                 Map<String, String> newFilterChains,
                                 Boolean clearPermission) {
        synchronized (this) {
            AbstractShiroFilter shiroFilter = null;
            try {
                shiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean.getObject();
            } catch (Exception e) {
                log.error("获取 AbstractShiroFilter 失败!");
            }

            PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) shiroFilter.getFilterChainResolver();
            DefaultFilterChainManager manager = (DefaultFilterChainManager) filterChainResolver.getFilterChainManager();

            // 添加自定义filter
            newFilters.forEach(manager::addFilter);

            // 清空拦截管理器中的存储
            manager.getFilterChains().clear();
            // 清空拦截工厂中的存储,如果不清空这里,还会把之前的带进去
            //            ps:如果仅仅是更新的话,可以根据这里的 map 遍历数据修改,重新整理好权限再一起添加
            if (clearPermission) {
                shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();
                // 动态查询数据库中所有权限
                shiroFilterFactoryBean.setFilterChainDefinitionMap(newFilterChains);
            } else {
                shiroFilterFactoryBean.getFilterChainDefinitionMap().putAll(newFilterChains);
            }

            // 重新构建生成拦截
            shiroFilterFactoryBean.getFilterChainDefinitionMap().forEach(manager::createChain);
            log.info("更新Shiro过滤链成功");
        }
    }
}
