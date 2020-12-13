package com.multi.database.dao.mysql.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.multi.database.dao.entity.Employee;


@Repository("mysqlEmployeeRepository")
public interface EmployeeRepository extends CrudRepository<Employee, Long> {
	
	@Query(value = "FROM Employee e WHERE e.empNo = :empNo")
	public Employee findWithEmpNo(@Param("empNo") Long empNo);
	
	@Query(value = "SELECT MAX(e.empNo) FROM Employee e")
	public Long findMaxId();
	
}
