package com.atguigu.ssyx.sys.service;


import com.atguigu.ssyx.model.sys.Region;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 地区表 服务类
 * </p>
 *
 * @author qkuang
 * @since 2023-07-11
 */
public interface IRegionService extends IService<Region> {
    /**
     * 模糊查询获取全部的地区列表
     *
     * @param regionName 名称
     * @return 集合结果
     */
    List<String> getRegion(String regionName);

    /**
     * 通过地区id获取region的列表
     *
     * @param regionCode 地区code
     * @return 仓库列表
     */
    List<String> getWareByRegion(String regionCode);
}
