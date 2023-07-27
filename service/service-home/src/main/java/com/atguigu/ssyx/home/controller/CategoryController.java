package com.atguigu.ssyx.home.controller;

import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.home.remote.ProductService;
import com.atguigu.ssyx.vo.product.CategoryVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

/**
 * @author kuanggong
 * @date 2023/7/27 14:40
 * @project_name atguigu-ssyx-parent
 */
@RestController
@RequestMapping("/wx/category")
public class CategoryController {

    @Autowired
    private ProductService productService;

    @ApiOperation("展示首页数据")
    @GetMapping("/findallcategorylist")
    public Result<List<CategoryVo>> findAllCategoryList() {
        Result<List<CategoryVo>> categoryList = productService.findAllCategoryList();
        if (!categoryList.getData().isEmpty()) {
            return Result.ok(categoryList.getData());
        }
        return Result.ok(Collections.emptyList());
    }


}
