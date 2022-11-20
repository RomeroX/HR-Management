package org.ada.HRmanagement.dto;

public class TalentProfileDTO {

    private Integer id;
    private Long professionalExperience;
    private Long skills;
    private String highestEducationLevel;

    public TalentProfileDTO(Long professionalExperience, Long skills, String highestEducationLevel) {
        this.professionalExperience = professionalExperience;
        this.skills = skills;
        this.highestEducationLevel = highestEducationLevel;
    }

    public void setId(Integer id) {
        this.id = id;
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

}
