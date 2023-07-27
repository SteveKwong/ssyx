package com.atguigu.ssyx.common.auth;

import com.atguigu.JwtHelper;
import com.atguigu.ssyx.common.constant.RedisConst;
import com.atguigu.ssyx.vo.user.UserLoginVo;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author kuanggong
 * @date 2023/7/26 15:03
 * @project_name atguigu-ssyx-parent
 */
public class UserLoginInterceptor implements HandlerInterceptor {

    private RedisTemplate redisTemplate;

    public UserLoginInterceptor(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request,
                             @NotNull HttpServletResponse response,
                             Object handler) {
        getUserLoginVO(request);
        return true;
    }

    /**
     * 获取
     *
     * @param request
     */
    private void getUserLoginVO(HttpServletRequest request) {
        // 从请求头里面获取token
        String token = request.getHeader("token");
        if (StringUtils.isNotEmpty(token)) {
            Long userId = JwtHelper.getUserId(token);
            // 根据userId到redis获取用户信息
            UserLoginVo userLoginVo = (UserLoginVo) redisTemplate.opsForValue()
                    .get(RedisConst.USER_LOGIN_KEY_PREFIX + userId);
            if (userLoginVo != null) {
                AuthContextHolder.setUserLoginVo(userLoginVo);
                AuthContextHolder.setUserId(userLoginVo.getUserId());
                AuthContextHolder.setWareId(userLoginVo.getWareId());
            }
        }
    }
}
