package org.jeecg.modules.workflow.utils;

import org.apache.commons.lang.StringUtils;
import org.jeecg.modules.extbpm.process.exception.BpmException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * 流程表达式通用工具类
 * 用于配置到流程图的表达式中，实现自定义逻辑
 *
 * @author Yoko
 * @since 2022/6/28 10:00
 */
@Component("yokoFlowUtil")
public class YokoFlowUtil {
    
    /**
     * 自定义的会签人员转换方法，支持抛出自定义异常<br/>
     * e.g.${yokoFlowUtil.stringToList(assigneeUserIdList,'请选择部室会签人员、财政审核人员！')}
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
