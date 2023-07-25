package org.jeecg.config.shiro.third;// package org.jeecg.config.shiro.third;
//
// import com.alibaba.fastjson.JSON;
// import lombok.Setter;
// import lombok.extern.slf4j.Slf4j;
// import org.apache.shiro.subject.SimplePrincipalCollection;
// import org.apache.shiro.subject.Subject;
// import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
// import org.jeecg.common.api.dto.LogDTO;
// import org.jeecg.common.constant.CommonConstant;
// import org.jeecg.common.system.vo.LoginUser;
// import org.jeecg.common.util.IPUtils;
// import org.jeecg.config.mybatis.TenantContext;
// import org.jeecg.modules.base.service.BaseCommonService;
// import org.springframework.http.HttpStatus;
// import org.springframework.web.bind.annotation.RequestMethod;
//
// import javax.annotation.Resource;
// import javax.servlet.ServletRequest;
// import javax.servlet.ServletResponse;
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;
// import java.util.Date;
// import java.util.List;
//
// import static org.jeecg.common.constant.CommonConstant.LOG_TYPE_2;
// import static org.jeecg.common.constant.CommonConstant.OPERATE_TYPE_1;
//
// /**
//  * 第三方系统权限过滤器
//  *
//  * @author Yoko
//  * @since 2023/7/25 14:19
//  */
// @Slf4j
// public class ThirdSystemFilterBak extends BasicHttpAuthenticationFilter {
//
//     private boolean allowOrigin = true;
//
//     @Setter
//     private List<ThirdSystemProperty> thirdSystem;
//
//     @Resource
//     private BaseCommonService baseCommonService;
//
//     @Override
//     protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) {
//
//         // Subject subject = getSubject(servletRequest, servletResponse);
//         // String url = getPathWithinApplication(servletRequest);
//         // log.info("当前用户正在访问的url为 " + url);
//         // log.info("subject.isPermitted(url);" + subject.isPermitted(url));
//         // 可自行根据需要判断是否拦截，可以获得subject判断用户权限，也可以使用req获得请求头请求体信息
//         // return true;
//         return this.checkPermission(servletRequest, servletResponse);
//     }
//
//     public boolean checkPermission(ServletRequest servletRequest, ServletResponse servletResponse) {
//         HttpServletRequest request = (HttpServletRequest) servletRequest;
//         HttpServletResponse response = (HttpServletResponse) servletResponse;
//         String systemName = request.getParameter("systemName");
//         String key = request.getParameter("key");
//         for (ThirdSystemProperty property : thirdSystem) {
//             if (property.getName().equals(systemName) && property.getKey().equals(key)) {
//                 // 注入subject
//                 Subject subject = getSubject(servletRequest, servletResponse);
//                 SimplePrincipalCollection simplePrincipalCollection = new SimplePrincipalCollection();
//                 LoginUser loginUser = new LoginUser();
//                 loginUser.setUsername(systemName);
//                 loginUser.setRealname(systemName);
//                 simplePrincipalCollection.add(loginUser, getName());
//                 subject.runAs(simplePrincipalCollection);
//                 // 记录日志
//                 this.doLog(loginUser, request);
//                 return true;
//             }
//         }
//         return false;
//     }
//
//     // @Override
//     // protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
//     //     // 从req中获得的值，也可以自己使用其它判断是否放行的方法
//     //     String username = servletRequest.getParameter("name");
//     //     String password = servletRequest.getParameter("password");
//     //     // // 创建token对象
//     //     UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
//     //     Subject subject = SecurityUtils.getSubject();
//     //     try {
//     //         // 使用subject对象的login方法验证该token能否登录(使用方法2中的shiroRealm.java中的方法验证)
//     //         subject.login(usernamePasswordToken);
//     //     } catch (Exception e) {
//     //         // log.info("登陆失败");
//     //         // log.info(e.getMessage());
//     //         return false;
//     //     }
//     //     log.info("登陆成功");
//     //     return false;
//     // }
//
//     public void doLog(LoginUser sysUser, ServletRequest servletRequest) {
//         HttpServletRequest request = (HttpServletRequest) servletRequest;
//         LogDTO dto = new LogDTO();
//
//         // 设置IP地址
//         String ip = IPUtils.getIpAddr(request);
//         dto.setIp(ip);
//
//         // 内容
//         String systemName = sysUser.getUsername();
//         String content = String.format("第三方系统登录，系统名称：%s，IP：%s，时间：%s", systemName, ip, new Date());
//         dto.setLogContent(content);
//
//         // 请求的url
//         String url = getPathWithinApplication(servletRequest);
//         dto.setMethod(url);
//
//         // 设置操作类型
//         dto.setLogType(LOG_TYPE_2);
//         dto.setOperateType(OPERATE_TYPE_1);
//
//         // 请求的参数
//         dto.setRequestParam(JSON.toJSONString(request.getParameterMap()));
//
//         // 获取登录用户信息
//         dto.setUserid(sysUser.getUsername());
//         dto.setUsername(sysUser.getRealname());
//
//         dto.setCreateTime(new Date());
//         // 保存系统日志
//         baseCommonService.addLog(dto);
//     }
//
//     /**
//      * 对跨域提供支持
//      */
//     @Override
//     protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
//         HttpServletRequest httpServletRequest = (HttpServletRequest) request;
//         HttpServletResponse httpServletResponse = (HttpServletResponse) response;
//         if(allowOrigin){
//             httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
//             httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
//             httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
//             //update-begin-author:scott date:20200907 for:issues/I1TAAP 前后端分离，shiro过滤器配置引起的跨域问题
//             // 是否允许发送Cookie，默认Cookie不包括在CORS请求之中。设为true时，表示服务器允许Cookie包含在请求中。
//             httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
//             //update-end-author:scott date:20200907 for:issues/I1TAAP 前后端分离，shiro过滤器配置引起的跨域问题
//         }
//         // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
//         if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
//             httpServletResponse.setStatus(HttpStatus.OK.value());
//             return false;
//         }
//         //update-begin-author:taoyan date:20200708 for:多租户用到
//         String tenant_id = httpServletRequest.getHeader(CommonConstant.TENANT_ID);
//         TenantContext.setTenant(tenant_id);
//         //update-end-author:taoyan date:20200708 for:多租户用到
//         return super.preHandle(request, response);
//     }
//
// }
