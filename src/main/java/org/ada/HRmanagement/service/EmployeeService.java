package org.ada.HRmanagement.service;

import org.ada.HRmanagement.dto.EmployeeDTO;
import org.ada.HRmanagement.entity.Department;
import org.ada.HRmanagement.entity.Employee;
import org.ada.HRmanagement.entity.IdentificationType;
import org.ada.HRmanagement.exceptions.ExistingResourceException;
import org.ada.HRmanagement.exceptions.InvalidDataException;
import org.ada.HRmanagement.exceptions.ResourceNotFoundException;
import org.ada.HRmanagement.repository.DepartmentRepository;
import org.ada.HRmanagement.repository.EmployeeRepository;
import org.ada.HRmanagement.repository.IdentificationTypeRepository;
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
    private final IdentificationTypeRepository identificationTypeRepository;
    private final DepartmentRepository departmentRepository;

    public EmployeeService(EmployeeRepository employeeRepository, AbsenceService absenceService, TalentProfileService talentProfileService, IdentificationTypeRepository identificationTypeRepository, DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.absenceService = absenceService;
        this.talentProfileService = talentProfileService;
        this.identificationTypeRepository = identificationTypeRepository;
        this.departmentRepository = departmentRepository;
    }

    public void create (EmployeeDTO employeeDTO) {
        Employee employee = mapToEntity(employeeDTO);
        checkForExistingEmployee(employee.getIdentificationNumber());
        validateIdentificationType(employee.getIdentificationTypeId());
        validateDepartment(employee.getDepartmentId());

        if (employeeDTO.getManager() != null) {
            employee.setManager(findManager(employeeDTO.getManager()));
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
        Employee employeeToReplace = findEmployee(employeeId);
        checkForExistingEmployee(employeeDTO.getIdentificationNumber());
        validateIdentificationType(employeeDTO.getIdentificationTypeId());
        validateDepartment(employeeDTO.getDepartmentId());

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
            employeeToReplace.setManager(findManager(employeeDTO.getManager()));
        } else{
            employeeToReplace.setManager(null);
        }
        employeeRepository.save(employeeToReplace);
    }

    public void modify(Integer employeeId, Map<String, Object> fieldsToModify) {
        Employee employeeToModify = findEmployee(employeeId);
        if (fieldsToModify.containsKey("identification_number")){
            checkForExistingEmployee(fieldsToModify.get("identification_number").toString());
        }

        if (fieldsToModify.containsKey("identification_type_id")){
            validateIdentificationType((Integer)fieldsToModify.get("identification_type_id"));
        }

        if (fieldsToModify.containsKey("department_id")){
            validateDepartment((Integer)fieldsToModify.get("department_id"));
        }

        fieldsToModify.forEach((key, value) -> employeeToModify.modifyAttributeValue(key, value));

        if (fieldsToModify.containsKey("manager_id")){
            employeeToModify.setManager(assignManager((Integer)fieldsToModify.get("manager_id")));
        }

        employeeRepository.save(employeeToModify);
    }

    private Employee assignManager(Integer manager_id){
        Employee newManager = null;
        if (manager_id != null){
            newManager = findManager(manager_id);
        }
        return newManager;

    }

    private void checkForExistingEmployee(String identificationNumber) {
        Optional<Employee> employee = employeeRepository.findByIdentificationNumber(identificationNumber);
        if (employee.isPresent()){
            throw new ExistingResourceException();
        }
    }

    private Employee findEmployee(Integer id){
        Optional<Employee> employee = employeeRepository.findById(id);
        if (!employee.isPresent()){
            throw new ResourceNotFoundException("El empleado no existe");
        }
        return employee.get();
    }

    private Employee findManager(Integer id){
        Optional<Employee> employee = employeeRepository.findById(id);
        if (!employee.isPresent()){
            throw new InvalidDataException("El mánager que intenta asignarle al empleado, no existe");
        }
        return employee.get();
    }

    private void validateIdentificationType(Integer identificationTypeId) {
        Optional<IdentificationType> identificationType = identificationTypeRepository.findById(identificationTypeId);
        if (!identificationType.isPresent()){
            throw new InvalidDataException("El tipo de identificador no es válido");
        }
    }

    private void validateDepartment(Integer departmentId){
        Optional<Department> department = departmentRepository.findById(departmentId);
        if (!department.isPresent()){
            throw new InvalidDataException("El departamento no es válido");
        }
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
