package com.atguigu.ssyx.product.controller;


import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.product.Attr;
import com.atguigu.ssyx.product.service.AttrService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 商品属性 前端控制器
 * </p>
 *
 * @author qkuang
 * @since 2023-07-12
 */
@RequestMapping("admin/sys/attr")
@Api(tags = "属性接口")
@RestController
@CrossOrigin
@RequiredArgsConstructor
public class AttrController {

    @Autowired
    private AttrService attrService;

    /**
     * 通过分组id获取属性列表
     *
     * @return 属性列表
     */
    @ApiOperation("根据组名id获取属性列表(一对多)")
    @GetMapping("{groupid}")
    public Result<List<Attr>> getGroupAttrs(@PathVariable("groupid") Long groupId) {
        return Result.ok(attrService.getGroupAttrs(groupId));
    }


}

