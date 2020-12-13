package com.multi.database.service;

import com.multi.database.dao.entity.Employee;
import com.multi.database.service.model.EmployeeContext;

public interface EmployeeService {

	public Employee findByEmpNo(Long empNo);
	public Long findMaxId();
	public Long save(EmployeeContext employeeContext);
	public Long saveAsDraft(Long empNo);
	public void transferRecordsBetweenDBs();
}
