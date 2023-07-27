package com.atguigu.ssyx.home.controller;


import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.home.service.HomeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 * 属性分组 前端控制器
 * </p>
 *
 * @author qkuang
 * @since 2023-07-12
 */
@RestController
@RequestMapping("/wx/home")
public class HomeController {
    /**
     * 展示的service
     */
    @Autowired
    private HomeService homeService;


    @ApiOperation("展示首页数据")
    @GetMapping("/getuserinfobyuserid")
    public Result showHome() {
        Map<Object, Object> homeData = homeService.showHome();
        return Result.ok(homeData);
    }
}

