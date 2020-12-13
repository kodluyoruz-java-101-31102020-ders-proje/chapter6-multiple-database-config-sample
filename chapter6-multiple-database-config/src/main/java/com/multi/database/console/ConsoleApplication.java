package com.multi.database.console;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.multi.database.service.EmployeeServiceImpl;
import com.multi.database.service.model.EmployeeContext;


@Component
public class ConsoleApplication implements CommandLineRunner {

	@Autowired
	private EmployeeServiceImpl employeeService;
	
	
	@Override
	public void run(String... args) throws Exception {
	
		/*
		 * mysql transactional
		EmployeeContext context = new EmployeeContext();
		context.setName("Batuhan");
		context.setLastName("Duzgun");
		context.setGender("M");
		context.setBirthDate(new Date());
		context.setHireDate(new Date());
		
		employeeService.save(context);
		*/
		
		// mongodb
		// employeeService.saveAsDraft(10059l);
		
		// mysql and postgresql transaction management!
		employeeService.transferRecordsBetweenDBs();
		System.out.println("Transferred from MySQL to Postgres!");
	}

}
