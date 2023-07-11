package com.atguigu.ssyx.model.acl;

import lombok.Data;

/**
 * @author ASUS
 */
@Data
public class AdminRoleResponse {
    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 是否拥有权限
     */
    private boolean hasPermissions=false;
    /**
     * 角色ID
     */
    private Long roleId;
}
