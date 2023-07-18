package com.atguigu.ssyx.product.service;

import com.atguigu.ssyx.model.product.Attr;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 商品属性 服务类
 * </p>
 *
 * @author qkuang
 * @since 2023-07-12
 */
@Service
public interface AttrService extends IService<Attr> {
    /**
     * 通过分组ID获取属性列
     * @param groupId 分组ID
     * @return 属性列表
     */
    List<Attr> getGroupAttrs(Long groupId);
}
