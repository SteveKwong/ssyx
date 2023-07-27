package com.atguigu.ssyx.user.controller;

import com.atguigu.ssyx.common.auth.AuthContextHolder;
import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.enums.user.User;
import com.atguigu.ssyx.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author kuanggong
 * @date 2023/7/26 09:29
 * @project_name recharge2
 */
@RestController
@RequestMapping("/api/user/weixin")
public class WexinApiController {

    @Autowired
    private UserService userService;


    @GetMapping("/user/weixin/wxLogin/{code}")
    public Result wxLogin(@PathVariable String code) {
        return userService.getHashMapResult(code);
    }



    @PostMapping("/auth/updateUser")
    @ApiOperation(value = "更新用户昵称与头像")
    public Result updateUser(@RequestBody User user) {
        //获取当前登录用户id
        User user1 = userService.getById(AuthContextHolder.getUserId());
        //把昵称更新为微信用户
        user1.setNickName(user.getNickName().replaceAll("[ue000-uefff]", "*"));
        user1.setPhotoUrl(user.getPhotoUrl());
        userService.updateById(user1);
        return Result.ok(null);
    }
}
