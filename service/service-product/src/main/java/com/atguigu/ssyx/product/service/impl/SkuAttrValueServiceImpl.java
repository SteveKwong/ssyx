package com.atguigu.ssyx.product.service.impl;

import com.atguigu.ssyx.model.product.SkuAttrValue;
import com.atguigu.ssyx.product.mapper.SkuAttrValueMapper;
import com.atguigu.ssyx.product.service.SkuAttrValueService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * spu属性值 服务实现类
 * </p>
 *
 * @author qkuang
 * @since 2023-07-12
 */
@Service
public class SkuAttrValueServiceImpl extends ServiceImpl<SkuAttrValueMapper, SkuAttrValue> implements SkuAttrValueService {

    /**
     * @param id vo的id
     * @return 属性的集合
     */
    @Override
    public List<SkuAttrValue> selectList(Long id) {
        LambdaQueryWrapper<SkuAttrValue> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SkuAttrValue::getSkuId, id);
        return baseMapper.selectList(queryWrapper);
    }
}
