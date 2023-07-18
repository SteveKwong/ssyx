package com.atguigu.ssyx.product.service.impl;

import com.atguigu.ssyx.model.product.SkuAttrValue;
import com.atguigu.ssyx.model.product.SkuPoster;
import com.atguigu.ssyx.product.mapper.SkuPosterMapper;
import com.atguigu.ssyx.product.service.SkuPosterService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 商品海报表 服务实现类
 * </p>
 *
 * @author qkuang
 * @since 2023-07-12
 */
@Service
public class SkuPosterServiceImpl extends ServiceImpl<SkuPosterMapper, SkuPoster> implements SkuPosterService {
    /**
     * 获取skuinfo信息的海报集合
     *
     * @param id sku的id
     * @return 海报的集合
     */
    @Override
    public List<SkuPoster> selectList(Long id) {
        LambdaQueryWrapper<SkuPoster> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SkuPoster::getSkuId, id);
        return baseMapper.selectList(queryWrapper);
    }
}
