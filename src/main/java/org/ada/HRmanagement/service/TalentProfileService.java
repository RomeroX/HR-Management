package org.ada.HRmanagement.service;

import org.ada.HRmanagement.dto.EmployeeDTO;
import org.ada.HRmanagement.dto.TalentProfileDTO;
import org.ada.HRmanagement.entity.Employee;
import org.ada.HRmanagement.entity.TalentProfile;
import org.ada.HRmanagement.repository.TalentProfileRepository;
import org.springframework.stereotype.Service;

@Service
public class TalentProfileService {

    private final TalentProfileRepository talentProfileRepository;

    public TalentProfileService(TalentProfileRepository talentProfileRepository) {
        this.talentProfileRepository = talentProfileRepository;
    }

    public void create(Employee employee, TalentProfileDTO talentProfileDTO) {
        TalentProfile talentProfile = mapToEntity(talentProfileDTO, employee);
        talentProfileRepository.save(talentProfile);
    }

    private TalentProfile mapToEntity(TalentProfileDTO talentProfileDTO, Employee employee) {
        TalentProfile talentProfile = new TalentProfile(talentProfileDTO.getProfessionalExperience(),
                talentProfileDTO.getSkills(),
                talentProfileDTO.getHighestEducationLevel(),
                employee);
        return talentProfile;
    }

    public TalentProfileDTO mapToDTO(TalentProfile talentProfile){
        TalentProfileDTO talentProfileDTO = new TalentProfileDTO(talentProfile.getProfessionalExperience(),
                talentProfile.getSkills(),
                talentProfile.getHighestEducationLevel());
        return talentProfileDTO;
    }
}
