package com.multi.database.dao.postgres.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.multi.database.dao.entity.Employee;


@Repository("postgresEmployeeRepository")
public interface EmployeeRepository extends CrudRepository<Employee, Long> {

	@Query(value = "SELECT emp FROM Employee emp WHERE emp.empNo = :mahmut")
	public Employee findWithEmpNo(@Param("mahmut") Long empNo);
	
	@Query(value = "SELECT MAX(e.empNo) FROM Employee e")
	public Long findMaxId();
	
}
