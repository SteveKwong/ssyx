package com.atguigu.ssyx.search.service.impl;

import com.atguigu.ssyx.search.remoteinvo.ProductService;
import com.atguigu.ssyx.search.vo.SkuInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author ASUS
 */
@Service
@EnableBinding(Sink.class)
@Slf4j
public class ProductDealServiceImplements {
    /**
     * 远程商品服务
     */
    @Autowired
    private ProductService productService;

    /**
     * 多线程
     */
    @Qualifier("myThreadPool")
    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;







    @StreamListener(Sink.INPUT)
    public void receiveMessage(String spuId) {
        threadPoolTaskExecutor.execute(() -> {

            if (!spuId.isEmpty()) {
                SkuInfoVO skuInfo = productService.findSkuListBySpuId(spuId).getData();
                Assert.notNull(skuInfo, "未查询到skuInfo相关的信息");
                // 使用es进行商品的上架
                if (!productStatusUp(skuInfo)) {
                    log.error("商品上架失败,id{}", spuId);
                }
            }
        });
    }

    /**
     * 商品上架
     *
     * @param skuInfo 商品信息
     * @return 上架结果
     */
    private boolean productStatusUp(SkuInfoVO skuInfo) {
//        https://www.bilibili.com/video/BV19M4y1q7Lt?t=682.4&p=50

        return false;
    }


}
