package com.atguigu.ssyx.product.service;

import com.atguigu.ssyx.model.product.SkuAttrValue;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * spu属性值 服务类
 * </p>
 *
 * @author qkuang
 * @since 2023-07-12
 */
public interface SkuAttrValueService extends IService<SkuAttrValue> {
    /**
     * 获取list集合
     * @param id vo的id
     * @return 获取到的集合
     */
    List<SkuAttrValue> selectList(Long id);
}
