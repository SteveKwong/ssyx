package com.atguigu.ssyx.product.mapper;

import com.atguigu.ssyx.model.product.SkuStockHistory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * sku的库存历史记录 Mapper 接口
 * </p>
 *
 * @author qkuang
 * @since 2023-07-12
 */
@Mapper
public interface SkuStockHistoryMapper extends BaseMapper<SkuStockHistory> {

}
