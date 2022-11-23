package org.ada.HRmanagement.controller;

import org.ada.HRmanagement.dto.AbsenceDTO;
import org.ada.HRmanagement.service.AbsenceService;
import org.ada.HRmanagement.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController

@RequestMapping(path = "/employees/{employeeId}/absences")
public class AbsenceController {

    private final AbsenceService absenceService;
    private final EmployeeService employeeService;

    public AbsenceController(AbsenceService absenceService, EmployeeService employeeService) {
        this.absenceService = absenceService;
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity create(@PathVariable Integer employeeId, @RequestBody AbsenceDTO absenceDTO) {
        AbsenceDTO createdAbsenceDTO = employeeService.createAbsence(employeeId, absenceDTO);
        return new ResponseEntity(createdAbsenceDTO.getId(), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity retrieve(@PathVariable Integer employeeId){
        return new ResponseEntity(employeeService.retrieveAllAbsences(employeeId), HttpStatus.OK);
    }

    @GetMapping("/{absenceId}")
    public ResponseEntity retrieveById(@PathVariable Integer employeeId, @PathVariable Integer absenceId ) {
        AbsenceDTO absenceDTO = employeeService.retrieveAbsenceById(employeeId, absenceId);
        return new ResponseEntity(absenceDTO, HttpStatus.OK);
    }
}
