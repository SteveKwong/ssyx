package com.atguigu.ssyx.search.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author kuanggong
 */
@Configuration
public class ElasticSearchConfig {
    @Value("${elastic.hostname}")
    private String hostname;
    @Value("${elastic.port}")
    private String port;

    public static final RequestOptions COMMON_OPTIONS;

    static {
        RequestOptions.Builder builder = RequestOptions.DEFAULT.toBuilder();
        COMMON_OPTIONS = builder.build();
    }

    @Bean("esClient")
    public RestHighLevelClient esClient() {
        int i = Integer.parseInt(port);
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(hostname, i, "http")
                ));
        return client;
    }


}
