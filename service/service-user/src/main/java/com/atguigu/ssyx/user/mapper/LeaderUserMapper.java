package com.atguigu.ssyx.user.mapper;

import com.atguigu.ssyx.enums.user.LeaderUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 团长会员关系表 Mapper 接口
 * </p>
 *
 * @author qkuang
 * @since 2023-07-26
 */
@Mapper
public interface LeaderUserMapper extends BaseMapper<LeaderUser> {

}
