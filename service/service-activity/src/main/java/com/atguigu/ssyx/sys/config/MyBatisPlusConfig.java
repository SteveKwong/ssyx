package com.atguigu.ssyx.sys.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author kuanggong
 * @date 2023/7/11 11:32
 * @project_name atguigu-ssyx-parent
 */
@Configuration
public class MyBatisPlusConfig {

    @Autowired
    @Qualifier(value = "dataSource")
    DataSource dataSource;

    @Autowired
    MybatisPlusInterceptor paginationInnerInterceptor;

    @Bean("sqlSessionFactory")
    MybatisSqlSessionFactoryBean bean() {
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        // 设置映射文件路径
        // 设置分页插件
        bean.setPlugins(paginationInnerInterceptor);
        return bean;
    }

    /**
     * myBatisPlus 分页插件
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return mybatisPlusInterceptor;
    }
}

