package cn.com.hyit.service.test;

import cn.com.hyit.config.util.QueryGenerator;
import cn.com.hyit.core.base.bean.HyitPage;
import cn.com.hyit.dao.test.TestOneMapper;
import cn.com.hyit.entity.test.TestOne;
import cn.com.hyit.form.test.TestOneDeleteForm;
import cn.com.hyit.form.test.TestOneForm;
import cn.com.hyit.form.test.TestOneQueryForm;
import cn.com.hyit.vo.test.TestOneVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 服务实现类-生成测试表-TestOne
 */
@Slf4j
@Service
public class TestOneService implements ITestOneService {

    @Resource
    private TestOneMapper testOneMapper;

    /**
     * 新增 TestOne
     *
     * @param form 表单
     * @return java.lang.String
     */
    @Override
    public String addTestOne(TestOneForm form) {
        TestOne entity = new TestOne();
        BeanUtils.copyProperties(form, entity);
        testOneMapper.insert(entity);
        return "添加成功";
    }

    /**
     * 修改 TestOne
     *
     * @param form 表单
     * @return java.lang.String
     */
    @Override
    public String editTestOne(TestOneForm form) {
        Assert.hasText(form.getId(), "id不能为空");
        TestOne entity = new TestOne();
        BeanUtils.copyProperties(form, entity);
        testOneMapper.updateById(entity);
        return "修改成功";
    }

    /**
     * 删除 TestOne
     *
     * @param form 表单
     * @return java.lang.String
     */
    @Override
    public String deleteTestOne(TestOneDeleteForm form) {
        Assert.hasText(form.getId(), "id不能为空");
        testOneMapper.deleteById(form.getId());
        return "删除成功";
    }

    /**
     * 分页查询 TestOneVO
     *
     * @param form 筛选条件
     * @param page 分页对象
     * @return HyitPage<TestOneVO>
     */
    @Override
    public HyitPage<TestOneVO> paginQuery(TestOneQueryForm form, HyitPage<TestOne> page) {
        TestOne entity = new TestOne();
        BeanUtils.copyProperties(form, entity);
        QueryWrapper<TestOne> wrapper = QueryGenerator.initQueryWrapper(entity, null);
        HyitPage<TestOne> result = testOneMapper.selectPageCustom(page, wrapper);
        List<TestOneVO> vos = result.getRecords().stream().map(item -> {
            TestOneVO vo = new TestOneVO();
            BeanUtils.copyProperties(item, vo);
            return vo;
        }).collect(Collectors.toList());
        HyitPage<TestOneVO> pageResult = new HyitPage<>();
        pageResult.setCurrent(result.getCurrent());
        pageResult.setSize(result.getSize());
        pageResult.setTotal(result.getTotal());
        pageResult.setRecords(vos);
        return pageResult;
    }

}
