package cn.com.hyit.rest.test;

import cn.com.hyit.core.base.bean.HyitPage;
import cn.com.hyit.core.logger.aspect.FinOperationLog;
import cn.com.hyit.core.logger.enums.LogTypeEnum;
import cn.com.hyit.core.logger.enums.OperTypeEnum;
import cn.com.hyit.core.web.response.BaseRest;
import cn.com.hyit.core.web.response.WebResponse;
import cn.com.hyit.entity.test.TestOne;
import cn.com.hyit.form.test.TestOneDeleteForm;
import cn.com.hyit.form.test.TestOneForm;
import cn.com.hyit.form.test.TestOneQueryForm;
import cn.com.hyit.service.test.ITestOneService;
import cn.com.hyit.vo.test.TestOneVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Optional;

/**
 * 表控制层-生成测试表-TestOne
 */
@Slf4j
@Api(tags = "生成测试表-TestOne-功能接口")
@RestController
@RequestMapping("/test/testOne")
public class TestOneRest extends BaseRest {

    @Resource
    private ITestOneService testOneService;

    @ApiOperation("新增-生成测试表-TestOne")
    @FinOperationLog(value = "新增-生成测试表-TestOne", type = OperTypeEnum.ADD, logType = LogTypeEnum.BUSINESS, operModule = "test")
    @PostMapping("/addTestOne")
    public WebResponse<String> addTestOne(@Valid @RequestBody TestOneForm form) {
        // 当前登陆人信息获取 AuthUserDetail authUser = HyitAuthUtils.getCurrentUser();
        return buildSuccessResponse(testOneService.addTestOne(form));
    }

    @ApiOperation("更新-生成测试表-TestOne")
    @FinOperationLog(value = "更新-生成测试表-TestOne", type = OperTypeEnum.UPDATE, logType = LogTypeEnum.BUSINESS, operModule = "test")
    @PostMapping("/editTestOne")
    public WebResponse<String> updateTestOne(@Valid @RequestBody TestOneForm form) {
        // 当前登陆人信息获取 AuthUserDetail authUser = HyitAuthUtils.getCurrentUser();
        return buildSuccessResponse(testOneService.editTestOne(form));
    }

    @ApiOperation("删除-生成测试表-TestOne")
    @FinOperationLog(value = "删除-生成测试表-TestOne", type = OperTypeEnum.DELETE, logType = LogTypeEnum.BUSINESS, operModule = "test")
    @PostMapping("/deleteTestOne")
    public WebResponse<String> deleteTestOne(@RequestBody TestOneDeleteForm form) {
        // 当前登陆人信息获取 AuthUserDetail authUser = HyitAuthUtils.getCurrentUser();
        return buildSuccessResponse(testOneService.deleteTestOne(form));
    }

    @ApiOperation("分页查询-生成测试表-TestOne")
    @FinOperationLog(value = "分页查询-生成测试表-TestOne", type = OperTypeEnum.QUERY, logType = LogTypeEnum.BUSINESS, operModule = "test")
    @PostMapping("/paginQueryTestOne")
    public WebResponse<HyitPage<TestOneVO>> paginQueryTestOne(@RequestBody TestOneQueryForm form) {
        // 当前登陆人信息获取 AuthUserDetail authUser = HyitAuthUtils.getCurrentUser();
        // 1.分页参数
        HyitPage<TestOne> page = new HyitPage<>();
        page.setSize(Optional.ofNullable(form.getPageSize()).orElse(20));
        page.setCurrent(Optional.ofNullable(form.getPageNum()).orElse(1));
        // 2.分页查询
        HyitPage<TestOneVO> pageResult = testOneService.paginQuery(form, page);
        return buildSuccessResponse(pageResult);
    }

}
