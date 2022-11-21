package org.ada.HRmanagement.service;

import org.ada.HRmanagement.dto.EmployeeDTO;
import org.ada.HRmanagement.entity.Employee;
import org.ada.HRmanagement.exception.ExistingResourceException;
import org.ada.HRmanagement.exception.ResourceNotFoundException;
import org.ada.HRmanagement.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
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

    public EmployeeDTO create (EmployeeDTO employeeDTO){
        Employee employee = mapToEntity(employeeDTO);
        checkForExistingEmployee(employee.getIdentificationNumber());

        if (employeeDTO.getManager() != null){
            employee.setManager(findEmployee(employeeDTO.getManager()));
        }
        employee = employeeRepository.save(employee);

        if (!CollectionUtils.isEmpty(employeeDTO.getAbsencesDTO())){
            absenceService.create(employee, employeeDTO.getAbsencesDTO());
        }

        if (employeeDTO.getTalentProfileDTO() != null){
            talentProfileService.create(employee, employeeDTO.getTalentProfileDTO());
        }

        employeeDTO.setId(employee.getId());
        return employeeDTO;
    }

    private void checkForExistingEmployee(String identificationNumber) {
        Optional<Employee> employee = employeeRepository.findByIdentificationNumber(identificationNumber);
        if (employee.isPresent()){
            throw new ExistingResourceException();
        }
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

    //Es correcto usar ResourceNotFoundException cuando el manager que se quiere asignar no existe?
    // O conviene crear otra excepción nueva?
    public Employee findEmployee(Integer id){
        Employee employee = employeeRepository.findById(id).orElse(null);
        if (employee == null){
            throw new ResourceNotFoundException();
        }
        return employee;

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
                absenceService.mapToDTOS(employee.getAbsences()),
                talentProfileService.mapToDTO(employee.getTalentProfile()));
        employeeDTO.setId(employee.getId());
        if (employee.getManager() != null){
            employeeDTO.setManager(employee.getManager().getId());
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
                LocalDate.parse(employeeDTO.getHireDate(), DATE_TIME_FORMATTER),
                null,
                null,
                null,
                null);

        return employee;
    }


}
