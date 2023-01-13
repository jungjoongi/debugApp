package com.weolbu.works.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
//configuration annotation

@EnableJpaRepositories(
        repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class,
        basePackages= "com.weolbu.works.domain",  //repository를 관리할 패키지 명시
        entityManagerFactoryRef = "entityManagerFactory", //EntityManagerFactory
        transactionManagerRef = "transactionManager") // transactionManager
public class JpaConfig {
    private static final String DEFAULT_NAMING_STRATEGY
            = "org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy";

    private static Logger LOGGER = LoggerFactory.getLogger(JpaConfig.class);
    @Value("${properties.jdbc.driver}")
    String encDriver;
    @Value("${properties.jdbc.url}")
    String encUrl;
    @Value("${properties.jdbc.userName}")
    String encUserName;
    @Value("${properties.jdbc.password}")
    String encPassword;
    @Value("${properties.encrypt.key}")
    String encKey;
    @Value("${properties.encrypt.iv}")
    String encIv;

    @Bean
    @Primary //해당 Bean을 우선적으로 선택하도록 하는 annotation
    public DataSource defaultDataSource() {

        String driver = "oracle.jdbc.driver";
        String url = "jdbc:mariadb://localhost:3306";
        String userName = "admin";
        String password ="password";

        try {
            /*
            driver = EncryptHelper.decAES(encDriver, encIv, encKey);
            url = EncryptHelper.decAES(encUrl, encIv, encKey);
            userName = EncryptHelper.decAES(encUserName, encIv, encKey);
            password = EncryptHelper.decAES(encPassword, encIv, encKey);
            */
            driver = encDriver;
            url = encUrl;
            userName = encUserName;
            password = encPassword;

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

    @Bean(name = "entityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder builder) {

        Map<String, String> propertiesHashMap = new HashMap<>();
        propertiesHashMap.put("hibernate.physical_naming_strategy",DEFAULT_NAMING_STRATEGY);

        LocalContainerEntityManagerFactoryBean rep =
                builder.dataSource(defaultDataSource())
                        .packages("com.weolbu.works")
                        //domain을 관리할 패키지 경로 명시 (domain = DO 파일)
                        .properties(propertiesHashMap)
                        .build();

        return rep;
    }

    @Primary
    @Bean(name = "transactionManager")
    PlatformTransactionManager transactionManager(
            EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(entityManagerFactory(builder).getObject());
    }

}

