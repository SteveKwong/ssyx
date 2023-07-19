package com.atguigu.ssyx.search.remoteinvo;

import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.search.vo.SkuInfoVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


/**
 * @author kuanggong
 * @date 2023/7/18 17:06
 * @project_name atguigu-ssyx-parent
 */
@FeignClient(name = "service-product")
@Component
public interface ProductService {
    /**
     * 通过spu的id获取skuList的集合
     * @param spuId spu的Id
     * @return sku的信息集合
     */
    @GetMapping(value = "/sys/sku-info/getskus/{spuid}")
    public Result<List<SkuInfoVO>> findSkuListBySpuId(@PathVariable("spuid") String spuId);
}
