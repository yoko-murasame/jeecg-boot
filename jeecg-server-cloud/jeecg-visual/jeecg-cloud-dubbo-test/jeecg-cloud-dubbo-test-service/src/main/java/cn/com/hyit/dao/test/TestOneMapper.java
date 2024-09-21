package cn.com.hyit.dao.test;

import cn.com.hyit.entity.test.TestOne;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

/**
 * Mapper-生成测试表-TestOne
 */
@Mapper
public interface TestOneMapper extends BaseMapper<TestOne> {

    <E extends IPage<TestOne>> E selectPageCustom(E page, @Param(Constants.WRAPPER) Wrapper<TestOne> wrapper);

    List<TestOne> selectListCustom(@Param(Constants.WRAPPER) Wrapper<TestOne> queryWrapper);

    TestOne selectOneCustom(@Param(Constants.WRAPPER) Wrapper<TestOne> queryWrapper);

    TestOne selectByIdCustom(@Param("id") Serializable id);

}
