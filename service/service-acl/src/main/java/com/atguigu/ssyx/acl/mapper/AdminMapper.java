package com.atguigu.ssyx.acl.mapper;

import com.atguigu.ssyx.model.acl.Admin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author qkuang
 * @since 2023-07-07
 */
@Repository
public interface AdminMapper extends BaseMapper<Admin> {
    /**
     * 查询用户所拥有的权限
     *
     * @param id 用户id
     * @return 权限列表
     */
    @Select("SELECT  role.role_name\n" +
            "FROM admin\n" +
            "JOIN admin_role ON admin.id = admin_role.admin_id\n" +
            "JOIN role ON  role.id= admin_role.role_id\n" +
            "WHERE admin.id = #{id}")
    List<String> selectCoursesByStudentId(@Param("id") Long id);

    /**
     * 删除角色信息
     *
     * @param userid 用户id
     * @return 删除结果
     */
    @Update("DELETE FROM admin_role WHERE admin_id = #{id}")
    boolean deleteAdminRolesByAdminId(Long userid);

    /**
     * 更新用户所拥有的权限
     *
     * @param userid 角色id
     * @param roles  角色列表
     * @return 更新结果
     */
    @Insert("<script>" +
            "INSERT INTO admin_role (admin_id, role_id) VALUES " +
            "<foreach collection='roles' item='item' separator=','>" +
            "(#{userid}, #{item})" +
            "</foreach>" +
            "</script>")
    boolean updateAdminRoles(@Param("userid") Long userid, @Param("roles") List<Long> roles);


}
