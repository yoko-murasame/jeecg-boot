// package org.jeecg.modules.system.service.impl;
//
// import org.apache.dubbo.config.annotation.DubboService;
// import org.jeecg.common.system.api.ISysBaseAPI;
// import org.jeecg.config.JeecgDubboCondition;
// import org.springframework.context.annotation.Conditional;
//
// /**
//  * Dubbo基础服务提供者
//  */
// @DubboService(
// 		interfaceClass = ISysBaseAPI.class,
// 		timeout = 3000
// )
// @Conditional(JeecgDubboCondition.class)
// public class SysBaseApiImplProvider extends SysBaseApiImpl {
//
// 	public SysBaseApiImplProvider() {
// 		super();
// 	}
// }
