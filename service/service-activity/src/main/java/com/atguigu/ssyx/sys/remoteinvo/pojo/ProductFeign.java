package com.atguigu.ssyx.sys.remoteinvo.pojo;

import com.atguigu.ssyx.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author kuanggong
 * @date 2023/7/24 11:33
 * @project_name atguigu-ssyx-parent
 */
@FeignClient(name = "service-product")
@Component
public interface ProductFeign {
    /**
     * 通过活动的id
     *
     * @param skuIds 商品的id列表集合
     * @return 获取到的集合
     */
    @GetMapping(value = "/sys/sku-info/getSkuInfoByActivityId/{skuId}")
    Result<Object> getSkuInfoByActivityId(@PathVariable("skuId") List<Long> skuIds);
}
