package com.atguigu.ssyx.home.service.impl;

import com.atguigu.ssyx.common.auth.AuthContextHolder;
import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.home.remote.UserService;
import com.atguigu.ssyx.home.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

/**
 * @author kuanggong
 * @date 2023/7/27 09:28
 * @project_name atguigu-ssyx-parent
 */
@Service
public class HomeServiceImpl implements HomeService {
    /**
     * 远程调用用户服务
     */
    @Autowired
    private UserService userService;

    /**
     * 查询首页数据展示
     *
     * @return 首页数据的集合
     */
    @Override
    public Map<Object, Object> showHome() {
        /*
         1.根据user 查询到他的收货地址 他这里是用service-Util 当做全服务的一个Common包,然后去进行各种各样的公共操作，
         通过threadLocal将该次请求的用户封装到holder当中,使用时进行用户的获取
         */
        Long userId = AuthContextHolder.getUserId();
        if (userId == null) {
            return Collections.emptyMap();
        }
        Result<Object> userInfoByUserId = userService.getUserInfoByUserId(userId);
        // 获取商品列表 todo 使用search模块进行商品的搜索功能

        // 获取新人专享 ToDo调用新人专享实现功能
        // 获取爆款商品
        return null;
    }
}
