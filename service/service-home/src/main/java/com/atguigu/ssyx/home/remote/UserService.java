package com.atguigu.ssyx.home.remote;

import com.atguigu.ssyx.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


/**
 * @author kuanggong
 * @date 2023/7/18 17:06
 * @project_name atguigu-ssyx-parent
 */
@FeignClient(name = "service-user")
@Component
public interface UserService {
    /**
     * 对user进行远程调用,获取用户地址的查询
     *
     * @param userId 用户id
     * @return 查询地址的结果
     */
    @GetMapping(value = "/sys/user/getuserinfobyuserid/{userId}")
    Result<Object> getUserInfoByUserId(@PathVariable("userid") Long userId);
}
