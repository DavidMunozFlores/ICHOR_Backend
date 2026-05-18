package com.erguidos.ichor.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "doctors")
public class Doctor
       extends WorksInHospital {
    
}
