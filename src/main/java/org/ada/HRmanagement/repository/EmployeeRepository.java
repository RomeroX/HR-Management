package org.ada.HRmanagement.repository;

import org.ada.HRmanagement.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Optional<Employee> findByIdentificationNumber(String identificationNumber);
}
