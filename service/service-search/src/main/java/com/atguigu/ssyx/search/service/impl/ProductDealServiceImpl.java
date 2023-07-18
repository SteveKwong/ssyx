package com.atguigu.ssyx.search.service.impl;

import com.atguigu.ssyx.search.service.ProductDealService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;

/**
 * @author kuanggong
 * @date 2023/7/18 11:51
 * @project_name atguigu-ssyx-parent
 */
@Service
@Slf4j
public class ProductDealServiceImpl implements ProductDealService {

    @Qualifier("esClient")
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * 上架商品信息
     * @param message 消息
     * @param channel 队列
     * @throws Exception 可能出现的异常
     */
    @Override
    public void importDataBySpuId(Message message, Channel channel) throws Exception {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            String spuId = Arrays.toString(message.getBody());
            System.out.println("处理业务逻辑" + spuId);
            channel.basicAck(deliveryTag, Boolean.TRUE);
        } catch (IOException e) {
            channel.basicNack(deliveryTag, Boolean.TRUE, Boolean.TRUE);
        }
    }


    /**
     * 下架商品信息
     * @param message 消息
     * @param channel 队列
     * @throws Exception 可能出现的异常
     */
    @Override
    public void delDataBySpuId(Message message, Channel channel) throws Exception {

    }
}
