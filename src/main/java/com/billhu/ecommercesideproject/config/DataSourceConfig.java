package com.billhu.ecommercesideproject.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;


@Configuration
@MapperScan(basePackages = "com.billhu.ecommercesideproject.dao.mapper")
public class DataSourceConfig {

    @Primary
    @Bean(name = "myDataSourceProperties")
    public  DataSourceProperties dataSourceProperties(){
        return new DataSourceProperties();
    }


    @Bean(name = "myDataSource")
    @Primary
    public DataSource dataSource(DataSourceProperties properties){
        //hikari connection pool config
        HikariConfig hikariConfig =new HikariConfig();

        hikariConfig.setDriverClassName(properties.getDriverClassName());
        hikariConfig.setJdbcUrl(properties.getUrl());
        hikariConfig.setUsername(properties.getUsername());
        hikariConfig.setPassword(properties.getPassword());
        hikariConfig.setMinimumIdle(1);  //連線池最基本連線數量
        hikariConfig.setMaximumPoolSize(3); //連線池最大連線數量
        hikariConfig.setConnectionTimeout(180*1000); // 連線time out 時間 (毫秒計算)
        hikariConfig.setIdleTimeout(300*1000); //連線池連線空閒時間 設定
        hikariConfig.setConnectionTestQuery("SELECT 1"); //連線測試語法
        hikariConfig.setMaxLifetime(600*1000); //連線池連線生命週期

        return new HikariDataSource(hikariConfig);
    }




    @Bean(name = "mySqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory(@Qualifier("myDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean= new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        return bean.getObject();
    }

    @Bean
    @Primary
    public DataSourceTransactionManager dataSourceTransactionManager(@Qualifier("myDataSource")DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }
    @Bean
    @Primary
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }



}
