package com.kodlamaio.hrms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kodlamaio.hrms.entities.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{

}
