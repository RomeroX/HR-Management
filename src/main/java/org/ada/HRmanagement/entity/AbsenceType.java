package org.ada.HRmanagement.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "absence_type")
public class AbsenceType {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String name;

}
