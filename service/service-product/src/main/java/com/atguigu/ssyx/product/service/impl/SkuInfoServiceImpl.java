package com.atguigu.ssyx.product.service.impl;

import com.atguigu.ssyx.common.exception.SsyxException;
import com.atguigu.ssyx.common.result.ResultCodeEnum;
import com.atguigu.ssyx.model.product.SkuAttrValue;
import com.atguigu.ssyx.model.product.SkuImage;
import com.atguigu.ssyx.model.product.SkuInfo;
import com.atguigu.ssyx.model.product.SkuPoster;
import com.atguigu.ssyx.product.mapper.SkuInfoMapper;
import com.atguigu.ssyx.product.service.SkuAttrValueService;
import com.atguigu.ssyx.product.service.SkuImageService;
import com.atguigu.ssyx.product.service.SkuInfoService;
import com.atguigu.ssyx.product.service.SkuPosterService;
import com.atguigu.ssyx.vo.product.SkuInfoQueryVo;
import com.atguigu.ssyx.vo.product.SkuInfoVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.jsonwebtoken.lang.Assert;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static com.atguigu.ssyx.product.config.RabbitMQConfig.*;
import static com.atguigu.ssyx.product.enums.GoodsStatus.GOODS_DOWN;
import static com.atguigu.ssyx.product.enums.GoodsStatus.GOODS_UP;

/**
 * <p>
 * sku信息 服务实现类
 * </p>
 *
 * @author qkuang
 * @since 2023-07-12
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoMapper, SkuInfo> implements SkuInfoService {
    /**
     * sku海报的服务
     */
    private final SkuPosterService skuPosterService;
    /**
     * sku的照片的服务
     */
    private final SkuImageService skuImageService;
    /**
     * sku的属性的服务
     */
    private final SkuAttrValueService skuAttrValueService;
    /**
     * 消息队列
     */
    private final RabbitTemplate rabbitTemplate;

    private CorrelationData correlationData;

    /**
     * 获取商品信息对象
     *
     * @param page           page对象
     * @param skuInfoQueryVo 查询条件封装
     * @return 获取商品的信息
     */
    @Override
    public IPage<SkuInfo> getSkuInfos(Page<SkuInfo> page, SkuInfoQueryVo skuInfoQueryVo) {
        String skuType = skuInfoQueryVo.getSkuType();
        Long categoryId = skuInfoQueryVo.getCategoryId();
        String keyword = skuInfoQueryVo.getKeyword();
        LambdaQueryWrapper<SkuInfo> queryWrapper = new LambdaQueryWrapper<>();

        if (StringUtils.isNotEmpty(skuType)) {
            queryWrapper.like(SkuInfo::getSkuType, skuType);
        }
        if (categoryId != null) {
            queryWrapper.eq(SkuInfo::getCategoryId, categoryId);
        }
        if (StringUtils.isNotEmpty(keyword)) {
            queryWrapper.like(SkuInfo::getSkuName, keyword);
        }
        return baseMapper.selectPage(page, queryWrapper);
    }

    /**
     * 保存skuInfo的实体类
     *
     * @param skuInfoVo skuInfo的实体类
     */
    @Override
    public void saveSkuInfoVo(SkuInfoVo skuInfoVo) {
        // sku的基本信息
        SkuInfo skuInfo = new SkuInfo();
        BeanUtils.copyProperties(skuInfoVo, skuInfo);
        baseMapper.insert(skuInfo);
        // sku的图片信息
        List<SkuImage> skuImagesList = skuInfoVo.getSkuImagesList();
        if (!CollectionUtils.isEmpty(skuImagesList)) {
            skuImagesList.forEach(skuImage -> {
                skuImage.setSkuId(skuInfo.getId());
            });
            skuImageService.saveBatch(skuImagesList);
        }

        // sku的海报信息
        List<SkuPoster> skuPosterList = skuInfoVo.getSkuPosterList();
        if (!CollectionUtils.isEmpty(skuPosterList)) {
            skuPosterList.forEach(skuPoster -> {
                skuPoster.setSkuId(skuInfo.getId());
            });
            skuPosterService.saveBatch(skuPosterList);
        }
        // 商品的属性信息
        List<SkuAttrValue> skuAttrValueList = skuInfoVo.getSkuAttrValueList();
        if (!CollectionUtils.isEmpty(skuAttrValueList)) {
            skuAttrValueList.forEach(skuAttrValue -> {
                skuAttrValue.setSkuId(skuInfo.getId());
            });
            skuAttrValueService.saveBatch(skuAttrValueList);
        }
    }

    /**
     * 获取skuinfoVO的信息
     *
     * @param id skuinfovo的id
     * @return
     */
    @Override
    public SkuInfoVo getSkuInfoVo(Long id) {
        SkuInfoVo skuInfoVo = new SkuInfoVo();
        SkuInfo skuInfo = baseMapper.selectById(id);
        BeanUtils.copyProperties(skuInfo, skuInfoVo);
        // 获取sku的属性信息
        List<SkuAttrValue> skuAttrValueList = skuAttrValueService.selectList(id);
        skuInfoVo.setSkuAttrValueList(skuAttrValueList);
        // 获取sku的海报信息
        List<SkuPoster> skuPosterList = skuPosterService.selectList(id);
        skuInfoVo.setSkuPosterList(skuPosterList);
        // 获取sku的图片信息
        List<SkuImage> skuImageList = skuImageService.selectList(id);
        skuInfoVo.setSkuImagesList(skuImageList);
        return skuInfoVo;
    }

    /**
     * 修改上架下架的状态
     *
     * @param id            skuInfo的id
     * @param publishStatus 审核的状态
     */
    @Override
    public void changePublishStatus(Long id, Long publishStatus) {
        // 上架
        if (1 == publishStatus) {
            LambdaUpdateWrapper<SkuInfo> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(SkuInfo::getId, id);
            updateWrapper.set(SkuInfo::getPublishStatus, publishStatus);
            baseMapper.update(null, updateWrapper);
            // TODO 整合NACOS  ROCKETMQ 和 ELASTICSEARCH
        } else {
            // 下架
            LambdaUpdateWrapper<SkuInfo> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(SkuInfo::getId, id);
            updateWrapper.set(SkuInfo::getPublishStatus, publishStatus);
            baseMapper.update(null, updateWrapper);

        }

    }

    /**
     * 修改审核状态
     *
     * @param id          skuInfo的id
     * @param checkStatus 审核状态
     */
    @Override
    public void changeCheckStatus(Long id, Long checkStatus) {
        LambdaUpdateWrapper<SkuInfo> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SkuInfo::getId, id);
        updateWrapper.set(SkuInfo::getCheckStatus, checkStatus);
        baseMapper.update(null, updateWrapper);
    }

    /**
     * 修改新人状态
     *
     * @param id          　skuInfo的id
     * @param isNewPerson 是否能够使用新人优惠
     */
    @Override
    public void changeNewPersonStatus(Long id, Long isNewPerson) {
        LambdaUpdateWrapper<SkuInfo> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SkuInfo::getId, id);
        updateWrapper.set(SkuInfo::getIsNewPerson, isNewPerson);
        baseMapper.update(null, updateWrapper);
    }

    /**
     * 商品上架索引库
     *
     * @param id 商品的id
     */
    @Override
    public void goodsUp(Long id) {
        // 商品的上架中直接商品状态改成上架,然后同步到索引库当中
        // 在这个方法中,需要先发送MQ到索引库服务 如果回调为真 则修改数据库状态为上架
        // 3.添加callback
        correlationData.getFuture().addCallback(
                result -> {
                    Assert.notNull(result);
                    if (result.isAck()) {
                        // 3.1.ack，消息成功
                        log.debug("消息发送成功, ID:{}", correlationData.getId());
                        // TODO 如何进行等待ACK的回执
                        LambdaUpdateWrapper<SkuInfo> updateWrapper = new LambdaUpdateWrapper<>();
                        updateWrapper.eq(SkuInfo::getId, id);
                        updateWrapper.set(SkuInfo::getPublishStatus, GOODS_UP);
                        baseMapper.update(null, updateWrapper);
                    } else {
                        // 3.2.nack，消息失败
                        log.error("消息发送失败, ID:{}, 原因{}", correlationData.getId(), result.getReason());
                        throw new SsyxException(ResultCodeEnum.FAIL);
                    }
                },
                ex -> log.error("消息发送异常, ID:{}, 原因{}", correlationData.getId(), ex.getMessage())
        );
        rabbitTemplate.convertAndSend(GOODS_UP_EXCHANGE, AD_UPDATE_QUEUE, id, correlationData);
    }

    /**
     * 商品下架索引库
     *
     * @param id 商品的id
     */
    @Override
    public void goodsDown(Long id) {
        // 商品的上架中直接商品状态改成上架,然后同步到索引库当中
        // 在这个方法中,需要先发送MQ到索引库服务 如果回调为真 则修改数据库状态为上架
        // 3.添加callback
        correlationData.getFuture().addCallback(
                result -> {
                    Assert.notNull(result);
                    if (result.isAck()) {
                        // 3.1.ack，消息成功
                        log.debug("消息发送成功, ID:{}", correlationData.getId());
                        // TODO 如何进行等待ACK的回执
                        LambdaUpdateWrapper<SkuInfo> updateWrapper = new LambdaUpdateWrapper<>();
                        updateWrapper.eq(SkuInfo::getId, id);
                        updateWrapper.set(SkuInfo::getPublishStatus, GOODS_DOWN);
                        this.baseMapper.update(null, updateWrapper);
                    } else {
                        // 3.2.nack，消息失败
                        log.error("消息发送失败, ID:{}, 原因{}", correlationData.getId(), result.getReason());
                        throw new SsyxException(ResultCodeEnum.FAIL);
                    }
                },
                ex -> log.error("消息发送异常, ID:{}, 原因{}", correlationData.getId(), ex.getMessage())
        );
        rabbitTemplate.convertAndSend(GOODS_DOWN_EXCHANGE, AD_UPDATE_QUEUE, id, correlationData);
    }
}
