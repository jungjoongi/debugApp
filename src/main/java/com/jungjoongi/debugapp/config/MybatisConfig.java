package com.jungjoongi.debugapp.config;

import javax.sql.DataSource;

import com.jungjoongi.debugapp.common.util.EncryptHelper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement
public class MybatisConfig {

    private static Logger LOGGER = LoggerFactory.getLogger(MybatisConfig.class);
    @Value("${properties.jdbc.driver}")
    String encDriver;
    @Value("${properties.jdbc.url}")
    String encUrl;
    @Value("${properties.jdbc.userName}")
    String encUserName;
    @Value("${properties.jdbc.password}")
    String encPassword;
    @Value("${properties.encrypt.key}")
    String key;
    @Value("${properties.encrypt.iv}")
    String iv;

    @Bean
    public DataSource batisDataSource() {
        String driver = "oracle.jdbc.driver";
        String url = "jdbc:oracle://localhost:3306";
        String userName = "admin";
        String password ="password";

        try {
            driver = EncryptHelper.decAES(encDriver, iv, key);
            url = EncryptHelper.decAES(encUrl, iv, key);
            userName = EncryptHelper.decAES(encUserName, iv, key);
            password = EncryptHelper.decAES(encPassword, iv, key);
        } catch (Exception e) {
            LOGGER.error("[JpaConfig] defaultDataSource() Exception : {}", e.getMessage());
        }
        HikariConfig dataSourceConfig = new HikariConfig();
        dataSourceConfig.setDriverClassName(driver);
        dataSourceConfig.setJdbcUrl(url);
        dataSourceConfig.setUsername(userName);
        dataSourceConfig.setPassword(password);
        dataSourceConfig.setMaximumPoolSize(10);
        dataSourceConfig.setMinimumIdle(5);
        dataSourceConfig.setMaxLifetime(1200000);
        dataSourceConfig.setConnectionTimeout(20000);
        dataSourceConfig.setIdleTimeout(300000);
        dataSourceConfig.validate();
        return new HikariDataSource(dataSourceConfig);
    }

    @Bean(name= "batisSqlSessionFactory")
    public SqlSessionFactory batisSqlSessionFactory(@Qualifier("batisDataSource") DataSource batisDataSource, ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sqlSession = new SqlSessionFactoryBean();
        sqlSession.setDataSource(batisDataSource);
        sqlSession.setConfigLocation(applicationContext.getResource("classpath:mybatis/mybatis-config.xml")); //mybatis-config.xml의 경로
        sqlSession.setMapperLocations(applicationContext.getResources("classpath:mapper/**/*SQL.xml")); //쿼리문을 관리하는 mapper파일의 경로

        return sqlSession.getObject();

    }

    @Bean(name = "batisSqlSessionTemplate")
    public SqlSessionTemplate batisSqlSessionTemplate(SqlSessionFactory batisSqlSessionFactory) throws Exception {

        return new SqlSessionTemplate(batisSqlSessionFactory);
    }

}



