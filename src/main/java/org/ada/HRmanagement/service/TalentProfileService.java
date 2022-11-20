package org.ada.HRmanagement.service;

import org.ada.HRmanagement.dto.TalentProfileDTO;
import org.ada.HRmanagement.repository.TalentProfileRepository;
import org.springframework.stereotype.Service;

@Service
public class TalentProfileService {

    private final TalentProfileRepository talentProfileRepository;

    public TalentProfileService(TalentProfileRepository talentProfileRepository) {
        this.talentProfileRepository = talentProfileRepository;
    }

    public TalentProfileDTO create(TalentProfileDTO talentProfileDTO){

        return talentProfileDTO;
    }
}
