package org.ada.HRmanagement.entity;

import javax.persistence.*;

@Entity
@Table(name = "talent_profile")
public class TalentProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "professional_experience")
    private Long professionalExperience;

    private Long skills;

    @Column(name = "highest_education_level", nullable = false)
    private String highestEducationLevel;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    public TalentProfile() {
    }

    public TalentProfile(Long professionalExperience, Long skills, String highestEducationLevel, Employee employee) {
        this.professionalExperience = professionalExperience;
        this.skills = skills;
        this.highestEducationLevel = highestEducationLevel;
        this.employee = employee;
    }

    public Integer getId() {
        return id;
    }

    public Long getProfessionalExperience() {
        return professionalExperience;
    }

    public Long getSkills() {
        return skills;
    }

    public String getHighestEducationLevel() {
        return highestEducationLevel;
    }

    public Employee getEmployee() {
        return employee;
    }
}
