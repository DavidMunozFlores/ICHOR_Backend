package com.erguidos.ichor.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "hospitals")
public final class Hospital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_hospital", nullable = false, columnDefinition = "BIGINT")
    private Long id;
    
    @Column(name = "name", nullable = false, columnDefinition = "VARCHAR(255)")
    private String name;

    @Column(name = "address", nullable = false, columnDefinition = "VARCHAR(255)")
    private String address;

    @Column(name = "longitude", nullable = false, columnDefinition = "DECIMAL(10,8)")
    private Double longitude;
    
    @Column(name = "latitude", nullable = false, columnDefinition = "DECIMAL(10,8)")
    private Double latitude;
}
