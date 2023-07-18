package com.atguigu.ssyx.sys.service.impl;


import com.atguigu.ssyx.model.sys.Region;
import com.atguigu.ssyx.sys.mapper.RegionMapper;
import com.atguigu.ssyx.sys.service.IRegionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<String> getRegion(String regionName) {
        return null;
    }

    @Override
    public List<String> getWareByRegion(String regionCode) {
        return null;
    }
}
