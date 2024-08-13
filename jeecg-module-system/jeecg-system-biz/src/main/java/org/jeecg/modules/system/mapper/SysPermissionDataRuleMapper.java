package org.jeecg.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.system.entity.SysPermissionDataRule;

import java.util.List;

/**
 * <p>
 * 权限规则 Mapper 接口
 * </p>
 *
 * @Author huangzhilin
 * @since 2019-04-01
 */
public interface SysPermissionDataRuleMapper extends BaseMapper<SysPermissionDataRule> {

	/**
	  * 根据用户名和权限id查询
	 * @param username 用户名
	 * @param permissionId 权限id
	 * @return
	 */
	public List<String> queryDataRuleIds(@Param("username") String username,@Param("permissionId") String permissionId);

	/**
	 * 根据用户名和权限id查询
	 * @param username 用户名
	 * @param permissionIds 权限id集合
	 * @return
	 */
	public List<String> queryDataRuleIdsMulti(@Param("username") String username,@Param("permissionIds") List<String> permissionIds);

}
