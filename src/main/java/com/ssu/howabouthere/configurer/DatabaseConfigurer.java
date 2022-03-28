package com.ssu.howabouthere.configurer;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import javax.sql.DataSource;

@Configuration
@MapperScan(value = {"com.ssu.howabouthere.mapper"})
public class DatabaseConfigurer {
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        final SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        Resource configLocation = new PathMatchingResourcePatternResolver().getResource("classpath:mybatis-config.xml");

        sqlSessionFactoryBean.setConfigLocation(configLocation);
        return sqlSessionFactoryBean.getObject();
    }
}

