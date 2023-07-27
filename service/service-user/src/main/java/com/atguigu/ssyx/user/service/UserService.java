package com.atguigu.ssyx.user.service;

import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.enums.user.User;
import com.atguigu.ssyx.vo.user.LeaderAddressVo;
import com.atguigu.ssyx.vo.user.UserLoginVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author qkuang
 * @since 2023-07-26
 */
public interface UserService extends IService<User> {
    /**
     * 通过openId查询user信息
     * @param openid openId
     * @return user信息
     */
    User getUserByOpenId(String openid);

    /**
     * 通过userId获取leader的地址
     * @param id
     * @return
     */
    LeaderAddressVo getLeaderAddressByUserId(Long id);

    /**
     * 通过id获取user的login的信息
     * @param id 用户的id
     * @return userVO
     */
    UserLoginVo getUserLoginVo(Long id);

    /**
     * 登录
     * @param code
     * @return
     */
    Result getHashMapResult(String code);
}
