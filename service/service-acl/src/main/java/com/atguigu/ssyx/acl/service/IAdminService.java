package com.atguigu.ssyx.acl.service;

import com.atguigu.ssyx.model.acl.Admin;
import com.atguigu.ssyx.model.acl.AdminRoleResponse;
import com.atguigu.ssyx.vo.acl.AdminQueryVo;
import com.atguigu.ssyx.vo.acl.AdminRoleVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author qkuang
 * @since 2023-07-07
 */
public interface IAdminService extends IService<Admin> {
    /**
     * 获取admin列表
     *
     * @param pageParam    页面传参
     * @param adminQueryVo 实体对象
     * @return 实体列表
     */
    IPage<Admin> getAdminList(Page<Admin> pageParam, AdminQueryVo adminQueryVo);

    /**
     * 获取全部的角色名称和该用户是否拥有该权限
     *
     * @param id
     * @return
     */
    List<AdminRoleResponse> getAdminRoles(Long id);

    /**
     * 添加用户权限
     *
     * @param adminRoleVo 用户角色信息
     * @return 更新结果
     */
    boolean addAdminRoles(AdminRoleVo adminRoleVo);
}
