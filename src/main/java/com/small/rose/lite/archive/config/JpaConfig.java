package com.small.rose.lite.archive.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * @Project : small-db-archive
 * @Author : zhangzongyuan
 * @Description : [ DataSourceConfig ] 说明：无
 * @Function :  功能说明：无
 * @Date ：2023/11/10 19:13
 * @Version ： 1.0
 **/
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"com.small.rose.lite.archive.module.dao.jpa" })
public class JpaConfig {

    // 配置jpa事务管理器
    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        // 配置实体管理器工厂
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }

    @Bean
    public EntityManager entityManager(DataSource dataSource) {
        return entityManagerFactory(dataSource).getObject().createEntityManager();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        // entity 包路径
        em.setPackagesToScan("com.small.rose.lite.archive.module.pojo");

        JpaVendorAdapter jpaAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(jpaAdapter);
        return em;
    }


    private final Properties jpaProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");

        return properties;
    }

}
