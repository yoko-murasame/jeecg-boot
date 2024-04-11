package org.jeecg.modules.activiti.jeecg.jasper.aaa;

import org.apache.jsp.designer.*;
import org.jeecg.config.JeecgSingleCondition;
import org.jeecg.modules.activiti.jeecg.jasper.jsp.*;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration("moduleDesigerConfig")
@Conditional(JeecgSingleCondition.class)
public class ModuleDesigerConfig {
    public ModuleDesigerConfig() {
    }

    //SN
    @Bean
    public ServletRegistrationBean a() {
        return new ServletRegistrationBean(new EventProperties_jsp(), new String[]{"/designer/eventProperties.jsp"});
    }

    @Bean
    public ServletRegistrationBean b() {
        return new ServletRegistrationBean(new addCandidateGroup_jsp(), new String[]{"/designer/addCandidateGroup.jsp"});
    }

    @Bean
    public ServletRegistrationBean c() {
        return new ServletRegistrationBean(new addCandidateUser_jsp(), new String[]{"/designer/addCandidateUser.jsp"});
    }

    @Bean
    public ServletRegistrationBean d() {
        return new ServletRegistrationBean(new boundaryEventProperties_jsp(), new String[]{"/designer/boundaryEventProperties.jsp"});
    }

    //SN
    @Bean
    public ServletRegistrationBean e() {
        return new ServletRegistrationBean(new BusinessRuleTaskProperties_jsp(), new String[]{"/designer/businessRuleTaskProperties.jsp"});
    }

    //SN
    @Bean
    public ServletRegistrationBean f() {
        return new ServletRegistrationBean(new CandidateGroupsConfig_jsp(), new String[]{"/designer/candidateGroupsConfig.jsp"});
    }

    //SN
    @Bean
    public ServletRegistrationBean g() {
        return new ServletRegistrationBean(new CandidateUsersConfig_jsp(), new String[]{"/designer/candidateUsersConfig.jsp"});
    }

    @Bean
    public ServletRegistrationBean h() {
        return new ServletRegistrationBean(new errorBoundaryEventProperties_jsp(), new String[]{"/designer/errorBoundaryEventProperties.jsp"});
    }

    @Bean
    public ServletRegistrationBean i() {
        return new ServletRegistrationBean(new flowListenerConfig_jsp(), new String[]{"/designer/flowListenerConfig.jsp"});
    }

    //SN
    @Bean
    public ServletRegistrationBean j() {
        return new ServletRegistrationBean(new FlowProperties_jsp(), new String[]{"/designer/flowProperties.jsp"});
    }

    //sn
    @Bean
    public ServletRegistrationBean k() {
        return new ServletRegistrationBean(new GatewayProperties_jsp(), new String[]{"/designer/gatewayProperties.jsp"});
    }

    //流程设计页面 SN码有
    @Bean
    public ServletRegistrationBean l() {
        return new ServletRegistrationBean(new Index_jsp(), new String[]{"/designer/index.jsp"});
    }

    //SN
    @Bean
    public ServletRegistrationBean m() {
        return new ServletRegistrationBean(new ListenerList_jsp(), new String[]{"/designer/listenerList.jsp"});
    }

    @Bean
    public ServletRegistrationBean n() {
        return new ServletRegistrationBean(new mailTaskProperties_jsp(), new String[]{"/designer/mailTaskProperties.jsp"});
    }

    @Bean
    public ServletRegistrationBean o() {
        return new ServletRegistrationBean(new processListenerConfig_jsp(), new String[]{"/designer/processListenerConfig.jsp"});
    }

    //SN
    @Bean
    public ServletRegistrationBean p() {
        return new ServletRegistrationBean(new Processpro_jsp(), new String[]{"/designer/processpro.jsp"});
    }

    //SN
    @Bean
    public ServletRegistrationBean q() {
        return new ServletRegistrationBean(new ProcessProperties_jsp(), new String[]{"/designer/processProperties.jsp"});
    }

    @Bean
    public ServletRegistrationBean r() {
        return new ServletRegistrationBean(new receiveTaskProperties_jsp(), new String[]{"/designer/receiveTaskProperties.jsp"});
    }

    @Bean
    public ServletRegistrationBean s() {
        return new ServletRegistrationBean(new scriptTaskProperties_jsp(), new String[]{"/designer/scriptTaskProperties.jsp"});
    }

    //SN
    @Bean
    public ServletRegistrationBean t() {
        return new ServletRegistrationBean(new SelectExpressionList_jsp(), new String[]{"/designer/selectExpressionList.jsp"});
    }

    //SN
    @Bean
    public ServletRegistrationBean u() {
        return new ServletRegistrationBean(new SelectProcessListenerList_jsp(), new String[]{"/designer/selectProcessListenerList.jsp"});
    }

    //SN
    @Bean
    public ServletRegistrationBean v() {
        return new ServletRegistrationBean(new ServiceTaskProperties_jsp(), new String[]{"/designer/serviceTaskProperties.jsp"});
    }

    //这里有SN码
    @Bean
    public ServletRegistrationBean w() {
        return new ServletRegistrationBean(new TaskMyProperties_jsp(), new String[]{"/designer/taskMyProperties.jsp"});
    }

    //SN
    @Bean
    public ServletRegistrationBean x() {
        return new ServletRegistrationBean(new SubProcessProperties_jsp(), new String[]{"/designer/subProcessProperties.jsp"});
    }

    @Bean
    public ServletRegistrationBean y() {
        return new ServletRegistrationBean(new taskFormConfig_jsp(), new String[]{"/designer/taskFormConfig.jsp"});
    }

    //SN
    @Bean
    public ServletRegistrationBean z() {
        return new ServletRegistrationBean(new TaskListenerConfig_jsp(), new String[]{"/designer/taskListenerConfig.jsp"});
    }

    //SN
    @Bean
    public ServletRegistrationBean A() {
        return new ServletRegistrationBean(new TaskProperties_jsp(), new String[]{"/designer/taskProperties.jsp"});
    }

    //SN
    @Bean
    public ServletRegistrationBean B() {
        return new ServletRegistrationBean(new TaskVariableConfig_jsp(), new String[]{"/designer/taskVariableConfig.jsp"});
    }

    //SN
    @Bean
    public ServletRegistrationBean C() {
        return new ServletRegistrationBean(new SubParameterList_jsp(), new String[]{"/designer/subParameterList.jsp"});
    }

    //SN
    @Bean
    public ServletRegistrationBean D() {
        return new ServletRegistrationBean(new SearchSelectUserList_jsp(), new String[]{"/designer/searchSelectUserList.jsp"});
    }

    //sn
    @Bean
    public ServletRegistrationBean E() {
        return new ServletRegistrationBean(new Mytags_jsp(), new String[]{"/context/mytags.jsp"});
    }
}
