package cn.com.hyit.provider.rpc.test;

import cn.com.hyit.core.base.bean.HyitPage;
import cn.com.hyit.core.base.bean.RpcResult;
import cn.com.hyit.entity.test.TestOne;
import cn.com.hyit.form.test.*;
import cn.com.hyit.service.test.ITestOneService;
import cn.com.hyit.vo.test.TestOneVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * rpc服务实现类-生成测试表-TestOne
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
        page.setSize(Optional.ofNullable(form.getPageSize()).orElse(20));
        page.setCurrent(Optional.ofNullable(form.getPageNum()).orElse(1));
        // 2.分页查询
        HyitPage<TestOneVO> pageResult = testOneService.paginQuery(target, page);
        RpcResult<HyitPage<TestOneVO>> rpcResult = RpcResult.create();
        return rpcResult.buildSuccess(pageResult);
    }

}
