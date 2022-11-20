package org.ada.HRmanagement.controller;


import org.ada.HRmanagement.dto.EmployeeDTO;
import org.ada.HRmanagement.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController

@RequestMapping(path = "/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody EmployeeDTO employeeDTO) {
        EmployeeDTO createdEmployeeDTO = employeeService.create(employeeDTO);
        //return new ResponseEntity(, HttpStatus.CREATED);
        return null;
    }

    @GetMapping
    public ResponseEntity retrieve(){
       // return new ResponseEntity(, HttpStatus.OK);
        return new ResponseEntity(HttpStatus.OK);
    }
}
