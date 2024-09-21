package org.jeecg.modules.workflow.utils;

import org.apache.commons.lang.StringUtils;
import org.jeecg.common.api.CommonAPI;
import org.jeecg.common.system.vo.SysUserModel;
import org.jeecg.modules.extbpm.process.exception.BpmException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 流程表达式通用工具类
 * 用于配置到流程图的表达式中，实现自定义逻辑
 *
 * @author Yoko
 * @since 2022/6/28 10:00
 */
@Component("yokoFlowUtil")
public class YokoFlowUtil {

    @Resource
    private CommonAPI commonAPI;

    /**
     * 根据指定角色code获取所有用户名，用于流程会签
     * e.g.${yokoFlowUtil.getUserListByRoleCodes('admin','没有配置处理人员，请检查角色配置！')}
     * e.g.接着在人员配置这里选择：处理人，表达式：${assigneeUserId}
     *
     * @author Yoko
     * @since 2024/8/20 20:17
     * @param roleCodes 角色编码
     * @param errorMsg 自定义错误信息
     * @return java.util.List<java.lang.String>
     */
    public List<String> getUserListByRoleCodes(String roleCodes, String errorMsg) {
        if (StringUtils.isEmpty(roleCodes)) {
            throw new BpmException("getUserListByRoleCodes方法必须传入角色编码！");
        }
        List<SysUserModel> userModelByRoleCodes = commonAPI.getUserModelByRoleCodes(roleCodes);
        if (userModelByRoleCodes.isEmpty()) {
            errorMsg = Optional.ofNullable(errorMsg).orElse("角色编码：" + roleCodes + "没有配置人员，请检查角色配置！");
            throw new BpmException(errorMsg);
        }
        return userModelByRoleCodes.stream().map(SysUserModel::getUsername).filter(StringUtils::isNotEmpty).collect(Collectors.toList());
    }

    /**
     * 自定义的会签人员转换方法，支持抛出自定义异常<br/>
     * e.g.${yokoFlowUtil.stringToList(assigneeUserIdList,'请选择部室会签人员、财政审核人员！')}
     * e.g.接着在人员配置这里选择：处理人，表达式：${assigneeUserId}
     *
     * @param assigneeUserIdList 会签人员,分割的字符串
     * @param errorMsg 错误时的提示，注意流程配置的时候，需要使用单引号引起来，双引号会导致xml报错，销毁整个流程！
     * @return java.util.List<java.lang.String>
     * @author Yoko
     */
    public List<String> stringToList(String assigneeUserIdList, String errorMsg) {
        if (StringUtils.isEmpty(assigneeUserIdList)) {
            errorMsg = Optional.ofNullable(errorMsg).orElse("下一步是会签节点，请选择会签人员!");
            throw new BpmException(errorMsg);
        } else {
            String[] arr = assigneeUserIdList.split(",");
            return Arrays.asList(arr);
        }
    }

    /**
     * 判断绝对值小于等于指定数字
     *
     * @param value 来源数值 取绝对值 和下面比较
     * @param number 比较的数值
     * @return boolean
     * @author Yoko
     */
    public boolean leqAbsNumber(BigDecimal value, Integer number) {
        double newVal = Optional.ofNullable(value).orElse(new BigDecimal(0)).abs().doubleValue();
        return newVal <= number;
    }

    /**
     * 判断绝对值大于指定数字
     *
     * @param value 来源数值 取绝对值 和下面比较
     * @param number 比较的数值
     * @return boolean
     * @author Yoko
     */
    public boolean gtAbsNumber(BigDecimal value, Integer number) {
        double newVal = Optional.ofNullable(value).orElse(new BigDecimal(0)).abs().doubleValue();
        return newVal > number;
    }

}
