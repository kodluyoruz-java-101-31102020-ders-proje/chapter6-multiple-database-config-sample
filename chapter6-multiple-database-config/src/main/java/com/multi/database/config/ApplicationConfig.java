package com.multi.database.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Order(4)
@Configuration
@Import({ MySQLConfig.class, PostgresConfig.class, MongoDBConfig.class })
@ComponentScan( { 
	"com.multi.database.service",
	"com.multi.database.console" } )
public class ApplicationConfig {

	@Autowired
	@Qualifier("mysqlTxManager")
	private PlatformTransactionManager mysqlTransactionManager;
	
	@Autowired
	@Qualifier("postgresTxManager")
	private PlatformTransactionManager postgresTransactionManager;
	
	
	@Bean("chainedMysqlPostgresTxManager")
	public ChainedTransactionManager getChainedTransactionManager() {
		
		return new ChainedTransactionManager(mysqlTransactionManager, postgresTransactionManager);
	}
}
