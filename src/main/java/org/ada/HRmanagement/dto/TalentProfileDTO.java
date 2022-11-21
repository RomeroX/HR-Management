package org.ada.HRmanagement.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

public class TalentProfileDTO {

    private Integer id;
    @JsonAlias("professional_experience")
    private String professionalExperience;
    private String skills;
    @JsonAlias("highest_education_level")
    private String highestEducationLevel;

    public TalentProfileDTO(String professionalExperience, String skills, String highestEducationLevel) {
        this.professionalExperience = professionalExperience;
        this.skills = skills;
        this.highestEducationLevel = highestEducationLevel;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProfessionalExperience() {
        return professionalExperience;
    }

    public String getSkills() {
        return skills;
    }

    public String getHighestEducationLevel() {
        return highestEducationLevel;
    }

}
