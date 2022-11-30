package org.ada.HRmanagement.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import org.ada.HRmanagement.entity.Absence;
import org.ada.HRmanagement.entity.Employee;
import org.ada.HRmanagement.entity.TalentProfile;

import java.util.List;

public class EmployeeDTO {

    private Integer id;
    @JsonAlias("first_name")
    private String firstName;
    @JsonAlias("middle_name")
    private String middleName;
    @JsonAlias("last_name")
    private String lastName;
    private String birthdate;
    @JsonAlias("job_name")
    private String jobName;
    private Double salary;
    @JsonAlias("identification_number")
    private String identificationNumber;
    private boolean isManager;
    private Character gender;
    @JsonAlias("marital_status")
    private String maritalStatus;
    @JsonAlias("department_id")
    private Integer departmentId;
    @JsonAlias("identification_type_id")
    private Integer identificationTypeId;
    private boolean isActive;
    @JsonAlias("hire_date")
    private String hireDate;
    @JsonAlias("absences")
    private List<AbsenceDTO> absencesDTO;
    @JsonAlias("talent_profile")
    private TalentProfileDTO talentProfileDTO;
    @JsonAlias("manager_id")
    private Integer manager;

    public EmployeeDTO(String firstName, String middleName, String lastName, String birthdate, String jobName, Double salary, String identificationNumber, boolean isManager, Character gender, String maritalStatus, Integer departmentId, Integer identificationTypeId, boolean isActive, String hireDate, List<AbsenceDTO> absencesDTO) {
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
        this.hireDate = hireDate;
        this.absencesDTO = absencesDTO;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
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

    public String getBirthdate() {
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

    public boolean getIsManager() {
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

    public boolean getIsActive() {
        return isActive;
    }


    public void setIsActive(boolean isActive) {
        isActive = isActive;
    }

    public String getHireDate() {
        return hireDate;
    }

    public List<AbsenceDTO> getAbsencesDTO () {
        return absencesDTO;
    }

    public void setTalentProfileDTO(TalentProfileDTO talentProfileDTO) {
        this.talentProfileDTO = talentProfileDTO;
    }

    public TalentProfileDTO getTalentProfileDTO() {
        return talentProfileDTO;
    }

    public Integer getManager() {
        return manager;
    }

    public void setManager(Integer manager) {
        this.manager = manager;
    }

}
