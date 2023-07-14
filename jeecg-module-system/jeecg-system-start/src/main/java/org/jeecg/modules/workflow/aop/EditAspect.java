package org.jeecg.modules.workflow.aop;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.modules.extbpm.process.entity.ExtActFlowData;
import org.jeecg.modules.extbpm.process.service.IExtActFlowDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * 流程操作接口-切面增强类
 *
 * 也可以在每个Controller的删除方法添加取回流程逻辑，看情况使用
 *
 * @author Yoko
 */
// @Component
// @Aspect
@Slf4j
public class EditAspect {

    @Resource
    private ISysBaseAPI sysBaseApi;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private TransactionDefinition transactionDefinition;

    // @Pointcut("execution(public * org.jeecg.modules.deal.controller.DealServiceLxdspController.delete(..))")
    // public void deleteLxdspCut() {}
    //
    // @Pointcut("execution(public * org.jeecg.modules.deal.controller.DealServiceZfkController.delete(..))")
    // public void deleteZfkCut() {}
    //
    // @Pointcut("execution(public * org.jeecg.modules.deal.controller.DealServiceJsglController.delete(..))")
    // public void deleteJsCut() {}
    //
    // @Pointcut("execution(public * org.jeecg.modules.safe.controller.SafeCheckController.delete(..))")
    // public void deleteSafeCut() {}
    //
    // @Pointcut("execution(public * org.jeecg.modules.gzcg.controller.GzcgCgjhController.delete(..))")
    // public void deleteGzcgCut() {}
    //
    // @Pointcut("execution(public * org.jeecg.modules.ztb.controller.Ztb*Controller.delete(..))")
    // public void deleteZtbCut() {}
    //
    // @Pointcut("deleteLxdspCut() || deleteZfkCut() || deleteJsCut() || deleteSafeCut() || deleteGzcgCut() || deleteZtbCut()")
    // public void allPoint() {}

    /**
     * 新增成功后，获取当前用户角色->新增后的项目id->新增权限数据（查删改）；若失败，回滚
     * 添加classSimpleName字段用以区分不同类型项目以减少切面接口的搜索量
     */
    // @Around("allPoint()")
    public Object aroundDelete(ProceedingJoinPoint point) throws Throwable {
        Object[] args = point.getArgs();
        log.debug("流程增强删除接口--请求参数{}", args);
        TransactionStatus status = transactionManager.getTransaction(transactionDefinition);
        Object result;
        try {
            if (args[0] instanceof String && StringUtils.hasText((String) args[0])) {
                IExtActFlowDataService extActFlowDataService = SpringContextUtils.getBean(IExtActFlowDataService.class);
                List<ExtActFlowData> extActFlowData =
                        extActFlowDataService.list(new LambdaQueryWrapper<ExtActFlowData>()
                                .eq(ExtActFlowData::getFormDataId, args[0])
                                .eq(ExtActFlowData::getBpmStatus, "2"));
                if (extActFlowData.size() > 0) {
                    throw new RuntimeException("存在未完成的流程, 请先取回流程!");
                }
            }
            result = point.proceed();
            log.info("流程增强删除接口--成功{}", result);
            transactionManager.commit(status);
        } catch (Throwable e) {
            transactionManager.rollback(status);
            log.error("流程增强删除接口--失败{}", e.getMessage());
            throw e;
        }
        return result;
    }

}
