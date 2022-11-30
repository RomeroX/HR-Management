package org.ada.HRmanagement.controller;

import org.ada.HRmanagement.dto.TalentProfileDTO;
import org.ada.HRmanagement.service.TalentProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController

@RequestMapping(path = "/employees/{employeeId}/talent-profile")
public class TalentProfileController {

    private final TalentProfileService talentProfileService;

    public TalentProfileController(TalentProfileService talentProfileService) {
        this.talentProfileService = talentProfileService;
    }

    @PostMapping
    public ResponseEntity create(@PathVariable Integer employeeId,
                                 @RequestBody TalentProfileDTO talentProfileDTO) {
        talentProfileService.create(employeeId, talentProfileDTO);
        return new ResponseEntity(talentProfileDTO.getId(), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity retrieve(@PathVariable Integer employeeId){
        return new ResponseEntity(talentProfileService.retrieve(employeeId), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity delete(@PathVariable Integer employeeId){
        talentProfileService.delete(employeeId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity replace(@PathVariable Integer employeeId,
                                  @RequestBody TalentProfileDTO talentProfileDTO){
        talentProfileService.replace(employeeId, talentProfileDTO);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity modify(@PathVariable Integer employeeId,
                                 @RequestBody Map<String, Object> fieldsToModify){
        talentProfileService.modify(employeeId, fieldsToModify);
        return new ResponseEntity(HttpStatus.OK);
    }
}
