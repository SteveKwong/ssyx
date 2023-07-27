package com.atguigu.ssyx.search.controller;

import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.search.service.impl.ProductDealServiceImpl;
import com.atguigu.ssyx.vo.search.SkuEsQueryVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author kuanggong
 * @date 2023/7/27 15:30
 * @project_name atguigu-ssyx-parent
 */
@RestController
@RequestMapping("/search/category")
public class SearchController {

    @Resource
    private ProductDealServiceImpl productDealService;

    @ApiOperation("根据分类查询商品信息")
    @GetMapping("/findskubycategory/{start}/{limit}")
    public Result findSkuByCategory(@PathVariable Long start,
                                    @PathVariable Long limit,
                                    SkuEsQueryVo skuEsQueryVo
    ) {
        productDealService.findSkuByCategory(start,limit,skuEsQueryVo);
        return Result.ok("");
    }
}
