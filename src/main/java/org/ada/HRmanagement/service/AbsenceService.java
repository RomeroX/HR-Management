package org.ada.HRmanagement.service;

import org.ada.HRmanagement.dto.AbsenceDTO;
import org.ada.HRmanagement.entity.Absence;
import org.ada.HRmanagement.entity.AbsenceType;
import org.ada.HRmanagement.entity.Employee;
import org.ada.HRmanagement.exceptions.ExistingResourceException;
import org.ada.HRmanagement.exceptions.ResourceNotFoundException;
import org.ada.HRmanagement.repository.AbsenceRepository;
import org.ada.HRmanagement.repository.AbsenceTypeRepository;
import org.ada.HRmanagement.repository.EmployeeRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AbsenceService {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private final AbsenceRepository absenceRepository;
    private final EmployeeRepository employeeRepository;
    private final AbsenceTypeRepository absenceTypeRepository;

    public AbsenceService(AbsenceRepository absenceRepository, EmployeeRepository employeeRepository, AbsenceTypeRepository absenceTypeRepository) {
        this.absenceRepository = absenceRepository;
        this.employeeRepository = employeeRepository;
        this.absenceTypeRepository = absenceTypeRepository;
    }

    public void create (Employee employee, List<AbsenceDTO> absencesDTO){
        List<Absence> absences = absencesDTO.stream()
                .map(absenceDTO -> mapToEntity(absenceDTO, employee))
                .collect(Collectors.toList());
        absences.forEach(absence -> validateAbsenceType(absence.getAbsenceTypeId()));
        absenceRepository.saveAll(absences);
    }

    public void create (Integer employeeId, AbsenceDTO absenceDTO ){
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        if (!employee.isPresent()){
            throw new ResourceNotFoundException("El empleado al que intenta asociar, no existe.");
        }
        Absence absence = mapToEntity(absenceDTO, employee.get());
        checkForExistingAbsence(absence, employee.get());
        validateAbsenceType(absence.getAbsenceTypeId());
        absenceRepository.save(absence);
        absenceDTO.setId(absence.getId());
    }

    public List<AbsenceDTO> retrieveAll(Integer employeeId) {
        Employee employee = validateEmployee(employeeId);
        List<Absence> absences = absenceRepository.findByEmployee(employee);
        return absences.stream()
                .map(absence -> mapToDTO(absence))
                .collect(Collectors.toList());
    }


    public AbsenceDTO retrieveById(Integer employeeId, Integer absenceId) {
        Employee employee = validateEmployee(employeeId);
        Optional<Absence> absence = absenceRepository.findByIdAndEmployee(absenceId, employee);
        if (!absence.isPresent()){
            throw new ResourceNotFoundException();
        }
        return mapToDTO(absence.get());
    }

    public void delete(Integer absenceId) {
        //Con validación de ausencia perteneciente a empleado
        /*
        Employee employee = validateEmployee(employeeId);
        Optional<Absence> absence = absenceRepository.findByIdAndEmployee(absenceId, employee);
        if (!absence.isPresent()){
            throw new ResourceNotFoundException();
        }
        absenceRepository.delete(absence.get());*/
        try {
            absenceRepository.deleteById(absenceId);
        }catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundException();
        }
    }

    public void replace(Integer employeeId, Integer absenceId, AbsenceDTO absenceDTO) {
        Employee employee = validateEmployee(employeeId);
        Absence absenceToReplace = findAbsence(absenceId);
        absenceToReplace.setStartDate(LocalDate.parse(absenceDTO.getStartDate(), DATE_TIME_FORMATTER));
        absenceToReplace.setEndDate(LocalDate.parse(absenceDTO.getEndDate(), DATE_TIME_FORMATTER));
        absenceToReplace.setComments(absenceDTO.getComments());
        absenceToReplace.setAbsenceTypeId(absenceDTO.getAbsenceTypeId());
        checkForExistingAbsence(absenceToReplace, employee);
        validateAbsenceType(absenceToReplace.getAbsenceTypeId());
        absenceRepository.save(absenceToReplace);
    }

    public void modify(Integer employeeId, Integer absenceId, Map<String, Object> fieldsToModify) {
        Employee employee = validateEmployee(employeeId);
        Absence absenceToModify = findAbsence(absenceId);
        if (fieldsToModify.containsKey("absence_type_id")){
            validateAbsenceType((Integer)fieldsToModify.get("absence_type_id"));
        }
        fieldsToModify.forEach((key, value) -> absenceToModify.modifyAttributeValue(key, value));
        if (fieldsToModify.containsKey("absence_type_id") || fieldsToModify.containsKey("start_date") ) {
            checkForExistingAbsence(absenceToModify, employee);
        }
        absenceRepository.save(absenceToModify);
    }

    private Absence findAbsence(Integer absenceId){
        Optional<Absence> absence = absenceRepository.findById(absenceId);
        if (!absence.isPresent()){
            throw new ResourceNotFoundException();
        }
        return absence.get();
    }

    private Employee validateEmployee(Integer employeeId){
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        if (!employee.isPresent()){
            throw new ResourceNotFoundException("El empleado consultado no existe");
        }
        return employee.get();
    }

    private void checkForExistingAbsence(Absence absence, Employee employee ) {
        Optional<Absence> optAbsence = absenceRepository.findByEmployeeAndStartDateAndAbsenceTypeId(absence.getEmployee(), absence.getStartDate(), absence.getAbsenceTypeId());
        if (optAbsence.isPresent()) {
            throw new ExistingResourceException();
        }
    }

   private void validateAbsenceType(Integer absenceTypeId){
        Optional<AbsenceType> absenceType = absenceTypeRepository.findById(absenceTypeId);
        if (!absenceType.isPresent()){
            throw new ResourceNotFoundException("El tipo de ausencia no es válido");
        }
   }

    private Absence mapToEntity(AbsenceDTO absenceDTO, Employee employee){
        Absence absence = new Absence(LocalDate.parse(absenceDTO.getStartDate(), DATE_TIME_FORMATTER),
                LocalDate.parse(absenceDTO.getEndDate(), DATE_TIME_FORMATTER),
                absenceDTO.getComments(),
                absenceDTO.getAbsenceTypeId(),
                employee
        );
        return absence;
    }

    public List<AbsenceDTO> mapToDTOS(List<Absence> absences){
        return absences.stream()
                .map(absence -> mapToDTO(absence))
                .collect(Collectors.toList());
    }

    private AbsenceDTO mapToDTO(Absence absence){
        AbsenceDTO absenceDTO = new AbsenceDTO(absence.getStartDate().toString(),
                absence.getEndDate().toString(),
                absence.getComments(),
                absence.getAbsenceTypeId());
        absenceDTO.setId(absence.getId());
        return absenceDTO;
    }
}
