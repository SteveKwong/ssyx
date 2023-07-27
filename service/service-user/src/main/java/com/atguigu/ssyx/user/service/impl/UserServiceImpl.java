package com.atguigu.ssyx.user.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.atguigu.JwtHelper;
import com.atguigu.ssyx.common.constant.RedisConst;
import com.atguigu.ssyx.common.exception.SsyxException;
import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.common.result.ResultCodeEnum;
import com.atguigu.ssyx.enums.UserType;
import com.atguigu.ssyx.enums.user.Leader;
import com.atguigu.ssyx.enums.user.User;
import com.atguigu.ssyx.enums.user.UserDelivery;
import com.atguigu.ssyx.user.converter.LeaderConverterMapper;
import com.atguigu.ssyx.user.mapper.LeaderMapper;
import com.atguigu.ssyx.user.mapper.UserDeliveryMapper;
import com.atguigu.ssyx.user.mapper.UserMapper;
import com.atguigu.ssyx.user.service.UserService;
import com.atguigu.ssyx.user.utils.ConstantPropertiesUtil;
import com.atguigu.ssyx.user.utils.HttpClientUtils;
import com.atguigu.ssyx.vo.user.LeaderAddressVo;
import com.atguigu.ssyx.vo.user.UserLoginVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author qkuang
 * @since 2023-07-26
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserDeliveryMapper userDeliveryMapper;
    @Autowired
    private LeaderMapper leaderMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 通过openId获取user
     *
     * @param openid openId
     * @return user信息
     */
    @Override
    public User getUserByOpenId(String openid) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getOpenId, openid);
        return baseMapper.selectOne(queryWrapper);
    }

    /**
     * 获取当前登录用户信息
     *
     * @param id 用户的id
     * @return
     */
    @Override
    public UserLoginVo getUserLoginVo(Long id) {
        User user = baseMapper.selectById(id);
        UserLoginVo userLoginVo = new UserLoginVo();
        userLoginVo.setUserId(id);
        userLoginVo.setNickName(user.getNickName());
        userLoginVo.setPhotoUrl(user.getPhotoUrl());
        userLoginVo.setIsNew(user.getIsNew());
        userLoginVo.setOpenId(user.getOpenId());

        UserDelivery userDelivery = userDeliveryMapper.selectOne(
                new LambdaQueryWrapper<UserDelivery>().eq(UserDelivery::getUserId, id)
                        .eq(UserDelivery::getIsDefault, 1)
        );
        if (userDelivery != null) {
            userLoginVo.setLeaderId(userDelivery.getLeaderId());
            userLoginVo.setWareId(userDelivery.getWareId());
        } else {
            userLoginVo.setLeaderId(1L);
            userLoginVo.setWareId(1L);
        }
        return userLoginVo;
    }

    /**
     * 根据userId查询提货点和团长信息
     *
     * @param userId
     * @return
     */
    @Override
    public LeaderAddressVo getLeaderAddressByUserId(Long userId) {
        //根据userId查询用户默认的团长id
        UserDelivery userDelivery = userDeliveryMapper.selectOne(
                new LambdaQueryWrapper<UserDelivery>()
                        .eq(UserDelivery::getUserId, userId)
                        .eq(UserDelivery::getIsDefault, 1)
        );
        if (userDelivery == null) {
            return null;
        }
        //拿着上面查询团长id查询leader表查询团长其他信息
        Leader leader = leaderMapper.selectById(userDelivery.getLeaderId());
        //封装数据到LeaderAddressVo
        LeaderAddressVo leaderAddressVo = new LeaderAddressVo();
        BeanUtils.copyProperties(leader, leaderAddressVo);
        leaderAddressVo.setUserId(userId);
        leaderAddressVo.setLeaderId(leader.getId());
        leaderAddressVo.setLeaderName(leader.getName());
        leaderAddressVo.setLeaderPhone(leader.getPhone());
        leaderAddressVo.setWareId(userDelivery.getWareId());
        leaderAddressVo.setStorePath(leader.getStorePath());
        return leaderAddressVo;
    }

    /**
     * 获取登录信息
     *
     * @param code
     * @return
     */
    @Override
    public Result<HashMap<String, Object>> getHashMapResult(String code) {
        // 拿到微信返回的Code临时票据值 加上 微信的用户和密码 上传
        String wxOpenAppId = ConstantPropertiesUtil.WX_OPEN_APP_ID;
        String wxOpenAppSecret = ConstantPropertiesUtil.WX_OPEN_APP_SECRET;

        StringBuffer url = new StringBuffer()
                .append("https://api.weixin.qq.com/sns/jscode2session")
                .append("?appid=%s")
                .append("&secret=%s")
                .append("&js_code=%s")
                .append("&grant_type=authorization_code");
        String tokenUrl = String.format(url.toString(),
                wxOpenAppId,
                wxOpenAppSecret,
                code);
        //HttpClient发送get请求
        String result;
        try {
            result = HttpClientUtils.get(tokenUrl);
        } catch (Exception e) {
            throw new SsyxException(ResultCodeEnum.FETCH_ACCESSTOKEN_FAILD);
        }
        //3 请求微信接口服务，返回两个值 session_key 和 openid
        //// openId是你微信唯一标识
        JSONObject jsonObject = JSONObject.parseObject(result);
//        String session_key = jsonObject.getString("session_key");
        String openid = jsonObject.getString("openid");
        //TODO 4 添加微信用户信息到数据库里面  我怎么记得openid是有时限的
        User user = this.getUserByOpenId(openid);
        if (user == null) {
            user = new User();
            user.setOpenId(openid);
            user.setNickName(openid);
            user.setPhotoUrl("");
            user.setUserType(UserType.USER.getCode());
            // 0代表为新用户
            user.setIsNew(0);
            this.save(user);
        }
        //5 根据userId查询提货点和团长信息
        ////提货点  user表  user_delivery表
        ////团长    leader表
        LeaderAddressVo leaderAddressVo =
                this.getLeaderAddressByUserId(user.getId());
        // 使用JWT生成token
        // 用户信息登录放到redis当中,超时时间为30分钟
        //6 使用JWT工具根据userId和userName生成token字符串
        String token = JwtHelper.createToken(user.getId(), user.getNickName());
        //7 获取当前登录用户信息，放到Redis里面，设置有效时间
        UserLoginVo userLoginVo = this.getUserLoginVo(user.getId());
        // TODO 为什么这个地方要把信息存入redis当中,而且这个时长也并不合理
        redisTemplate.opsForValue()
                .set(RedisConst.USER_LOGIN_KEY_PREFIX + user.getId(),
                        userLoginVo,
                        RedisConst.USERKEY_TIMEOUT,
                        TimeUnit.DAYS);
        //8 需要数据封装到map返回
        HashMap<String, Object> map = new HashMap<>(3);
        map.put("user", user);
        map.put("token", token);
        map.put("leaderAddressVo", leaderAddressVo);
        return Result.ok(map);
    }

    /**
     * 通过用户id展示地址
     *
     * @param userId 用户的id
     * @return 地址信息
     */
    @Override
    public LeaderAddressVo showAddress(Long userId) {
        Leader leader = baseMapper.showAddress(userId);
        LeaderAddressVo leaderAddressVo = LeaderConverterMapper.INSTANCE.converterLeaderToLeaderVO(leader);
        return leaderAddressVo;
    }
}
