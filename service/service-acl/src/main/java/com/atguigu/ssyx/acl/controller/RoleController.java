package com.atguigu.ssyx.acl.controller;

import com.atguigu.ssyx.acl.service.RoleService;
import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.acl.Role;
import com.atguigu.ssyx.vo.acl.RoleQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;

/**
 * @author ASUS
 */

@Api(tags = "角色接口")
@RestController
@CrossOrigin
@RequestMapping("/admin/acl/role")
@RequiredArgsConstructor
public class RoleController {
    /**
     * 角色服务层
     */
    private final RoleService roleService;

    /**
     * 角色条件分页查询
     *
     * @param current     当前页数
     * @param limit       显示数量
     * @param roleQueryVo 查询条件
     * @return
     */
    @ApiOperation("角色条件分页查询")
    @GetMapping("{current}/{limit}")
    public Result pageList(@PathVariable @NotNull Long current,
                           @PathVariable @NotNull Long limit,
                           RoleQueryVo roleQueryVo) {
        //https://www.bilibili.com/video/BV19M4y1q7Lt?t=1.3&p=15
        Page<Role> pageParam = new Page<>(current, limit);
        IPage<Role> roles = roleService.selectRolePage(pageParam, roleQueryVo);
        return Result.ok(roles);
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
     *
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

    /**
     * 通过id获取该角色
     *
     * @param id id
     * @return 角色信息
     */
    @ApiOperation("登出")
    @PostMapping("get/{id}")
    public Result getRoleById(@NotBlank @PathVariable Long id) {
        Role role = roleService.getById(id);
        return Result.ok(role);
    }

    /**
     * 保存角色
     *
     * @param role 角色信息
     * @return 是否保存成功
     */
    @ApiOperation("保存角色")
    @PostMapping("keep/{id}")
    public Result save(@NotNull Role role) {
        return Result.ok(roleService.save(role));
    }

    /**
     * 修改角色
     *
     * @param role 修改角色
     * @return
     */
    @ApiOperation("修改角色")
    @PutMapping("update")
    public Result update(@RequestBody Role role) {
        return Result.ok(roleService.updateById(role));
    }

    /**
     * 删除角色
     *
     * @param id 删除角色
     * @return
     */
    @ApiOperation("删除角色")
    @DeleteMapping("delete{id}")
    public Result deleteById(@NotBlank @PathVariable Long id) {
        return Result.ok(roleService.removeById(id));
    }

    /**
     * 批量删除角色
     *
     * @param idList 删除角色集合
     * @return
     */
    @ApiOperation("删除角色")
    @DeleteMapping("batchRemove")
    public Result batchRemove(@RequestBody List<Long> idList) {
        roleService.removeByIds(idList);
        return Result.ok(null);
    }

}
