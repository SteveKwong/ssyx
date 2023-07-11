package com.atguigu.ssyx.acl.controller;


import com.atguigu.ssyx.acl.service.IPermissionService;
import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.acl.Permission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


/**
 * <p>
 * 权限 前端控制器
 * </p>
 *
 * @author qkuang
 * @since 2023-07-09
 */
@RestController
@RequestMapping("/admin/acl/permission")
@Api(tags = "角色接口")
@CrossOrigin
@RequiredArgsConstructor
public class PermissionController {


    private final IPermissionService permissionService;


    /**
     * 查询全部的权限列表(树形查询)
     *
     * @return 权限列表
     */
    @ApiOperation("角色条件分页查询")
    @GetMapping("")
    public Result<List<Permission>> queryAllPermissions() {
        List<Permission> roles = permissionService.queryAllPermissions();
        return Result.ok(roles);
    }

    /**
     * 递归删除菜单
     *
     * @return 删除结果
     */
    @ApiOperation("递归删除菜单")
    @DeleteMapping("deletepermissionsbyid/{id}")
    public Result<String> deletePermissionsById(@PathVariable("id") Long id) {
        Optional<Boolean> deleteResult = permissionService.deletePermissionsById(id);
        if (deleteResult.isPresent()) {
            if (deleteResult.get()) {
                return Result.ok("删除成功");
            } else {
                return Result.fail("删除失败");
            }
        }
        return Result.fail("删除过程存在问题,请联系开发人员");
    }

}

