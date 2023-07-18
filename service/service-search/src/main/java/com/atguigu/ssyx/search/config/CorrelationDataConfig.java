package com.atguigu.ssyx.search.config;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author kuanggong
 * @date 2023/7/18 11:43
 * @project_name atguigu-ssyx-parent
 */
@Component
public class CorrelationDataConfig {

    @Bean("correlationData")
    public CorrelationData createCorrelationData() {
    return new CorrelationData(UUID.randomUUID().toString());
    }
}
