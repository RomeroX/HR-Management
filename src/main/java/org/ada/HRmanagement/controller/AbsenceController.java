package org.ada.HRmanagement.controller;

import org.ada.HRmanagement.dto.AbsenceDTO;
import org.ada.HRmanagement.dto.EmployeeDTO;
import org.ada.HRmanagement.service.AbsenceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController

@RequestMapping(path = "/absences")
public class AbsenceController {

    private final AbsenceService absenceService;

    public AbsenceController(AbsenceService absenceService) {
        this.absenceService = absenceService;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody AbsenceDTO absenceDTO) {
        AbsenceDTO createdAbsenceDTO = absenceService.create(absenceDTO);
        //return new ResponseEntity(, HttpStatus.CREATED);
        return null;
    }

    @GetMapping
    public ResponseEntity retrieve(){
        // return new ResponseEntity(, HttpStatus.OK);
        return null;
    }
}
