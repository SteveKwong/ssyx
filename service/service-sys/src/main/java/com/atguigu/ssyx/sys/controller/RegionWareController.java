package com.atguigu.ssyx.sys.controller;


import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.sys.RegionWare;
import com.atguigu.ssyx.sys.service.IRegionWareService;
import com.atguigu.ssyx.vo.sys.RegionWareQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 城市仓库关联表 前端控制器
 * </p>
 *
 * @author qkuang
 * @since 2023-07-11
 */
@RestController
@RequestMapping("/1/region-ware")
@CrossOrigin
public class RegionWareController  {

    @Autowired
    private IRegionWareService regionWareService;

    @GetMapping("/query/allregionware/{limit}/{size}")
    public Result queryAllRegionWare(@PathVariable("limit") Long pageStare,
                                     @PathVariable("size") Long pageSize,
                                     RegionWareQueryVo regionWareQueryVo
                                     ){
        Page<RegionWare> pageParam = new Page<>(pageStare, pageSize);
        IPage<RegionWare> roles = regionWareService.queryAllRegionWare(pageParam, regionWareQueryVo);
        return Result.ok(roles);
    }
}

