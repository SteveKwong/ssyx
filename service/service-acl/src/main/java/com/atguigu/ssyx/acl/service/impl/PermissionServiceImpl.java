package com.atguigu.ssyx.acl.service.impl;

import com.atguigu.ssyx.acl.mapper.PermissionMapper;
import com.atguigu.ssyx.acl.service.IPermissionService;
import com.atguigu.ssyx.model.acl.Permission;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 * 权限 服务实现类
 * </p>
 *
 * @author qkuang
 * @since 2023-07-09
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {
    /**
     * 查询全部的权限列表
     *
     * @return
     */
    @Override
    public List<Permission> queryAllPermissions() {
        List<Permission> permissions = baseMapper.selectList(null);
        // 通过递归的方式,查询到所有的子菜单
        return permissions.stream()
                .filter(permission -> permission.getPid() == 0)
                .map((permission) -> {
                    permission.setChildren(getChilrenList(permission, permissions));
                    return permission;
                }).collect(Collectors.toList());
    }

    /**
     * 递归删除菜单
     *
     * @param id 用户id
     * @return 用户所拥有的权限
     */
    @Override
    public Optional<Boolean> deletePermissionsById(Long id) {
        try {
            List<Long> shouldDeleteIds = new ArrayList<>();
            // 查询出应该要删除的ids 通过菜单的id
            getAllId(id, shouldDeleteIds);
            if (baseMapper.deleteBatchIds(shouldDeleteIds) > 0) {
                return Optional.of(Boolean.TRUE);
            } else {
                return Optional.of(Boolean.FALSE);
            }
        } catch (Exception e) {
            log.error("程序出现问题{}", e);
        }
        return Optional.empty();
    }

    /**
     * 获取全部的ID   https://www.bilibili.com/video/BV19M4y1q7Lt?t=1120.8&p=25
     * @param id 用户id
     * @param shouldDeleteIds 要删除的id集合
     */
    private void getAllId(Long id, List<Long> shouldDeleteIds) {
        LambdaQueryWrapper<Permission> queryWrapper = new LambdaQueryWrapper<>();
        List<Long> ids = baseMapper.selectList(queryWrapper).stream()
                .map(Permission::getId)
                .collect(Collectors.toList());
        shouldDeleteIds.add(id);
        shouldDeleteIds.addAll(ids);
    }


    /**
     * 该方法用来递归子项,查询每一个该项目中的子项
     *
     * @param permission         父项
     * @param permissionEntities 全部的集合
     * @return 子项集合
     */
    private List<Permission> getChilrenList(Permission permission, List<Permission> permissionEntities) {
        List<Permission> childrenList = permissionEntities.stream()
                .filter(permissionEntity -> permission.getId().equals(permissionEntity.getPid()))
                .map((permissionEntity) -> {
                    // 通过方法来获取集合中每个子类中的项目
                    permissionEntity.setChildren(getChilrenList(permissionEntity, permissionEntities));
                    return permissionEntity;
                })
                .collect(Collectors.toList());
        return childrenList;
    }
}
