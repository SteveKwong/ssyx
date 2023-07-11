package com.atguigu.ssyx.sys.controller;


import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.sys.RegionWare;
import com.atguigu.ssyx.sys.service.IRegionWareService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/sys/region-ware")
@CrossOrigin
@RequiredArgsConstructor
public class RegionWareController {
    /**
     * 地区
     */
    private final IRegionWareService regionWareService;


    /**
     * 获取登录信息
     *
     * @return 用户头像及姓名
     */
    @ApiOperation("获取登录信息")
    @GetMapping("save")
    public Result insertRegionWare(@RequestBody RegionWare regionWare) {
        regionWareService.saveRegionWare(regionWare);
        return Result.ok(null);
    }
}

