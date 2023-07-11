package com.atguigu.ssyx.acl.controller;

import com.atguigu.ssyx.acl.service.IAdminService;
import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.acl.Admin;
import com.atguigu.ssyx.model.acl.AdminRoleResponse;
import com.atguigu.ssyx.vo.acl.AdminQueryVo;
import com.atguigu.ssyx.vo.acl.AdminRoleVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author qkuang
 * @since 2023-07-07
 */
@Api(tags = "用户接口")
@RestController
@CrossOrigin
@RequestMapping("/admin/acl/admin")
@RequiredArgsConstructor
public class AdminController {


    private final IAdminService adminService;


    // 获取用户列表

    /**
     * 获取登录信息
     *
     * @return 用户头像及姓名
     */
    @ApiOperation("获取登录信息")
    @GetMapping("{current}/{limit}")
    public Result getAdminList(@PathVariable @NotNull Long current,
                               @PathVariable @NotNull Long limit,
                               AdminQueryVo adminQueryVo) {
        Page<Admin> pageParam = new Page<>(current, limit);
        IPage<Admin> admins = adminService.getAdminList(pageParam, adminQueryVo);
        return Result.ok(admins);
    }

    /**
     * 获取用户的角色和全部角色
     *
     * @return 用户的角色和全部的角色
     */
    @ApiOperation("获取登录信息")
    @GetMapping("getadminroles/{id}")
    public Result getAdminRoles(@PathVariable @NotNull Long id) {
        // 两个字段,是否拥有权限和角色名称
        List<AdminRoleResponse> adminList = adminService.getAdminRoles(id);
        return Result.ok(adminList);
    }

    /**
     * 获取用户的角色和全部角色
     *
     * @return 用户的角色和全部的角色
     */
    @ApiOperation("获取登录信息")
    @PostMapping("add/adminroles")
    //https://www.bilibili.com/video/BV19M4y1q7Lt?t=18.1&p=22
    public Result addAdminRoles(@RequestBody @Valid AdminRoleVo adminRoleVo) {
        boolean addSuccess = adminService.addAdminRoles(adminRoleVo);
        if (addSuccess) {
            return Result.ok("修改用户权限成功");
        } else {
            return Result.fail("修改用户权限失败");
        }
    }
}

