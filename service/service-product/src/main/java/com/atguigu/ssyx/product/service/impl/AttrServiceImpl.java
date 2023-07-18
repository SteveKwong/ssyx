package com.atguigu.ssyx.product.service.impl;

import com.atguigu.ssyx.model.product.Attr;
import com.atguigu.ssyx.product.mapper.AttrGroupMapper;
import com.atguigu.ssyx.product.mapper.AttrMapper;
import com.atguigu.ssyx.product.service.AttrService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 商品属性 服务实现类
 * </p>
 *
 * @author qkuang
 * @since 2023-07-12
 */
@Service
public class AttrServiceImpl extends ServiceImpl<AttrMapper, Attr> implements AttrService {
    /**
     * 获取属性分组的DAO
     */
    @Autowired
    private AttrGroupMapper attrGroupMapper;



    /**
     * 通过分组ID获取属性列
     *
     * @param groupId 分组ID
     * @return 属性列表
     */
    @Override
    public List<Attr> getGroupAttrs(Long groupId) {
        // 连表查询
        LambdaQueryWrapper<Attr> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Attr::getAttrGroupId,groupId);
        return baseMapper.selectList(queryWrapper);
    }
}
