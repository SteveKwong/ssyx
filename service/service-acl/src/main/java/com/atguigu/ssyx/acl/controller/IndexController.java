package com.atguigu.ssyx.acl.controller;

import com.atguigu.ssyx.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * @author ASUS
 */

@Api(tags = "登录接口")
@RestController
@CrossOrigin
@RequestMapping("/admin/acl/index")
public class IndexController {
    /**
     * 登录接口
     *
     * @return token
     */
    @ApiOperation("登录接口")
    @PostMapping("login")
    public Result login() {
        HashMap<String, String> loginResult = new HashMap<>();
        loginResult.put("name", "admin");
        loginResult.put("avatar", "https://m.kuaikanmanhua.com/webs/world/1231104785333267989152");
        return Result.ok(loginResult);
    }

    /**
     * 获取登录信息
     *
     * @return 用户头像及姓名
     */
    @ApiOperation("获取登录信息")
    @GetMapping("info")
    public Result getInfo() {
        HashMap<String, String> infoMaps = new HashMap<>();
        infoMaps.put("name", "admin");
        infoMaps.put("avatar", "https://m.kuaikanmanhua.com/webs/world/1231104785333267989152");
        return Result.ok(infoMaps);
    }

    /**
     * 退出登录
     * @return 清空Cookie当中的用户token
     */
    @ApiOperation("登出")
    @PostMapping("logOut")
    public Result logOut() {
        HashMap<String, String> infoMaps = new HashMap<>();
        infoMaps.put("name", "admin");
        infoMaps.put("avatar", "admin");
        return Result.ok(infoMaps);
    }
}
