package com.atguigu.ssyx.acl.service.impl;

import com.atguigu.ssyx.acl.mapper.AdminMapper;
import com.atguigu.ssyx.acl.mapper.RoleMapper;
import com.atguigu.ssyx.acl.service.IAdminService;
import com.atguigu.ssyx.model.acl.Admin;
import com.atguigu.ssyx.model.acl.AdminRoleResponse;
import com.atguigu.ssyx.vo.acl.AdminQueryVo;
import com.atguigu.ssyx.vo.acl.AdminRoleVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author qkuang
 * @since 2023-07-07
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

    @Autowired
    private RoleMapper roleMapper;

    /**
     * 获取admin列表
     *
     * @param pageParam    页面传参
     * @param adminQueryVo 实体对象
     * @return 实体列表
     */
    @Override
    public IPage<Admin> getAdminList(Page<Admin> pageParam, AdminQueryVo adminQueryVo) {
        String name = adminQueryVo.getName();
        String username = adminQueryVo.getUsername();
        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(name)) {
            queryWrapper.like(Admin::getName, name);
        }
        if (!StringUtils.isEmpty(username)) {
            queryWrapper.like(Admin::getUsername, username);
        }
        IPage<Admin> adminPage = this.baseMapper.selectPage(pageParam, queryWrapper);
        return adminPage;
    }

    /**
     * 获取用户角色信息
     *
     * @param id 用户id
     * @return 获取结果
     */
    @Override
    public List<AdminRoleResponse> getAdminRoles(Long id) {
        // 获取全部的角色名称
        try {
            // 先获取到全部的名称
            List<String> roleNames = baseMapper.selectCoursesByStudentId(id);
            // 返回结果集合
            return roleMapper.selectList(null).stream().map((role) -> {
                AdminRoleResponse adminRoleResponse = new AdminRoleResponse();
                String roleName = role.getRoleName();
                adminRoleResponse.setRoleName(roleName);
                adminRoleResponse.setRoleId(role.getId());
                // admin当中存在id 你只需要先查出这个角色所拥有的角色名字然后去对比
                for (String name : roleNames) {
                    if (name.equals(roleName)) {
                        adminRoleResponse.setHasPermissions(Boolean.TRUE);
                    }
                }
                return adminRoleResponse;
            }).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("程序出现错误{}", e);
        }
        return null;
    }

    /**
     * 修改用户角色信息
     *
     * @param adminRoleVo 用户角色信息
     * @return 修改结果
     */
    @Override
    public boolean addAdminRoles(AdminRoleVo adminRoleVo) {
        // 首先要删除中间表,其次,我们添加也是通过中间表
        baseMapper.deleteAdminRolesByAdminId(adminRoleVo.getUserid());
        // 添加到新表当中去
        if (!adminRoleVo.getRoles().isEmpty()) {
            boolean updateResult = baseMapper.updateAdminRoles(adminRoleVo.getUserid(), adminRoleVo.getRoles());
            if (updateResult) {
                return true;
            } else {
                return false;
            }
        }
        return true;
    }
}
