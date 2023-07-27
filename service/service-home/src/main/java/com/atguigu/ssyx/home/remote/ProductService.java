package com.atguigu.ssyx.home.remote;

import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.vo.product.CategoryVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author kuanggong
 * @date 2023/7/27 14:40
 * @project_name atguigu-ssyx-parent
 */
@FeignClient(name = "service-product")
@Component
public interface ProductService {
    /**
     * 对user进行远程调用,获取用户地址的查询
     *
     * @param userId 用户id
     * @return 查询地址的结果
     */
    @GetMapping(value = "/sys/user/getuserinfobyuserid/{userId}")
    Result<Object> getUserInfoByUserId(@PathVariable("userid") Long userId);
    /**
     * 查询分类列表
     *
     * @return 商品分类集合
     */
    @GetMapping("/sys/category/findallcategorylist")
    Result<List<CategoryVo>> findAllCategoryList();
}
