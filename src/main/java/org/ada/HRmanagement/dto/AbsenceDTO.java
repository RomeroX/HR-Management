package org.ada.HRmanagement.dto;
import com.fasterxml.jackson.annotation.JsonAlias;

import java.time.LocalDate;

public class AbsenceDTO {

    private Integer id;
    @JsonAlias("start_date")
    private String startDate;
    @JsonAlias("end_date")
    private String endDate;
    private String comments;
    @JsonAlias("absence_type_id")
    private Integer absenceTypeId;

    public AbsenceDTO(String startDate, String endDate, String comments, Integer absenceTypeId) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.comments = comments;
        this.absenceTypeId = absenceTypeId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getComments() {
        return comments;
    }

    public Integer getAbsenceTypeId() {
        return absenceTypeId;
    }
}
