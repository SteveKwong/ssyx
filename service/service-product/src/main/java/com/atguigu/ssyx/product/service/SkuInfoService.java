package com.atguigu.ssyx.product.service;

import com.atguigu.ssyx.model.product.SkuInfo;
import com.atguigu.ssyx.product.remoteinvo.pojo.SkuInfoVO;
import com.atguigu.ssyx.vo.product.SkuInfoQueryVo;
import com.atguigu.ssyx.vo.product.SkuInfoVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * sku信息 服务类
 * </p>
 *
 * @author qkuang
 * @since 2023-07-12
 */
public interface SkuInfoService extends IService<SkuInfo> {
    /**
     * 获取商品言论
     *
     * @param page           page对象
     * @param skuInfoQueryVo 查询条件封装
     * @return 查询结果
     */
    IPage<SkuInfo> getSkuInfos(Page<SkuInfo> page, SkuInfoQueryVo skuInfoQueryVo);

    /**
     * 保存sku的info信息
     *
     * @param skuInfoVo skuInfo的实体类
     */
    void saveSkuInfoVo(SkuInfoVo skuInfoVo);

    /**
     * 获取skuInfoVO的信息
     *
     * @param id skuinfovo的id
     * @return skuinfo的信息
     */
    SkuInfoVo getSkuInfoVo(Long id);

    /**
     * 修改上架下架的状态
     *
     * @param id     skuInfo的id
     * @param status
     */
    void changePublishStatus(Long id, Long publishStatus);

    /**
     * 修改审核状态
     *
     * @param id          skuInfo的id
     * @param checkStatus 审核状态
     */
    void changeCheckStatus(Long id, Long checkStatus);

    /**
     * @param id          　skuInfo的id
     * @param isNewPerson 是否能够使用新人优惠
     */
    void changeNewPersonStatus(Long id, Long isNewPerson);

    /**
     * 商品上架索引库
     *
     * @param id 商品的id
     */
    void goodsUp(Long id);

    /**
     * 商品下架索引库
     *
     * @param id 商品的id
     */
    void goodsDown(Long id);

    /**
     * 获取sku的信息
     * @param id skuId
     * @return sku的信息
     */
    SkuInfoVO getSkuInfo(Long id);
}
