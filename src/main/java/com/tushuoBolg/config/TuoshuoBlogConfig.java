package com.tushuoBolg.config;

import org.hibernate.SessionFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by hyrj on 2019/10/11.
 */
@Configuration
@ComponentScan("com.tushuoBolg")
@EnableTransactionManagement
public class TuoshuoBlogConfig implements EnvironmentAware{

    @Resource
    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {

    }

    /**
     * hibernate 配置
     * @return
     */
    @Bean
    @Primary
    public LocalSessionFactoryBean getSessionFactory(DataSource dataSource) {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", environment.getProperty("spring.jpa.hibernate.dialect"));
        properties.setProperty("hibernate.show_sql", environment.getProperty("spring.jpa.hibernate.show_sql"));
        properties.setProperty("hibernate.format_sql", environment.getProperty("spring.jpa.hibernate.format_sql"));
        properties.setProperty("hibernate.use_sql_comments", environment.getProperty("spring.jpa.hibernate.use_sql_comments"));
        properties.setProperty("hibernate.hbm2ddl.auto", environment.getProperty("spring.jpa.hibernate.hbm2ddl.auto"));
        properties.setProperty("connection.useUnicode", environment.getProperty("spring.jpa.connection.useUnicode"));
        properties.setProperty("connection.characterEncoding", environment.getProperty("spring.jpa.connection.characterEncoding"));
        LocalSessionFactoryBean lsf = new LocalSessionFactoryBean();
        lsf.setDataSource(dataSource);
        lsf.setHibernateProperties(properties);
        lsf.setPackagesToScan("com.tushuoBolg.entity");
        return lsf;
    }

    /**
     * Jdbc 数据源
     * @param dataSource
     * @return
     */
    @Primary
    @Bean
    public JdbcTemplate getJdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public SessionFactory sessionFactory(EntityManagerFactory factory) {
        if (factory.unwrap(SessionFactory.class) == null) {
            throw new NullPointerException("factory is not a hibernate factory");
        }
        return factory.unwrap(SessionFactory.class);
    }
}
