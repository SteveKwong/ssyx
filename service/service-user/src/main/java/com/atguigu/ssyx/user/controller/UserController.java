package com.atguigu.ssyx.user.controller;


import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.user.service.UserService;
import com.atguigu.ssyx.vo.user.LeaderAddressVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author qkuang
 * @since 2023-07-26
 */
@RestController
@RequestMapping("/sys/user")
public class UserController {

    /**
     * 用户服务层
     */
    @Autowired
    private UserService userService;

    @ApiOperation("展示首页数据")
    @GetMapping("/show/{userid}")
    public Result showAddress(@PathVariable(value = "userid") Long userId) {
        // 他这里的收货地址是通过user的信息查询团长的收货地址
        LeaderAddressVo leaderAddressVo = userService.showAddress(userId);
        return Result.ok(leaderAddressVo);
    }

}

