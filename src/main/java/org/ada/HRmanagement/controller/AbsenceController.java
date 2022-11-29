package org.ada.HRmanagement.controller;

import org.ada.HRmanagement.dto.AbsenceDTO;
import org.ada.HRmanagement.dto.EmployeeDTO;
import org.ada.HRmanagement.service.AbsenceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController

@RequestMapping(path = "/employees/{employeeId}/absences")
public class AbsenceController {

    private final AbsenceService absenceService;

    public AbsenceController(AbsenceService absenceService) {
        this.absenceService = absenceService;
    }

    @PostMapping
    public ResponseEntity create(@PathVariable Integer employeeId,
                                 @RequestBody AbsenceDTO absenceDTO) {
        absenceService.create(employeeId, absenceDTO);
        return new ResponseEntity(absenceDTO.getId(), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity retrieve(@PathVariable Integer employeeId){
        return new ResponseEntity(absenceService.retrieveAll(employeeId), HttpStatus.OK);
    }

    @GetMapping("/{absenceId}")
    public ResponseEntity retrieveById(@PathVariable Integer employeeId, @PathVariable Integer absenceId ) {
        AbsenceDTO absenceDTO = absenceService.retrieveById(employeeId, absenceId);
        return new ResponseEntity(absenceDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{absenceId}")
    public ResponseEntity delete(@PathVariable Integer absenceId){
        absenceService.delete(absenceId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/{absenceId}")
    public ResponseEntity replace(@PathVariable Integer employeeId, @PathVariable Integer absenceId,
                                  @RequestBody AbsenceDTO absenceDTO){
        absenceService.replace(employeeId, absenceId, absenceDTO);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PatchMapping("/{absenceId}")
    public ResponseEntity modify(@PathVariable Integer employeeId, @PathVariable Integer absenceId,
                                 @RequestBody Map<String, Object> fieldsToModify) {
        absenceService.modify(employeeId, absenceId, fieldsToModify);
        return new ResponseEntity(HttpStatus.OK);
    }
}
