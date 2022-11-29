package org.ada.HRmanagement.service;

import org.ada.HRmanagement.dto.EmployeeDTO;
import org.ada.HRmanagement.entity.Employee;
import org.ada.HRmanagement.exceptions.ExistingResourceException;
import org.ada.HRmanagement.exceptions.ResourceNotFoundException;
import org.ada.HRmanagement.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private final EmployeeRepository employeeRepository;
    private final AbsenceService absenceService;
    private final TalentProfileService talentProfileService;

    public EmployeeService(EmployeeRepository employeeRepository, AbsenceService absenceService, TalentProfileService talentProfileService) {
        this.employeeRepository = employeeRepository;
        this.absenceService = absenceService;
        this.talentProfileService = talentProfileService;
    }

    public void create (EmployeeDTO employeeDTO) {
        Employee employee = mapToEntity(employeeDTO);
        checkForExistingEmployee(employee.getIdentificationNumber());

        if (employeeDTO.getManager() != null) {
            employee.setManager(findEmployee(employeeDTO.getManager(), "El mánager que intenta asignarle al empleado, no existe"));
        }
        employee = employeeRepository.save(employee);

        if (!CollectionUtils.isEmpty(employeeDTO.getAbsencesDTO())) {
            absenceService.create(employee, employeeDTO.getAbsencesDTO());
        }

        if (employeeDTO.getTalentProfileDTO() != null) {
            talentProfileService.create(employee, employeeDTO.getTalentProfileDTO());
        }

        employeeDTO.setId(employee.getId());

    }


    public List<EmployeeDTO> retrieveAll(){
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
                .map(employee -> mapToDTO(employee))
                .collect(Collectors.toList());

    }

    public EmployeeDTO retrieveById(Integer id){
        Optional<Employee> employee = employeeRepository.findById(id);
        if (!employee.isPresent()){
            throw new ResourceNotFoundException();
        }
        return mapToDTO(employee.get());
    }

    public void replace(Integer employeeId, EmployeeDTO employeeDTO) {
        Employee employeeToReplace = findEmployee(employeeId, "El empleado no existe");
        checkForExistingEmployee(employeeDTO.getIdentificationNumber());
        employeeToReplace.setFirstName(employeeDTO.getFirstName());
        employeeToReplace.setMiddleName(employeeDTO.getMiddleName());
        employeeToReplace.setLastName(employeeDTO.getLastName());
        employeeToReplace.setBirthdate(LocalDate.parse(employeeDTO.getBirthdate(), DATE_TIME_FORMATTER));
        employeeToReplace.setJobName(employeeDTO.getJobName());
        employeeToReplace.setSalary(employeeDTO.getSalary());
        employeeToReplace.setIdentificationNumber(employeeDTO.getIdentificationNumber());
        employeeToReplace.setIsManager(employeeDTO.getIsManager());
        employeeToReplace.setGender(employeeDTO.getGender());
        employeeToReplace.setMaritalStatus(employeeDTO.getMaritalStatus());
        employeeToReplace.setDepartmentId(employeeDTO.getDepartmentId());
        employeeToReplace.setIdentificationTypeId(employeeDTO.getIdentificationTypeId());
        employeeToReplace.setActive(employeeDTO.getIsActive());
        employeeToReplace.setHireDate(LocalDate.parse(employeeDTO.getHireDate(), DATE_TIME_FORMATTER));
        if (employeeDTO.getManager() != null){
            employeeToReplace.setManager(findEmployee(employeeDTO.getManager(), "El mánager que intenta asignarle al empleado, no existe"));
        } else{
            employeeToReplace.setManager(null);
        }
        employeeRepository.save(employeeToReplace);
    }

    public void modify(Integer employeeId, Map<String, Object> fieldsToModify) {
        Employee employeeToModify = findEmployee(employeeId, "El empleado no existe");
        if (fieldsToModify.containsKey("identification_number")){
            checkForExistingEmployee(fieldsToModify.get("identification_number").toString());
        }
        fieldsToModify.forEach((key, value) -> employeeToModify.modifyAttributeValue(key, value));
        if (fieldsToModify.containsKey("manager_id")){
            Employee newManager = null;
            if (fieldsToModify.get("manager_id") != null){
                newManager = findEmployee((Integer)fieldsToModify.get("manager_id"), "El mánager que intenta asignarle al empleado, no existe");
            }
            employeeToModify.setManager(newManager);
        }
        employeeRepository.save(employeeToModify);
    }

    private void checkForExistingEmployee(String identificationNumber) {
        Optional<Employee> employee = employeeRepository.findByIdentificationNumber(identificationNumber);
        if (employee.isPresent()){
            throw new ExistingResourceException();
        }
    }

    private Employee findEmployee(Integer id, String msg){
        Optional<Employee> employee = employeeRepository.findById(id);
        if (!employee.isPresent()){
            throw new ResourceNotFoundException(msg);
        }
        return employee.get();
    }

    private EmployeeDTO mapToDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO(employee.getFirstName(),
                employee.getMiddleName(),
                employee.getLastName(),
                employee.getBirthdate().toString(),
                employee.getJobName(),
                employee.getSalary(),
                employee.getIdentificationNumber(),
                employee.getIsManager(),
                employee.getGender(),
                employee.getMaritalStatus(),
                employee.getDepartmentId(),
                employee.getIdentificationTypeId(),
                employee.getActive(),
                employee.getHireDate().toString(),
                absenceService.mapToDTOS(employee.getAbsences()));
        employeeDTO.setId(employee.getId());
        if (employee.getManager() != null){
            employeeDTO.setManager(employee.getManager().getId());
        }
        if (employee.getTalentProfile() != null){
            employeeDTO.setTalentProfileDTO(talentProfileService.mapToDTO(employee.getTalentProfile()));
        }
        return employeeDTO;
    }

    private Employee mapToEntity(EmployeeDTO employeeDTO) {
        Employee employee = new Employee(employeeDTO.getFirstName(),
                employeeDTO.getMiddleName(),
                employeeDTO.getLastName(),
                LocalDate.parse(employeeDTO.getBirthdate(), DATE_TIME_FORMATTER),
                employeeDTO.getJobName(),
                employeeDTO.getSalary(),
                employeeDTO.getIdentificationNumber(),
                employeeDTO.getIsManager(),
                employeeDTO.getGender(),
                employeeDTO.getMaritalStatus(),
                employeeDTO.getDepartmentId(),
                employeeDTO.getIdentificationTypeId(),
                employeeDTO.getIsActive(),
                LocalDate.parse(employeeDTO.getHireDate(), DATE_TIME_FORMATTER));

        return employee;
    }
}
