package com.atguigu.ssyx.product.service;

import com.atguigu.ssyx.model.product.SkuPoster;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 商品海报表 服务类
 * </p>
 *
 * @author qkuang
 * @since 2023-07-12
 */
public interface SkuPosterService extends IService<SkuPoster> {
    /**
     * 获取海报集合
     * @param id sku的id
     * @return 海报的集合照片
     */
    List<SkuPoster> selectList(Long id);
}
