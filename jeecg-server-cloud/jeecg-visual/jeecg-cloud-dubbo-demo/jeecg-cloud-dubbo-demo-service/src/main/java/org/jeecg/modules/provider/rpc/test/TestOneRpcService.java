package org.jeecg.modules.provider.rpc.test;

import org.jeecg.modules.core.base.bean.HyitPage;
import org.jeecg.modules.core.base.bean.RpcResult;
import org.jeecg.modules.entity.test.TestOne;
import org.jeecg.modules.form.test.*;
import org.jeecg.modules.service.test.ITestOneService;
import org.jeecg.modules.vo.test.TestOneVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;

import javax.annotation.Resource;

/**
 * rpc服务实现类-测试模块-TestOne
 */
@Slf4j
@DubboService(
        interfaceClass = ITestOneRpc.class,
        version = "${dubbo.rpc.test.version:2.0.0}",
        timeout = 3000
)
public class TestOneRpcService implements ITestOneRpc {

    @Resource
    private ITestOneService testOneService;

    /**
     * 添加-TestOne
     */
    @Override
    public RpcResult<String> addTestOne(TestOneRpcAddForm form) throws Exception {
        TestOneForm target = new TestOneForm();
        BeanUtils.copyProperties(form, target);
        String result = testOneService.addTestOne(target);
        RpcResult<String> rpcResult = RpcResult.create();
        return rpcResult.buildSuccess(result);
    }

    /**
     * 修改-TestOne
     */
    @Override
    public RpcResult<String> editTestOne(TestOneRpcEditForm form) throws Exception {
        TestOneForm target = new TestOneForm();
        BeanUtils.copyProperties(form, target);
        String result = testOneService.editTestOne(target);
        RpcResult<String> rpcResult = RpcResult.create();
        return rpcResult.buildSuccess(result);
    }

    /**
     * 删除-TestOne
     */
    @Override
    public RpcResult<String> deleteTestOne(TestOneRpcDeleteForm form) throws Exception {
        TestOneDeleteForm target = new TestOneDeleteForm();
        BeanUtils.copyProperties(form, target);
        String result = testOneService.deleteTestOne(target);
        RpcResult<String> rpcResult = RpcResult.create();
        return rpcResult.buildSuccess(result);
    }

    /**
     * 分页查询-TestOne
     */
    @Override
    public RpcResult<HyitPage<TestOneVO>> paginQueryTestOne(TestOneRpcQueryForm form) throws Exception {
        TestOneQueryForm target = new TestOneQueryForm();
        BeanUtils.copyProperties(form, target);
        // 1.分页参数
        HyitPage<TestOne> page = new HyitPage<>();
        page.setSize(form.getPageSize());
        page.setCurrent(form.getPageNum());
        // 2.分页查询
        HyitPage<TestOneVO> pageResult = testOneService.paginQuery(target, page);
        RpcResult<HyitPage<TestOneVO>> rpcResult = RpcResult.create();
        return rpcResult.buildSuccess(pageResult);
    }

}
