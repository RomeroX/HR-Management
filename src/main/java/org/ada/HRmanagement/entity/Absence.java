package org.ada.HRmanagement.entity;

import javax.persistence.*;
import java.time.LocalDate;

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

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getComments() {
        return comments;
    }

    public Integer getAbsenceTypeId() {
        return absenceTypeId;
    }

    public Employee getEmployee() {
        return employee;
    }
}
