package com.multi.database.test.config;

import java.sql.SQLException;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Order(1)
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories( 
		basePackages = { "com.multi.database.dao.mysql.repository" }, 
		entityManagerFactoryRef = "mysqlEntityManagerFactory", 
		transactionManagerRef = "mysqlTxManager")
@TestPropertySource({ "classpath:application.properties" })
public class MySQLConfig {

	@Autowired
	private Environment env;
	
	@Bean("mysqlDataSource")
	@Primary
	public DataSource dataSource() {
		
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getProperty("spring.datasource.mysql.driver"));
		dataSource.setUrl(env.getProperty("spring.datasource.mysql.url"));
		dataSource.setUsername(env.getProperty("spring.datasource.mysql.username"));
		dataSource.setPassword(env.getProperty("spring.datasource.mysql.password"));
		
		return dataSource;
	}
	
	@Bean("mysqlEntityManagerFactory")
	@Primary
	public EntityManagerFactory entityManagerFactory() throws SQLException {

		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setDatabase(Database.MYSQL);
		vendorAdapter.setDatabasePlatform(env.getProperty("spring.jpa.mysql.database-platform"));

		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setPackagesToScan("com.multi.database.dao.entity");
		factory.setDataSource(dataSource());
		factory.afterPropertiesSet();
		factory.setJpaProperties(additionalProperties());
		return factory.getObject();
	}
	
	@Bean("mysqlTxManager")
	@Primary
	public PlatformTransactionManager transactionManager() throws SQLException {
		return new JpaTransactionManager(entityManagerFactory());
	}
	
	private final Properties additionalProperties() {
		
		Properties properties = new Properties();
		properties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
		properties.setProperty("hibernate.dialect", env.getProperty("spring.jpa.properties.mysql.hibernate.dialect"));
		properties.setProperty("hibernate.show_sql", env.getProperty("spring.jpa.show-sql"));
		properties.setProperty("hibernate.format_sql", env.getProperty("spring.jpa.properties.hibernate.format_sql"));
		return properties;
	}
}
