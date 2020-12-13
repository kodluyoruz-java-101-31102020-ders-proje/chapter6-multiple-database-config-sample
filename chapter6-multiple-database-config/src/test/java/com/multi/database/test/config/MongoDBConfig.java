package com.multi.database.test.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mongodb.MongoClient;

@Order(3)
@Configuration
@EnableTransactionManagement
@EnableMongoRepositories(basePackages = { "com.multi.database.dao.mongo.repository" })
@TestPropertySource( { "classpath:application.properties" })
public class MongoDBConfig extends AbstractMongoConfiguration{

	@Autowired
	private Environment env;
 
    @Override
    protected String getDatabaseName() {
        return env.getProperty("spring.data.mongodb.database");
    }
 
    @Override
    public MongoClient mongoClient() {
        return new MongoClient(env.getProperty("spring.data.mongodb.host"), env.getProperty("spring.data.mongodb.port", Integer.class));
    }
}
