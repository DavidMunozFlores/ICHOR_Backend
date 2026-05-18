package com.erguidos.ichor.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user", nullable = false, columnDefinition = "BIGINT")
    private Long id;
    
    @Column(name = "username", nullable = false, columnDefinition = "VARCHAR(255)")
    private String username;
    
    @Column(name = "password", nullable = false, columnDefinition = "VARCHAR(255)")
    private String password;
}
