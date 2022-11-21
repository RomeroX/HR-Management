package org.ada.HRmanagement.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Column(name = "hire_date", nullable = false)
    private LocalDate hireDate;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Absence> absences;

    @OneToOne(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private TalentProfile talentProfile;

    @OneToMany(mappedBy = "manager", fetch = FetchType.LAZY) //Que valor de cascada tengo que usar?
    private List<Employee> employees;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "manager_id")
    private Employee manager;


    public Employee(){
    }

    public Employee(String firstName, String middleName, String lastName, LocalDate birthdate, String jobName, Double salary, String identificationNumber, Boolean isManager, Character gender, String maritalStatus, Integer departmentId, Integer identificationTypeId, boolean isActive, LocalDate hireDate, List<Absence> absences, TalentProfile talentProfile, List<Employee> employees, Employee manager) {
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
        this.absences = absences;
        this.talentProfile = talentProfile;
        this.employees = employees;
        this.manager = manager;
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

    public void setManager(Employee manager) {
        this.manager = manager;
    }

    public Employee getManager() {

        return manager;
    }

    public Boolean getIsManager() {
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

    public boolean getActive() {
        return isActive;
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

    public List<Employee> getEmployees() {
        if (employees == null){
            employees = new ArrayList<>();
        }
        return employees;
    }


}
