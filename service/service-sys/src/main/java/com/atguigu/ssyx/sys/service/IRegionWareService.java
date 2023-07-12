package com.atguigu.ssyx.sys.service;

import com.atguigu.ssyx.model.sys.RegionWare;
import com.atguigu.ssyx.vo.sys.RegionWareQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
     * 查询全部的地域
     * @param pageParam 查询参数
     * @param regionWareQueryVo 返回对象结果
     * @return
     */
    IPage<RegionWare> queryAllRegionWare(Page<RegionWare> pageParam, RegionWareQueryVo regionWareQueryVo);
}
