package com.multi.database.test.config;

import java.sql.SQLException;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

@Order(2)
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories( 
		basePackages = { "com.multi.database.dao.postgres.repository" },
		entityManagerFactoryRef = "postgresEntityManagerFactory", 
		transactionManagerRef = "postgresTxManager" )
@TestPropertySource({ "classpath:application.properties" })
public class PostgresConfig {

	@Autowired
	private Environment env;
	
	@Bean("postgresDataSource")
	public DataSource dataSource() {
		
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getProperty("spring.datasource.postgres.driver"));
		dataSource.setUrl(env.getProperty("spring.datasource.postgres.url"));
		dataSource.setUsername(env.getProperty("spring.datasource.postgres.username"));
		dataSource.setPassword(env.getProperty("spring.datasource.postgres.password"));
		
		return dataSource;
	}
	
	@Bean("postgresEntityManagerFactory")
	public EntityManagerFactory entityManagerFactory() throws SQLException {

		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setDatabase(Database.POSTGRESQL);
		vendorAdapter.setDatabasePlatform(env.getProperty("spring.jpa.postgres.database-platform"));

		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setPackagesToScan("com.multi.database.dao.entity");
		factory.setDataSource(dataSource());
		factory.afterPropertiesSet();
		factory.setJpaProperties(additionalProperties());
		return factory.getObject();
	}
	
	@Bean("postgresTxManager")
	public PlatformTransactionManager transactionManager() throws SQLException {
		return new JpaTransactionManager(entityManagerFactory());
	}
	
	private final Properties additionalProperties() {
		
		Properties properties = new Properties();
		properties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
		properties.setProperty("hibernate.dialect", env.getProperty("spring.jpa.properties.postgres.hibernate.dialect"));
		properties.setProperty("hibernate.show_sql", env.getProperty("spring.jpa.show-sql"));
		properties.setProperty("hibernate.format_sql", env.getProperty("spring.jpa.properties.hibernate.format_sql"));
		properties.setProperty("hibernate.jdbc.lob.non_contextual_creation", env.getProperty("spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation"));
		properties.setProperty("hibernate.temp.use_jdbc_metadata_defaults", env.getProperty("spring.jpa.properties.hibernate.temp.use_jdbc_metadata_defaults"));

		return properties;
	}
}
