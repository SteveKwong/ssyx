package com.atguigu.ssyx.search.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author kuanggong
 * @date 2023/7/18 17:13
 * @project_name atguigu-ssyx-parent
 */
@Configuration
public class GsonBuilderConfig {

    @Bean
    public Gson gsonBuild() {
        return new GsonBuilder()
                // 设置日期格式
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
    }
}
