package org.ada.HRmanagement.service;

import org.ada.HRmanagement.dto.TalentProfileDTO;
import org.ada.HRmanagement.entity.Employee;
import org.ada.HRmanagement.entity.TalentProfile;
import org.ada.HRmanagement.exceptions.ExistingResourceException;
import org.ada.HRmanagement.exceptions.ResourceNotFoundException;
import org.ada.HRmanagement.repository.EmployeeRepository;
import org.ada.HRmanagement.repository.TalentProfileRepository;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class TalentProfileService {

    private final TalentProfileRepository talentProfileRepository;
    private final EmployeeRepository employeeRepository;

    public TalentProfileService(TalentProfileRepository talentProfileRepository, EmployeeRepository employeeRepository) {
        this.talentProfileRepository = talentProfileRepository;
        this.employeeRepository = employeeRepository;
    }

    public TalentProfileDTO create(Employee employee, TalentProfileDTO talentProfileDTO){
        TalentProfile talentProfile = mapToEntity(talentProfileDTO, employee);
        talentProfileRepository.save(talentProfile);
        return talentProfileDTO;
    }


    public void create(Integer employeeId, TalentProfileDTO talentProfileDTO) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        if (!employee.isPresent()){
            throw new ResourceNotFoundException("El empleado al que intenta asociar, no existe.");
        }
        if (employee.get().getTalentProfile() != null) {
            throw new ExistingResourceException("El empleado ya cuenta con un Perfil de Talento asociado");
        }
        TalentProfile talentProfile = mapToEntity(talentProfileDTO, employee.get());
        talentProfileRepository.save(talentProfile);
    }

    public TalentProfileDTO retrieve(Integer employeeId) {
        Employee employee = validateEmployee(employeeId);
        TalentProfile talentProfile = findTalentProfile(employee);
        return mapToDTO(talentProfile);
    }

    public void delete(Integer employeeId) {
        Employee employee = validateEmployee(employeeId);
        TalentProfile talentProfile = findTalentProfile(employee);
        talentProfileRepository.delete(talentProfile);
    }

    public void replace(Integer employeeId, TalentProfileDTO talentProfileDTO) {
        Employee employee = validateEmployee(employeeId);
        TalentProfile talentProfileToReplace = findTalentProfile(employee);
        talentProfileToReplace.setProfessionalExperience(talentProfileDTO.getProfessionalExperience());
        talentProfileToReplace.setSkills(talentProfileDTO.getSkills());
        talentProfileToReplace.setHighestEducationLevel(talentProfileDTO.getHighestEducationLevel());
        talentProfileRepository.save(talentProfileToReplace);
    }

    public void modify(Integer employeeId, Map<String, Object> fieldsToModify) {
        Employee employee = validateEmployee(employeeId);
        TalentProfile talentProfileToModify = findTalentProfile(employee);
        fieldsToModify.forEach((key, value) -> talentProfileToModify.modifyAttributeValue(key, value));
        talentProfileRepository.save(talentProfileToModify);
    }

    private TalentProfile findTalentProfile (Employee employee){
        Optional<TalentProfile> talentProfile = talentProfileRepository.findByEmployee(employee);
        if (!talentProfile.isPresent()){
            throw new ResourceNotFoundException("El empleado no tiene un perfil de talento");
        }
        return talentProfile.get();
    }

    private Employee validateEmployee (Integer employeeId){
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        if (!employee.isPresent()){
            throw new ResourceNotFoundException();
        }
        return employee.get();
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
        talentProfileDTO.setId(talentProfile.getId());
        return talentProfileDTO;
    }
}
