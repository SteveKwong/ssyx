package com.atguigu.ssyx.search.service.impl;

import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.search.remoteinvo.ProductService;
import com.atguigu.ssyx.search.vo.SkuInfoVO;
import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import io.jsonwebtoken.lang.Assert;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;

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
            if (!spuId.isEmpty()) {
                Result<SkuInfoVO> skuListBySpuId = productService.findSkuListBySpuId(spuId);
                SkuInfoVO skuInfo = skuListBySpuId.getData();
                Assert.notNull(skuInfo,"未查询到skuInfo相关的信息");
                // 使用es进行商品的上架
                boolean upResult = productStatusUp(skuInfo);
                // 如果商品上架失败,则重新投递到队列队尾进行上架排序
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
     * @param infoVO 单个商品的sku的信息集合
     */
    private boolean productStatusUp(SkuInfoVO infoVO) throws Exception {
        if (infoVO == null) {
            return true;
        }
        //创建批量请求
        boolean exists = indexExists(PRODUCT_INDEX);
        IndexRequest indexRequest;
        IndexResponse indexResponse;
        // 不存在则重新创建一个索引库,索引库的数据为传入的sku的信息json
        if (!exists) {
            // 设置索引名称
            indexRequest = new IndexRequest(PRODUCT_INDEX);
            // 设置文档ID
            indexRequest.id(infoVO.getSkuId());
            // 设置文档内容
            indexRequest.source(buildJSONFromEntity(infoVO), XContentType.JSON);
            indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
            if (indexResponse.status().getStatus() == 1) {
                // 插入
                insertIntoIndex(infoVO);
            }
        } else {
            insertIntoIndex(infoVO);
        }
        return false;
    }

    /**
     * 插入到索引库
     *
     * @param infoVO 信息
     * @throws IOException 异常
     */
    private void insertIntoIndex(SkuInfoVO infoVO) throws IOException {
        IndexRequest indexRequest;
        IndexResponse indexResponse;
        indexRequest = new IndexRequest(PRODUCT_INDEX);
        indexRequest.id(infoVO.getSkuId());
        indexRequest.source(gson.toJson(infoVO), XContentType.JSON);
        indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
        System.out.println("Index created with status: " + indexResponse.status());
    }

    /**
     * 判断索引库是否存在
     */
    public boolean indexExists(String indexName) throws Exception {
        IndicesClient indicesClient = client.indices();
        // 创建get请求
        GetIndexRequest request = new GetIndexRequest(indexName);
        // 判断索引库是否存在
        boolean exists = indicesClient.exists(request, RequestOptions.DEFAULT);
        return exists;
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

    /**
     * 创建索引库字段实体
     * @param skuInfoVO s
     * @return s
     * @throws IOException y
     */
    private XContentBuilder buildJSONFromEntity(SkuInfoVO skuInfoVO) throws IOException {
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        // 设置字段名和对应的值
        builder.field("sku_id", skuInfoVO.getSkuId());
        builder.field("sku_name", skuInfoVO.getSkuName());
        builder.field("price", skuInfoVO.getPrice());
        builder.field("market_price", skuInfoVO.getMarketPrice());
        builder.field("stock", skuInfoVO.getStock());
        builder.field("sku_image", skuInfoVO.getSkuImage());
        builder.field("sales", skuInfoVO.getSales());
        builder.field("skuPosters", skuInfoVO.getSkuPosters());
        builder.endObject();
        return builder;
    }

}
