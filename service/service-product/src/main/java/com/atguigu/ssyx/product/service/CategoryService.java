package com.atguigu.ssyx.product.service;

import com.atguigu.ssyx.model.product.Category;
import com.atguigu.ssyx.vo.product.CategoryVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 商品三级分类 服务类
 * </p>
 *
 * @author qkuang
 * @since 2023-07-12
 */
public interface CategoryService extends IService<Category> {
    /**
     * 查询分类列表
     *
     * @return 商品分类集合
     */
    List<CategoryVo> findAllCategoryList();
}
