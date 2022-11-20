package org.ada.HRmanagement.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import org.ada.HRmanagement.entity.Absence;
import org.ada.HRmanagement.entity.Employee;
import org.ada.HRmanagement.entity.TalentProfile;

import java.time.LocalDate;
import java.util.List;

public class EmployeeDTO {

    private Integer id;
    @JsonAlias("first_name")
    private String firstName;
    @JsonAlias("middle_name")
    private String middleName;
    @JsonAlias("last_name")
    private String lastName;
    private LocalDate birthdate;
    @JsonAlias("job_name")
    private String jobName;
    private Double salary;
    @JsonAlias("identification_number")
    private String identificationNumber;
    @JsonAlias("is_manager")
    private Boolean isManager;
    private Character gender;
    @JsonAlias("marital_status")
    private String maritalStatus;
    @JsonAlias("department_id")
    private Integer departmentId;
    @JsonAlias("identification_type_id")
    private Integer identificationTypeId;
    @JsonAlias("is_active")
    private Boolean isActive;
    private List<Absence> absences;
    @JsonAlias("talent_profile")
    private TalentProfile talentProfile;
    @JsonAlias("employee_manager")
    private Employee employeeManager;

    public EmployeeDTO(String firstName, String middleName, String lastName, LocalDate birthdate, String jobName, Double salary, String identificationNumber, Boolean isManager, Character gender, String maritalStatus, Integer departmentId, Integer identificationTypeId, Boolean isActive, List<Absence> absences, TalentProfile talentProfile, List<Employee> employees, Employee employeeManager) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.jobName = jobName;
        this.salary = salary;
        this.identificationNumber = identificationNumber;
        this.isManager = isManager;
        this.gender = gender;
        this.maritalStatus = maritalStatus;
        this.departmentId = departmentId;
        this.identificationTypeId = identificationTypeId;
        this.isActive = isActive;
        this.absences = absences;
        this.talentProfile = talentProfile;
        this.employeeManager = employeeManager;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public String getJobName() {
        return jobName;
    }

    public Double getSalary() {
        return salary;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public Boolean getManager() {
        return isManager;
    }

    public Character getGender() {
        return gender;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public Integer getIdentificationTypeId() {
        return identificationTypeId;
    }

    public Boolean getActive() {
        return isActive;
    }

    public List<Absence> getAbsences() {
        return absences;
    }

    public TalentProfile getTalentProfile() {
        return talentProfile;
    }

    public Employee getEmployeeManager() {
        return employeeManager;
    }
}
