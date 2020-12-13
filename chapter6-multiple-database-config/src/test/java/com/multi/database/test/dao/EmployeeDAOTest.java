package com.multi.database.test.dao;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.multi.database.dao.entity.Employee;
import com.multi.database.dao.mysql.repository.EmployeeRepository;
import com.multi.database.service.model.EmployeeContext;
import com.multi.database.test.config.ApplicationConfig;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ApplicationConfig.class)
@TestPropertySource({ "classpath:application.properties" })
public class EmployeeDAOTest {

	@Autowired
	@Qualifier("mysqlEmployeeRepository")
	private EmployeeRepository mysqlEmployeeRepository;
	
	@Autowired
	@Qualifier("postgresEmployeeRepository")
	private com.multi.database.dao.postgres.repository.EmployeeRepository postgresEmployeeRepository;
	
	@Test
	public void queryEmployee() {
		
		Employee employee = mysqlEmployeeRepository.findWithEmpNo(10002L);
		
		Assert.assertNotNull(employee);
		Assert.assertTrue(employee.getEmpNo() > 0);
	}
	
	@Test
	@Transactional(transactionManager = "mysqlTxManager")
	public void saveEmployeeInMysql() {
		
		Long maxId = mysqlEmployeeRepository.findMaxId() + 1;
		
		Employee employee = new Employee();
		employee.setEmpNo(maxId + 1);
		employee.setName("Batuhan");
		employee.setLastName("Duzgun");
		employee.setGender("M");
		employee.setBirthDate(new Date());
		employee.setHireDate(new Date());
		
		employee = mysqlEmployeeRepository.save(employee);
		
		employee = mysqlEmployeeRepository.findWithEmpNo(maxId + 1);
		
		Assert.assertNotNull(employee);
		Assert.assertTrue(employee.getEmpNo() > 0);
	}
	
	@Test
	@Transactional(transactionManager = "postgresTxManager")
	public void saveEmployeeInPostgres() {
		
		Long maxId = postgresEmployeeRepository.findMaxId() + 1;
		
		Employee employee = new Employee();
		employee.setEmpNo(maxId + 1);
		employee.setName("Batuhan");
		employee.setLastName("Duzgun");
		employee.setGender("M");
		employee.setBirthDate(new Date());
		employee.setHireDate(new Date());
		
		employee = postgresEmployeeRepository.save(employee);
		
		employee = postgresEmployeeRepository.findWithEmpNo(maxId + 1);
		
		Assert.assertNotNull(employee);
		Assert.assertTrue(employee.getEmpNo() > 0);
	}
	
	@Test
	@Transactional(transactionManager = "chainedMysqlPostgresTxManager")
	public void transferEntityBetweenDBs() {
		
		Long maxId = mysqlEmployeeRepository.findMaxId();
		
		Employee employee = new Employee();
		employee.setEmpNo(maxId + 1);
		employee.setName("Demo2");
		employee.setLastName("User2");
		employee.setGender("M");
		employee.setBirthDate(new Date());
		employee.setHireDate(new Date());
		
		employee = mysqlEmployeeRepository.save(employee);
		
		employee = postgresEmployeeRepository.save(employee);
		
		Assert.assertNotNull(employee);
		Assert.assertTrue(employee.getEmpNo() > 0);
	}
}
