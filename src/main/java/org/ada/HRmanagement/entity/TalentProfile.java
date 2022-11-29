package org.ada.HRmanagement.entity;

import javax.persistence.*;

@Entity
@Table(name = "talent_profile")
public class TalentProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "professional_experience")
    private String professionalExperience;

    private String skills;

    @Column(name = "highest_education_level", nullable = false)
    private String highestEducationLevel;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    public TalentProfile() {
    }

    public TalentProfile(String professionalExperience, String skills, String highestEducationLevel, Employee employee) {
        this.professionalExperience = professionalExperience;
        this.skills = skills;
        this.highestEducationLevel = highestEducationLevel;
        this.employee = employee;
    }

    public Integer getId() {
        return id;
    }

    public void setProfessionalExperience(String professionalExperience) {
        this.professionalExperience = professionalExperience;
    }

    public String getProfessionalExperience() {
        return professionalExperience;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getSkills() {
        return skills;
    }

    public void setHighestEducationLevel(String highestEducationLevel) {
        this.highestEducationLevel = highestEducationLevel;
    }

    public String getHighestEducationLevel() {
        return highestEducationLevel;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void modifyAttributeValue(String attributeName, Object newValue) {
        switch (attributeName){
            case("professional_experience"):
                this.professionalExperience = (String) newValue;
                break;
            case("skills"):
                this.skills = (String) newValue;
                break;
            case("highest_education_level"):
                this.highestEducationLevel = (String) newValue;
                break;
        }
    }
}
