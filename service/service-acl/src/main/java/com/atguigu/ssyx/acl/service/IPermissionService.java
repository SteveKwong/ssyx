package com.atguigu.ssyx.acl.service;

import com.atguigu.ssyx.model.acl.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 权限 服务类
 * </p>
 *
 * @author qkuang
 * @since 2023-07-09
 */
public interface IPermissionService extends IService<Permission> {
    /**
     * 查询全部的权限列表
     * @return 权限列表
     */
    List<Permission> queryAllPermissions();

    /**
     * 根据用户的id删除它的权限
     * @param id
     * @return
     */
    Optional<Boolean> deletePermissionsById(Long id);
}
