package com.atguigu.ssyx.product.mapper;

import com.atguigu.ssyx.model.product.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 商品三级分类 Mapper 接口
 * </p>
 *
 * @author qkuang
 * @since 2023-07-12
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

}
