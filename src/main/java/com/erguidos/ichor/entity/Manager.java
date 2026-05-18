package com.erguidos.ichor.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "managers")
public class Manager
       extends User {
    
    protected Manager() {}
}
