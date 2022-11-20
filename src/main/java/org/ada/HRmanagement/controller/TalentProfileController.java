package org.ada.HRmanagement.controller;

import org.ada.HRmanagement.dto.AbsenceDTO;
import org.ada.HRmanagement.dto.TalentProfileDTO;
import org.ada.HRmanagement.service.TalentProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController

@RequestMapping(path = "/talent-profiles")
public class TalentProfileController {

    private final TalentProfileService talentProfileService;

    public TalentProfileController(TalentProfileService talentProfileService) {
        this.talentProfileService = talentProfileService;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody TalentProfileDTO talentProfileDTO) {
        TalentProfileDTO createdTalentProfileDTO = talentProfileService.create(talentProfileDTO);
        //return new ResponseEntity(, HttpStatus.CREATED);
        return null;
    }

    @GetMapping
    public ResponseEntity retrieve(){
        // return new ResponseEntity(, HttpStatus.OK);
        return null;
    }
}
