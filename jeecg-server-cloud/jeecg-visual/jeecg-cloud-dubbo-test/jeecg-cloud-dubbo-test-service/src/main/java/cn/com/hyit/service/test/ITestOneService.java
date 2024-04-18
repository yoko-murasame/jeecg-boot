package cn.com.hyit.service.test;

import cn.com.hyit.core.base.bean.HyitPage;
import cn.com.hyit.entity.test.TestOne;
import cn.com.hyit.form.test.TestOneDeleteForm;
import cn.com.hyit.form.test.TestOneForm;
import cn.com.hyit.form.test.TestOneQueryForm;
import cn.com.hyit.vo.test.TestOneVO;

/**
 * 服务接口-生成测试表-TestOne
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
