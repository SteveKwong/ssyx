package com.atguigu.ssyx.user.mapper;

import com.atguigu.ssyx.enums.user.Leader;
import com.atguigu.ssyx.enums.user.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author qkuang
 * @since 2023-07-26
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    /**
     * 展示地址信息
     * @param userId
     * @return
     */
    @Select("SELECT leader.*" +
            " FROM leader" +
            " JOIN  leader_user ON leader.id=leader_user.leader_id" +
            " JOIN `user`  ON `user`.id = leader_user.user_id" +
            " WHERE `user`.id= #{userId}")
    @Results(value = {
            @Result(property = "userId", column = "user_id"),
            @Result(property = "province", column = "province"),
            @Result(property = "city", column = "city"),
            @Result(property = "district", column = "district"),
            @Result(property = "detailAddress", column = "detailAddress")
    })
    Leader showAddress(Long userId);
}
