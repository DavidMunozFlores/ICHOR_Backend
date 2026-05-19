package com.erguidos.ichor.entity;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "hospitals")
public class Hospital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    
    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "address", nullable = false, length = 255)
    private String address;
    
    /**
     * Longitude defined with a precision of 11 and a scale of 8 to follow the
     * WGS 84 (World Geodetic System 1984) standard.
     * <br>As 180.XXX_XXX_XX
     */
    @Column(name = "longitude", nullable = false, precision = 11, scale = 8)
    private BigDecimal longitude;
    
    /**
     * Latitude defined with a precision of 10 and a scale of 8 to follow the
     * WGS 84 (World Geodetic System 1984) standard.
     * <br>As 90.XXX_XXX_XX
     */
    @Column(name = "latitude", nullable = false, precision = 10, scale = 8)
    private BigDecimal latitude;
    
    @OneToMany(mappedBy = "hopsital")
    private List<WorksInHospital> workers;
    
    @OneToMany(mappedBy = "donorHospital")
    private List<Organ> organs;

    protected Hospital() {}
    
    public Long getId() { return this.id; }
    
    public String getName() { return this.name; }

    public String getAddress() { return this.address; } 
    
    public BigDecimal getLongitude() { return this.longitude; }
    
    public BigDecimal getLatitude() { return this.latitude; }

	public List<WorksInHospital> getWorkers() {
		return workers;
	}

	public List<Organ> getOrgans() {
		return organs;
	}
    
    
}
