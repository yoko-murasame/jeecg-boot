package cn.com.hyit.entity.system;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@ApiModel(value="房地-系统用户实体", description="房地-系统用户实体")
@Accessors(chain = true)
@Data
@EqualsAndHashCode(callSuper = false)
public class SysUser {


    /**
     * 登录人id
     */
    private String id;

    /**
     * 登录人账号
     */
    private String username;

    private String orgCode;

}
