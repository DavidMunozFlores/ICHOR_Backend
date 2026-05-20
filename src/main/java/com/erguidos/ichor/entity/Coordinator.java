package com.erguidos.ichor.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "coordinators")
public class Coordinator extends WorksInHospital {
    
    protected Coordinator() {}
}
