package org.jeecg.modules.provider.rpc.test;

import org.jeecg.modules.core.base.bean.HyitPage;
import org.jeecg.modules.core.base.bean.RpcResult;
import org.jeecg.modules.form.test.TestOneRpcAddForm;
import org.jeecg.modules.form.test.TestOneRpcDeleteForm;
import org.jeecg.modules.form.test.TestOneRpcEditForm;
import org.jeecg.modules.form.test.TestOneRpcQueryForm;
import org.jeecg.modules.vo.test.TestOneVO;

/**
 * rpc-测试模块-TestOne
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
