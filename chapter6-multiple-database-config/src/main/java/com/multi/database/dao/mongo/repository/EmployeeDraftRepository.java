package com.multi.database.dao.mongo.repository;

import java.math.BigInteger;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.multi.database.dao.mongo.entity.EmployeeDraft;

@Repository("employeeDraftRepository")
public interface EmployeeDraftRepository extends MongoRepository<EmployeeDraft, BigInteger>{

	@Query(value = "{ 'empNo': ?0 }")
	public EmployeeDraft findEmployeeByEmpNumber(Long id);
}
