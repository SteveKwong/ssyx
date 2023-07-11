package com.atguigu.ssyx.sys.controller;


import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.sys.service.IRegionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 地区表 前端控制器
 * </p>
 *
 * @author qkuang
 * @since 2023-07-11
 */
@RestController
@RequestMapping("admin/sys/region")
@Api(tags = "地区接口")
@CrossOrigin
public class RegionController {
    @Autowired
    private IRegionService regionService;

    /**
     * 模糊查询全部的地区列表
     *
     * @return 用户头像及姓名
     */
    @ApiOperation("模糊查询全部的地区列表")
    @GetMapping("getRegion")
    public Result<List<String>> getRegion(@RequestParam("name") String regionName) {
        List<String> regions = regionService.getRegion(regionName);
        return Result.ok(regions);
    }

    /**
     * 根据地区选择该地区的仓库
     *
     * @return 用户头像及姓名
     */
    @ApiOperation("模糊查询全部的地区列表")
    @GetMapping("getwarebyregion/{regioncode}")
    public Result<List<String>> getWareByRegion(@PathVariable("regioncode") String regionCode) {
        List<String> wares = regionService.getWareByRegion(regionCode);
        return Result.ok(wares);
    }
}

