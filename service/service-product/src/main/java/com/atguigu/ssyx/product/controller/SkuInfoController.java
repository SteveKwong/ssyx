package com.atguigu.ssyx.product.controller;


import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.product.SkuInfo;
import com.atguigu.ssyx.product.remoteinvo.pojo.SkuInfoVO;
import com.atguigu.ssyx.product.service.SkuInfoService;
import com.atguigu.ssyx.vo.product.SkuInfoQueryVo;
import com.atguigu.ssyx.vo.product.SkuInfoVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * sku信息 前端控制器
 * </p>
 *
 * @author qkuang
 * @since 2023-07-12
 */
@RestController
@RequestMapping("/sys/sku-info")
public class SkuInfoController {
    /**
     * sku信息
     */
    @Autowired
    private SkuInfoService skuInfoService;

    /**
     * 通过分组id获取属性列表
     *
     * @return 属性列表
     */
    @ApiOperation("根据组名id获取属性列表(一对多)")
    @GetMapping("{page}/{limit}")
    public Result<IPage<SkuInfo>> getSkuInfos(@PathVariable("page") Long start,
                                              @PathVariable("limit") Long limit,
                                              SkuInfoQueryVo skuInfoQueryVo
    ) {
        Page<SkuInfo> page = new Page<>(start, limit);
        IPage<SkuInfo> skuInfos = skuInfoService.getSkuInfos(page, skuInfoQueryVo);
        return Result.ok(skuInfos);
    }

    /**
     * 通过分组id获取属性列表
     *
     * @return 属性列表
     */
    @ApiOperation("添加商品的SKU信息")
    @GetMapping("save")
    public Result save(@RequestBody SkuInfoVo skuInfoVo) {
        skuInfoService.saveSkuInfoVo(skuInfoVo);
        return Result.ok(null);
    }

    /**
     * 通过分组id获取属性列表
     *
     * @return 属性列表
     */
    @ApiOperation("添加商品的SKU信息")
    @GetMapping("getskuinfovo/{id}")
    public Result<SkuInfoVo> getskuInfoVo(@PathVariable Long id) {
        SkuInfoVo skuInfoVo = skuInfoService.getSkuInfoVo(id);
        return Result.ok(skuInfoVo);
    }

    /**
     * 修改上架和下架的状态
     *
     * @return 属性列表
     */
    @ApiOperation("修改上架和下架的状态")
    @GetMapping("changepubishstatus/{id}/{publishstatus}")
    public Result<String> changePublishStatus(@PathVariable("id") Long id, @PathVariable("publishstatus") Long publishStatus) {
        skuInfoService.changePublishStatus(id, publishStatus);
        return Result.ok("修改上架下架状态成功");
    }

    /**
     * 修改审核的状态
     *
     * @return 属性列表
     */
    @ApiOperation("修改审核状态")
    @GetMapping("changecheckstatus/{id}/{checkstatus}")
    public Result<String> changeCheckStatus(@PathVariable Long id, @PathVariable("checkstatus") Long checkStatus) {
        skuInfoService.changeCheckStatus(id, checkStatus);
        return Result.ok("修改审核状态成功");
    }

    /**
     * 修改是否新人专享
     *
     * @return 属性列表
     */
    @ApiOperation("修改是否新人专享")
    @GetMapping("changecheckstatus/{id}/{isnewperson}")
    public Result<String> changeNewPersonStatus(@PathVariable Long id, @PathVariable("isnewperson") Long isNewPerson) {
        skuInfoService.changeNewPersonStatus(id, isNewPerson);
        return Result.ok("新ユーザーの割引券使用できる");
    }

    /**
     * 商品上架
     *
     * @return 属性列表
     */
    @ApiOperation("商品上架")
    @GetMapping("goodsup/{id}")
    public Result<String> goodsUp(@PathVariable("id") Long id) {
        skuInfoService.goodsUp(id);
        return Result.ok("商品上架成功");
    }

    /**
     * 商品下架
     *
     * @return 属性列表
     */
    @ApiOperation("商品下架")
    @GetMapping("goodsdown/{id}")
    public Result<String> goodsDown(@PathVariable("id") Long id) {
        skuInfoService.goodsDown(id);
        return Result.ok("商品下架成功");
    }

    /**
     * 获取sku的集合信息
     *
     * @return 属性列表
     */
    @ApiOperation("获取sku的集合信息")
    @GetMapping("getskuinfo/{spuid}")
    public Result<SkuInfoVO> getSkuInfo(@PathVariable("spuid") Long id) {
        SkuInfoVO skuInfoVO = skuInfoService.getSkuInfo(id);
        return Result.ok(skuInfoVO);
    }
}

