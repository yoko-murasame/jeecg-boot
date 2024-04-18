package cn.com.hyit.provider.rpc.test;

import cn.com.hyit.core.base.bean.HyitPage;
import cn.com.hyit.core.base.bean.RpcResult;
import cn.com.hyit.form.test.TestOneRpcAddForm;
import cn.com.hyit.form.test.TestOneRpcDeleteForm;
import cn.com.hyit.form.test.TestOneRpcEditForm;
import cn.com.hyit.form.test.TestOneRpcQueryForm;
import cn.com.hyit.vo.test.TestOneVO;

/**
 * rpc-生成测试表-TestOne
 */
public interface ITestOneRpc {

    /**
     * 添加-TestOne
     */
    RpcResult<String> addTestOne(TestOneRpcAddForm form) throws Exception;

    /**
     * 修改-TestOne
     */
    RpcResult<String> editTestOne(TestOneRpcEditForm form) throws Exception;

    /**
     * 删除-TestOne
     */
    RpcResult<String> deleteTestOne(TestOneRpcDeleteForm form) throws Exception;

    /**
     * 分页查询-TestOne
     */
    RpcResult<HyitPage<TestOneVO>> paginQueryTestOne(TestOneRpcQueryForm form) throws Exception;

}
