package org.ada.HRmanagement.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "middle_name", nullable = false)
    private String middleName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(nullable = false)
    private LocalDate birthdate;

    @Column(name = "job_name", nullable = false)
    private String jobName;

    @Column(nullable = false)
    private Double salary;

    @Column(name = "identification_number", nullable = false)
    private String identificationNumber;

    @Column(name = "manager_flag", nullable = false)
    private Boolean isManager;

    private Character gender;

    @Column(name = "marital_status")
    private String maritalStatus;

    @Column(name = "department_id", nullable = false)
    private Integer departmentId;

    @Column(name = "identification_type_id", nullable = false)
    private Integer identificationTypeId;

    @Column(nullable = false)
    private boolean isActive;

    @Column(name = "hire_date", nullable = false)
    private LocalDate hireDate;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Absence> absences;

    @OneToOne(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private TalentProfile talentProfile;

    @OneToMany(mappedBy = "manager", fetch = FetchType.LAZY)
    private List<Employee> employees;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "manager_id")
    private Employee manager;


    public Employee() {
    }

    public Employee(String firstName, String middleName, String lastName, LocalDate birthdate, String jobName, Double salary, String identificationNumber, Boolean isManager, Character gender, String maritalStatus, Integer departmentId, Integer identificationTypeId, boolean isActive, LocalDate hireDate) {
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
    }

    public Integer getId() {
        return id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobName() {
        return jobName;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Double getSalary() {
        return salary;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }

    public Employee getManager() {

        return manager;
    }

    public void setIsManager(Boolean manager) {
        isManager = manager;
    }

    public boolean getIsManager() {
        return isManager;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

    public Character getGender() {
        return gender;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setIdentificationTypeId(Integer identificationTypeId) {
        this.identificationTypeId = identificationTypeId;
    }

    public Integer getIdentificationTypeId() {
        return identificationTypeId;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean getActive() {
        return isActive;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public List<Absence> getAbsences() {
        if (absences == null){
            absences = new ArrayList<>();
        }
        return absences;
    }

    public TalentProfile getTalentProfile() {
        return talentProfile;
    }

    public void modifyAttributeValue(String attributeName, Object newValue) {
        switch (attributeName) {
            case "first_name":
                this.firstName = (String) newValue;
                break;
            case "middle_name":
                this.middleName = (String) newValue;
                break;
            case "last_name":
                this.lastName = (String) newValue;
                break;
            case "birthdate":
                this.birthdate = LocalDate.parse((String) newValue, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                break;
            case "job_name":
                this.jobName = (String) newValue;
                break;
            case "salary":
                int newSalary = (Integer) newValue;
                this.salary = (double) (newSalary);
                break;
            case "identification_number":
                this.identificationNumber = (String) newValue;
                break;
            case "isManager":
                this.isManager = (Boolean) newValue;
                break;
            case "gender":
                Character newGender = ((String) newValue).charAt(0);
                this.gender = newGender;
                break;
            case "marital_status":
                this.maritalStatus = (String) newValue;
                break;
            case "department_id":
                this.departmentId = (Integer) newValue;
                break;
            case "identification_type_id":
                this.identificationTypeId = (Integer) newValue;
                break;
            case "isActive":
                this.isActive = (Boolean) newValue;
                break;
            case "hire_date":
                this.hireDate = LocalDate.parse((String) newValue, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                break;


        }
    }
}
