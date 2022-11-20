package org.ada.HRmanagement.service;

import org.ada.HRmanagement.dto.EmployeeDTO;
import org.ada.HRmanagement.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public EmployeeDTO create (EmployeeDTO employeeDTO){

        return employeeDTO;
    }
}
