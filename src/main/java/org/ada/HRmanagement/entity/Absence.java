package org.ada.HRmanagement.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "absence")
public class Absence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    private String comments;

    @Column(name = "absence_type_id", nullable = false)
    private Integer absenceTypeId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    public Absence(){
    }

    public Absence(LocalDate startDate, LocalDate endDate, String comments, Integer absenceTypeId, Employee employee) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.comments = comments;
        this.absenceTypeId = absenceTypeId;
        this.employee = employee;
    }

    public Integer getId() {
        return id;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getComments() {
        return comments;
    }

    public void setAbsenceTypeId(Integer absenceTypeId) {
        this.absenceTypeId = absenceTypeId;
    }

    public Integer getAbsenceTypeId() {
        return absenceTypeId;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void modifyAttributeValue(String attributeName, Object newValue) {
        switch (attributeName){
            case ("start_date"):
                this.startDate = LocalDate.parse((String) newValue, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                break;
            case ("end_date"):
                this.endDate = LocalDate.parse((String) newValue, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                break;
            case ("comments"):
                this.comments = (String) newValue;
                break;
            case("absence_type_id"):
                this.absenceTypeId = (Integer) newValue;
                break;
        }
    }
}
