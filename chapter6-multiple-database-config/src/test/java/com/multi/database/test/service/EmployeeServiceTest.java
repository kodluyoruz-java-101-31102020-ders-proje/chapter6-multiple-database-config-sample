package com.multi.database.test.service;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import com.multi.database.dao.entity.Employee;
import com.multi.database.dao.mongo.repository.EmployeeDraftRepository;
import com.multi.database.dao.mysql.repository.EmployeeRepository;
import com.multi.database.service.EmployeeServiceImpl;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceTest {
	
	@Mock
    private EmployeeServiceImpl employeeService;
	
	
	@Before
	public void setUp() {
	    Employee emp = new Employee();
	    emp.setEmpNo(10L);
	    emp.setName("Mehmet");
	    emp.setLastName("Filiz");
	    emp.setGender("M");
	    emp.setBirthDate(new Date());
	    emp.setHireDate(new Date());
	 
	    Mockito
	    	.when(employeeService.findByEmpNo(10L))
	    	.thenReturn(emp);
	}
	
	
	@Test
	public void queryEmployee() {
		
		Employee emp = employeeService.findByEmpNo(10L);
		Assert.assertNotNull(emp);
		Assert.assertTrue(emp.getEmpNo() > 0);
	}
}
