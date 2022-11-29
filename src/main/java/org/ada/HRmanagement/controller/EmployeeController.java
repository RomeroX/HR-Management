package org.ada.HRmanagement.controller;


import org.ada.HRmanagement.dto.EmployeeDTO;
import org.ada.HRmanagement.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController

@RequestMapping(path = "/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody EmployeeDTO employeeDTO) {
        employeeService.create(employeeDTO);
        return new ResponseEntity(employeeDTO.getId(), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity retrieve(){
        return new ResponseEntity(employeeService.retrieveAll(), HttpStatus.OK);
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity retrieveById(@PathVariable Integer employeeId) {
        EmployeeDTO employeeDTO = employeeService.retrieveById(employeeId);
        return new ResponseEntity(employeeDTO, HttpStatus.OK);
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity replace(@PathVariable Integer employeeId,
                                  @RequestBody EmployeeDTO employeeDTO){
        employeeService.replace(employeeId, employeeDTO);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PatchMapping ("/{employeeId}")
    public ResponseEntity modify(@PathVariable Integer employeeId,
                                 @RequestBody Map<String, Object> fieldsToModify){
        employeeService.modify(employeeId, fieldsToModify);
        return new ResponseEntity(HttpStatus.OK);
    }
}
