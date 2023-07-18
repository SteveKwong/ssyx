package com.atguigu.ssyx.search.service;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;

/**
 * @author kuanggong
 * @date 2023/7/18 11:49
 * @project_name atguigu-ssyx-parent
 */

public interface ProductDealService {
    /**
     * 上架商品信息
     * @param message 消息
     * @param channel 队列
     * @throws Exception 可能发生的异常
     */
    void importDataBySpuId(Message message, Channel channel)throws Exception ;

    /**
     * 下架商品信息
     * @param message 消息
     * @param channel 队列
     * @throws Exception 可能发生的异常
     */
    void delDataBySpuId(Message message, Channel channel)throws Exception ;
}
