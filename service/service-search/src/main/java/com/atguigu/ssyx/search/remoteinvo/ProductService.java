package com.atguigu.ssyx.search.remoteinvo;

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
    @GetMapping(value = "/sku/spu/{spuid}")
    public List<SkuInfoVO> findSkuListBySpuId(@PathVariable("spuid") String spuId);
}
