package com.atguigu.ssyx.search.service.impl;

import com.atguigu.ssyx.search.config.RabbitMQConfig;
import com.atguigu.ssyx.search.service.ProductDealService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 此类用来监听商品的上下架
 *
 * @author kuanggong
 * @date 2023/7/14 11:53
 * @project_name atguigu-ssyx-parent
 */
@Component
@Slf4j
public class ProductListener {

    @Autowired
    private ProductDealService productDealService;

    @RabbitListener(queues = RabbitMQConfig.GOODS_UP_EXCHANGE)
    public void receive(Message message, Channel channel) throws Exception {
        log.info("上架索引库,接受到的消息为[{}]", message.getBody());
        //查询SKU的列表并导入到索引库
        productDealService.importDataBySpuId(message, channel);
    }

    @RabbitListener(queues = RabbitMQConfig.GOODS_DOWN_EXCHANGE)
    public void del(Message message, Channel channel) throws Exception {
        log.info("删除索引库,接受到的消息为[{}]", message.getBody());
        //查询SKU的列表并导入到索引库
        productDealService.delDataBySpuId(message, channel);
    }

}
