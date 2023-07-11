package com.atguigu.ssyx.sys.service;

import com.atguigu.ssyx.model.sys.RegionWare;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 城市仓库关联表 服务类
 * </p>
 *
 * @author qkuang
 * @since 2023-07-11
 */
public interface IRegionWareService extends IService<RegionWare> {
    /**
     * 新增城市仓库
     * @param regionWare 城市仓库
     */
    void saveRegionWare(RegionWare regionWare);
}
