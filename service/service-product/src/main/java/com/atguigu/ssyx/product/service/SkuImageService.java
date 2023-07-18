package com.atguigu.ssyx.product.service;

import com.atguigu.ssyx.model.product.SkuImage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 商品图片 服务类
 * </p>
 *
 * @author qkuang
 * @since 2023-07-12
 */
public interface SkuImageService extends IService<SkuImage> {
    /**
     * 获取照片的集合
     * @param id sku的id
     * @return 照片的集合
     */
    List<SkuImage> selectList(Long id);
}
