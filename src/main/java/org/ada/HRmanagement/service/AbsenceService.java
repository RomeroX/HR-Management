package org.ada.HRmanagement.service;

import org.ada.HRmanagement.dto.AbsenceDTO;
import org.ada.HRmanagement.entity.Absence;
import org.ada.HRmanagement.entity.Employee;
import org.ada.HRmanagement.exception.ExistingResourceException;
import org.ada.HRmanagement.exception.ResourceNotFoundException;
import org.ada.HRmanagement.repository.AbsenceRepository;
import org.ada.HRmanagement.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AbsenceService {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private final AbsenceRepository absenceRepository;
    private final EmployeeRepository employeeRepository;

    public AbsenceService(AbsenceRepository absenceRepository, EmployeeRepository employeeRepository) {
        this.absenceRepository = absenceRepository;
        this.employeeRepository = employeeRepository;
    }

    public void create (Employee employee, List<AbsenceDTO> absencesDTO){
        List<Absence> absences = absencesDTO.stream()
                .map(absenceDTO -> mapToEntity(absenceDTO, employee))
                .collect(Collectors.toList());
        absenceRepository.saveAll(absences);
    }

    //Es correcto sobrecargar el método? o debería encontrar la forma de enviarle el empleado desde el controlador
    // para reutilizar el create que ya existe?
    public AbsenceDTO create (Integer employeeId, AbsenceDTO absenceDTO ){
        Employee employee = findEmployee(employeeId);
        Absence absence = mapToEntity(absenceDTO, employee);
        checkForExistingAbsence(absence, employee);
        absenceRepository.save(absence);
        absenceDTO.setId(absence.getId());
        return absenceDTO;
    }

    public List<AbsenceDTO> retrieveAll(Integer employeeId) {
        Employee employee = findEmployee(employeeId);
        List<Absence> absences = absenceRepository.findByEmployee(employee);
        return absences.stream()
                .map(absence -> mapToDTO(absence))
                .collect(Collectors.toList());
    }

    public AbsenceDTO retrieveById(Integer employeeId, Integer absenceId) {
        Employee employee = findEmployee(employeeId);
        Optional<Absence> absence = absenceRepository.findByIdAndEmployee(absenceId, employee);
        if (!absence.isPresent()){
            throw new ResourceNotFoundException();
        }
        return mapToDTO(absence.get());
    }

    //Tengo el mismo método en EmployeeService pero no puedo usarlo porque al inyectar la dependencia me genera un error "Circular References"
    private Employee findEmployee(Integer employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        if (employee == null){
            throw new ResourceNotFoundException();
        }
        return employee;
    }

    private void checkForExistingAbsence(Absence absence, Employee employee ) {
        Optional<Absence> optAbsence = absenceRepository.findByEmployeeAndStartDateAndAbsenceTypeId(absence.getEmployee(), absence.getStartDate(), absence.getAbsenceTypeId());
        if (optAbsence.isPresent()) {
            throw new ExistingResourceException();
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
