package org.ada.HRmanagement.entity;

import javax.persistence.*;

@Entity
@Table(name = "identification_type")
public class IdentificationType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;


}
