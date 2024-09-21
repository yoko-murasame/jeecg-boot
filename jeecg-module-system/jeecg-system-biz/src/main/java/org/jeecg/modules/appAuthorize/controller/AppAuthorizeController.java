package org.jeecg.modules.appAuthorize.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.common.util.TokenUtils;
import org.jeecg.modules.appAuthorize.entity.DO.AppAuthorize;
import org.jeecg.modules.appAuthorize.entity.VO.getTokenVO;
import org.jeecg.modules.appAuthorize.service.IAppAuthorizeService;
import org.jeecg.modules.system.entity.SysUser;
import org.jeecg.modules.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
@RequestMapping("token")
public class AppAuthorizeController {
    @Autowired
    private IAppAuthorizeService appAuthorizeService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private ISysUserService userService;

    /**
     * 获取临时token
     *
     * @return
     */
    @GetMapping("accessToken")
    public Result getAccessToken(@RequestParam String appKey) {
        AppAuthorize appAuthorize = appAuthorizeService.getOne(new LambdaQueryWrapper<AppAuthorize>()
                .eq(AppAuthorize::getAppKey, appKey));
        if (appAuthorize == null) {
            return Result.error("应用不存在");
        }
        SysUser user=userService.getUserByName("bjcbd");
        // 生成token
        String token = JwtUtil.sign(user.getUsername(), user.getPassword());
        // 设置token缓存有效时间
        redisUtil.set(CommonConstant.PREFIX_USER_TOKEN + token, token);
        redisUtil.expire(CommonConstant.PREFIX_USER_TOKEN + token, 30);

        return Result.OK(token);
    }

    /**
     * 获取正式Token
     *
     * @return
     */
    @GetMapping("getToken")
    public Result getToken(HttpServletRequest request, getTokenVO tokenVO) {
        String accesstoken = TokenUtils.getTokenByRequest(request);
        Boolean r = redisUtil.hasKey(CommonConstant.PREFIX_USER_TOKEN + accesstoken);
        if (!r) {
            return Result.error("签名已失效");
        }
        String accessToken = redisUtil.get(CommonConstant.PREFIX_USER_TOKEN + accesstoken).toString();
        if (!accessToken.equals(accesstoken)) {
            return Result.error("签名无效");
        }
        AppAuthorize appAuthorize = appAuthorizeService.getOne(new LambdaQueryWrapper<AppAuthorize>()
                .eq(AppAuthorize::getAppKey, tokenVO.getAppKey())
                .eq(AppAuthorize::getAppSecret, tokenVO.getAppSecret()));
        if (appAuthorize == null) {
            return Result.error("应用不匹配");
        }
        // 生成token
        SysUser user=userService.getUserByName("bjcbd");
        // 生成token
        String token = JwtUtil.sign(user.getUsername(), user.getPassword());
        // 设置token缓存有效时间
        redisUtil.set(CommonConstant.PREFIX_USER_TOKEN + token, token);
        redisUtil.expire(CommonConstant.PREFIX_USER_TOKEN + token, JwtUtil.EXPIRE_TIME * 2 / 1000);

        redisUtil.del(CommonConstant.PREFIX_USER_TOKEN + accesstoken);
        return Result.OK(token);
    }
}
