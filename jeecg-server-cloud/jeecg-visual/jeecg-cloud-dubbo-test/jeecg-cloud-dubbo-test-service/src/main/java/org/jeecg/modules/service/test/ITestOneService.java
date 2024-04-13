package org.jeecg.modules.service.test;

import org.jeecg.modules.core.base.bean.HyitPage;
import org.jeecg.modules.entity.test.TestOne;
import org.jeecg.modules.form.test.TestOneDeleteForm;
import org.jeecg.modules.form.test.TestOneForm;
import org.jeecg.modules.form.test.TestOneQueryForm;
import org.jeecg.modules.vo.test.TestOneVO;

/**
 * 服务接口-测试模块-TestOne
 */
public interface ITestOneService {

    /**
     * 新增 TestOne
     *
     * @param form 表单
     * @return java.lang.String
     */
    String addTestOne(TestOneForm form);

    /**
     * 修改 TestOne
     *
     * @param form 表单
     * @return java.lang.String
     */
    String editTestOne(TestOneForm form);

    /**
     * 删除 TestOne
     *
     * @param form 表单
     * @return java.lang.String
     */
    String deleteTestOne(TestOneDeleteForm form);

    /**
     * 分页查询 TestOneVO
     *
     * @param form 筛选条件
     * @param page 分页对象
     * @return HyitPage<TestOneVO>
     */
    HyitPage<TestOneVO> paginQuery(TestOneQueryForm form, HyitPage<TestOne> page);

}
