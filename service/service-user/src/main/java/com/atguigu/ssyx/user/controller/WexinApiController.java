package com.atguigu.ssyx.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.JwtHelper;
import com.atguigu.ssyx.common.constant.RedisConst;
import com.atguigu.ssyx.common.exception.SsyxException;
import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.common.result.ResultCodeEnum;
import com.atguigu.ssyx.enums.UserType;
import com.atguigu.ssyx.enums.user.User;
import com.atguigu.ssyx.user.service.UserService;
import com.atguigu.ssyx.user.utils.ConstantPropertiesUtil;
import com.atguigu.ssyx.user.utils.HttpClientUtils;
import com.atguigu.ssyx.vo.user.LeaderAddressVo;
import com.atguigu.ssyx.vo.user.UserLoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author kuanggong
 * @date 2023/7/26 09:29
 * @project_name recharge2
 */
@RestController
@RequestMapping("/api/user/weixin")
public class WexinApiController {

    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/user/weixin/wxLogin/{code}")
    public Result wxLogin(@PathVariable String code) {
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
          User user = userService.getUserByOpenId(openid);
        if (user == null) {
            user = new User();
            user.setOpenId(openid);
            user.setNickName(openid);
            user.setPhotoUrl("");
            user.setUserType(UserType.USER);
            // 0代表为新用户
            user.setIsNew(0);
            userService.save(user);
        }
        //5 根据userId查询提货点和团长信息
        ////提货点  user表  user_delivery表
        ////团长    leader表
        LeaderAddressVo leaderAddressVo =
                userService.getLeaderAddressByUserId(user.getId());
        // 使用JWT生成token
        // 用户信息登录放到redis当中,超时时间为30分钟
        //6 使用JWT工具根据userId和userName生成token字符串
        String token = JwtHelper.createToken(user.getId(), user.getNickName());
        //7 获取当前登录用户信息，放到Redis里面，设置有效时间
        UserLoginVo userLoginVo = userService.getUserLoginVo(user.getId());
        // TODO 为什么这个地方要把信息存入redis当中,而且这个时长也并不合理
        redisTemplate.opsForValue()
                .set(RedisConst.USER_LOGIN_KEY_PREFIX+user.getId(),
                        userLoginVo,
                        RedisConst.USERKEY_TIMEOUT,
                        TimeUnit.DAYS);
        //8 需要数据封装到map返回
        HashMap<String,Object> map = new HashMap<>();
        map.put("user",user);
        map.put("token",token);
        map.put("leaderAddressVo",leaderAddressVo);
        return Result.ok(map);
    }


}
