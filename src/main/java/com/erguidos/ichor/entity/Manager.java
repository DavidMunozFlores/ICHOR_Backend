package com.erguidos.ichor.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "managers")
@PrimaryKeyJoinColumn(name = "id_user")
public class Manager extends User {
    
    protected Manager() {}
}
