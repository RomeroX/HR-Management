package org.ada.HRmanagement.service;

import org.ada.HRmanagement.dto.AbsenceDTO;
import org.ada.HRmanagement.dto.EmployeeDTO;
import org.ada.HRmanagement.repository.AbsenceRepository;
import org.springframework.stereotype.Service;

@Service
public class AbsenceService {

    private final AbsenceRepository absenceRepository;

    public AbsenceService(AbsenceRepository absenceRepository) {
        this.absenceRepository = absenceRepository;
    }

    public AbsenceDTO create (AbsenceDTO absenceDTO){

        return absenceDTO;
    }
}
