package org.ada.HRmanagement.dto;
import com.fasterxml.jackson.annotation.JsonAlias;

import java.time.LocalDate;

public class AbsenceDTO {

    private Integer id;
    @JsonAlias("start_date")
    private LocalDate startDate;
    @JsonAlias("end_date")
    private LocalDate endDate;
    private Long comments;
    @JsonAlias("absence_type_id")
    private Integer absenceTypeId;

    public AbsenceDTO(LocalDate startDate, LocalDate endDate, Long comments, Integer absenceTypeId) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.comments = comments;
        this.absenceTypeId = absenceTypeId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Long getComments() {
        return comments;
    }

    public Integer getAbsenceTypeId() {
        return absenceTypeId;
    }
}
