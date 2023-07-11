package com.atguigu.ssyx.sys.service.impl;

import com.atguigu.ssyx.model.sys.Region;
import com.atguigu.ssyx.model.sys.Ware;
import com.atguigu.ssyx.sys.mapper.RegionMapper;
import com.atguigu.ssyx.sys.mapper.WareMapper;
import com.atguigu.ssyx.sys.service.IRegionService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 地区表 服务实现类
 * </p>
 *
 * @author qkuang
 * @since 2023-07-11
 */
@Service
public class RegionServiceImpl extends ServiceImpl<RegionMapper, Region> implements IRegionService {
    /**
     * 仓库的DAO
     */
    @Autowired
    private WareMapper wareMapper;

    /**
     * 模糊查询获取全部的地区列表
     *
     * @param regionName 名称
     * @return 返回结果
     */
    @Override
    public List<String> getRegion(String regionName) {
        LambdaQueryWrapper<Region> wrapper = null;
        if (!StringUtils.isEmpty(regionName)) {
            wrapper = new LambdaQueryWrapper<>();
            wrapper.like(Region::getName, regionName);
        }
        List<Region> regions = baseMapper.selectList(wrapper);
        return regions.stream().map(Region::getName).collect(Collectors.toList());
    }

    /**
     * 通过地区code获取当地仓库列表
     *
     * @param regionCode 地区code
     * @return 当地仓库列表
     */
    @Override
    public List<String> getWareByRegion(String regionCode) {
        if (!regionCode.isEmpty()) {
            return wareMapper.selectWareListByRegionCode(regionCode).stream()
                    .map(Ware::getName)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}
