package com.atguigu.ssyx.search.service.impl;

import com.atguigu.ssyx.search.config.ElasticSearchConfig;
import com.atguigu.ssyx.search.remoteinvo.ProductService;
import com.atguigu.ssyx.search.vo.SkuInfoVO;
import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.atguigu.ssyx.search.config.RabbitMQConfig.*;

/**
 * @author kuanggong
 * @date 2023/7/18 11:51
 * @project_name atguigu-ssyx-parent
 */
@Service
@Slf4j
public class ProductDealServiceImpl {
    /**
     * 索引库客户端
     */
    @Qualifier("esClient")
    @Autowired
    private RestHighLevelClient client;
    /**
     * 商品远程服务
     */
    @Autowired
    private ProductService productService;
    /**
     * json序列化工具
     */
    @Autowired
    private Gson gson;
    /**
     * ssyx的索引列
     */
    public static final String PRODUCT_INDEX = "ssyx_product";

    /**
     * 上架商品信息
     *
     * @param message 消息
     * @param channel 队列
     * @throws Exception 可能出现的异常
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = SEARCH_ADD_QUEUE, durable = "true"),
            exchange = @Exchange(value = GOODS_UP_EXCHANGE),
            key = ""
    ))
    public void importDataBySpuId(String spuId, Message message, Channel channel) throws Exception {
        try {
            // TODO 商品上架
            //  FEIGN 远程获取商品信息
            if (!spuId.isEmpty()) {
                List<SkuInfoVO> skuInfos = productService.findSkuListBySpuId(spuId);
                // 使用es进行商品的上架
                boolean upResult = productStatusUp(skuInfos);
                // 如果商品上架失败,则重新投递到队列进行上架排序
                if (!upResult) {
                    channel.basicNack(message.getMessageProperties().getDeliveryTag(), Boolean.FALSE, Boolean.TRUE);
                }
            }
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), Boolean.FALSE);
        } catch (IOException e) {
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), Boolean.FALSE, Boolean.FALSE);
        }
    }

    /**
     * 进行商品的上架
     *
     * @param skuInfos 单个商品的sku的信息集合
     */
    private boolean productStatusUp(List<SkuInfoVO> skuInfos) throws IOException {
        if (CollectionUtils.isEmpty(skuInfos)) {
            return true;
        }
        //创建批量请求
        BulkRequest bulkRequest = new BulkRequest();

        for (SkuInfoVO esModel : skuInfos) {
            IndexRequest indexRequest = new IndexRequest(PRODUCT_INDEX);
            indexRequest.id(esModel.getSkuId());
            indexRequest.source(gson.toJson(skuInfos), XContentType.JSON);
            bulkRequest.add(indexRequest);
        }
        BulkResponse bulk = client.bulk(bulkRequest, ElasticSearchConfig.COMMON_OPTIONS);
        //  存入ES返回是否存在失败信息
        boolean hasFailures = bulk.hasFailures();
        List<String> collect = Arrays.stream(bulk.getItems()).map(BulkItemResponse::getId).collect(Collectors.toList());
        log.info("商品上架完成:{}", collect);
        return hasFailures;
    }


    /**
     * 下架商品信息
     *
     * @param message 消息
     * @param channel 队列
     * @throws Exception 可能出现的异常
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = SEARCH_DEL_QUEUE, durable = "true"),
            exchange = @Exchange(value = GOODS_DOWN_EXCHANGE),
            key = ""
    ))
    public void delDataBySpuId(Message message, Channel channel) throws Exception {

    }
}
