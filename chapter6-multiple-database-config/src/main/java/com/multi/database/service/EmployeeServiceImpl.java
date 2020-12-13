package com.multi.database.service;

import java.math.BigInteger;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.multi.database.dao.entity.Employee;
import com.multi.database.dao.mongo.entity.EmployeeDraft;
import com.multi.database.dao.mongo.repository.EmployeeDraftRepository;
import com.multi.database.dao.mysql.repository.EmployeeRepository;
import com.multi.database.service.model.EmployeeContext;


@Service
public class EmployeeServiceImpl implements EmployeeService{

	@Autowired
	@Qualifier("mysqlEmployeeRepository")
	private EmployeeRepository mysqlEmployeeRepository;
	
	@Autowired
	@Qualifier("postgresEmployeeRepository")
	private com.multi.database.dao.postgres.repository.EmployeeRepository postgresEmployeeRepository;
	
	@Autowired
	@Qualifier("employeeDraftRepository")
	private EmployeeDraftRepository employeeDraftRepository;
	
	
	public Employee findByEmpNo(Long empNo) {
		
		return mysqlEmployeeRepository.findWithEmpNo(empNo);
	}
	
	public Long findMaxId() {
		
		return mysqlEmployeeRepository.findMaxId();
	}
	
	@Transactional(transactionManager = "mysqlTxManager")
	public Long save(EmployeeContext employeeContext) {
		
		Long maxId = mysqlEmployeeRepository.findMaxId() + 1;
		
		Employee employee = new Employee();
		employee.setEmpNo(maxId);
		employee.setName(employeeContext.getName());
		employee.setLastName(employeeContext.getLastName());
		employee.setGender(employeeContext.getGender());
		employee.setBirthDate(employeeContext.getBirthDate());
		employee.setHireDate(employeeContext.getHireDate());
		
		/*
		if(employee.getEmpNo() > 0) {
			throw new RuntimeException("CUSTOM ERROR FOR ROLLBACK!");
		}
		*/
		
		
		employee = mysqlEmployeeRepository.save(employee);
		return employee.getEmpNo();
	}
	
	public Long saveAsDraft(Long empNo) {
		
		Employee employee = mysqlEmployeeRepository.findWithEmpNo(empNo);
		
		if(employee == null) {
			return -1L;
		}
		
		EmployeeDraft draft = new EmployeeDraft();
		draft.setBirthDate(employee.getBirthDate());
		draft.setHireDate(employee.getHireDate());
		draft.setEmpNo(BigInteger.valueOf(employee.getEmpNo()));
		draft.setGender(employee.getGender());
		draft.setLastName(employee.getLastName());
		draft.setName(employee.getName());
		
		employeeDraftRepository.save(draft);
		return employee.getEmpNo();
	}
	
	@Transactional(transactionManager = "chainedMysqlPostgresTxManager")
	public void transferRecordsBetweenDBs() {
		
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
		
		/*
		if(maxId > 0) {
			throw new RuntimeException("Transfer Failed!");
		}
		*/
	}
}


