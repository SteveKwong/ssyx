package com.atguigu.ssyx.product.controller;


import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.product.service.CategoryService;
import com.atguigu.ssyx.vo.product.CategoryVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 商品三级分类 前端控制器
 * </p>
 *
 * @author qkuang
 * @since 2023-07-12
 */
@RestController
@RequestMapping("/sys/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 通过分组id获取属性列表
     *
     * @return 属性列表
     */
    @ApiOperation("添加商品的SKU信息")
    @GetMapping("findallcategorylist")
    public Result<List<CategoryVo>> findAllCategoryList() {
        List<CategoryVo> allCategoryList = categoryService.findAllCategoryList();
        return Result.ok(allCategoryList);
    }

}

