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
    
    @OneToMany(mappedBy = "hospital")
    private List<Doctor> doctors;
    
    @OneToMany(mappedBy = "hospital")
    private List<Coordinator> coordinators;
    
    @OneToMany(mappedBy = "donorHospital")
    private List<Organ> organs;
    
    @OneToMany(mappedBy = "hospital")
    private List<Patient> patients;

    protected Hospital() {}
    
    private Hospital(String name, String address, BigDecimal longitude, BigDecimal latitude) {
        this.setName(name);
        this.setAddress(address);
        this.setLongitude(longitude);
        this.setLatitude(latitude);
    }
    
    public Long getId() { return this.id; }
    
    public String getName() { return this.name; }
    public void setName(String name) {
        assert name != null;
        this.name = name;
    }
    
    public String getAddress() { return this.address; } 
    public void setAddress(String address) {
        assert address != null;
        this.address = address;
    }
    
    public BigDecimal getLongitude() { return this.longitude; }
    public void setLongitude(BigDecimal longitude) {
        assert longitude != null;
        this.longitude = longitude;
    }
    
    public BigDecimal getLatitude() { return this.latitude; }
    public void setLatitude(BigDecimal latitude) {
        assert latitude != null;
        this.latitude = latitude;
    }
    
    public List<Organ> getOrgans() { return this.organs; }
    public void addOrgan(Organ organ) { this.organs.add(organ); }

    public List<Doctor> getDoctors() { return this.doctors; }
    public void addDoctor(Doctor doctor) { this.doctors.add(doctor); }

    public List<Coordinator> getCoordinators() { return this.coordinators; }
    public void addCoordinator(Coordinator coordinator) { this.coordinators.add(coordinator); }

    public List<Patient> getPatients() { return this.patients; }
    public void addPatient(Patient patient) { this.patients.add(patient); }
    
    public static HospitalBuilder builder() {
        return new HospitalBuilder();
    }
    
    public static HospitalBuilder builder(Hospital hospital) {
        return new HospitalBuilder(hospital);
    }
    
    public static class HospitalBuilder {
        private String name = null;
        private String address = null;
        private BigDecimal longitude = null;
        private BigDecimal latitude = null;
        
        private HospitalBuilder() {}
        
        private HospitalBuilder(Hospital hospital) {
            this.name = hospital.name;
            this.address = hospital.address;
            this.longitude = hospital.longitude;
            this.latitude = hospital.latitude;
        }
        
        public HospitalBuilder setName(String name) {
            this.name = name;
            return this;
        }
        public HospitalBuilder setAddress(String address) {
            this.address = address;
            return this;
        }
        public HospitalBuilder setLongitude(BigDecimal longitude) {
            this.longitude = longitude;
            return this;
        }
        public HospitalBuilder setLatitude(BigDecimal latitude) {
            this.latitude = latitude;
            return this;
        }
        
        public Hospital build() {
            return new Hospital(
                this.name,
                this.address,
                this.longitude,
                this.latitude
            );
        }
    }
}
