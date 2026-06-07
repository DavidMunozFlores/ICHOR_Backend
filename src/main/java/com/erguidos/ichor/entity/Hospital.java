package com.erguidos.ichor.entity;

import java.math.BigDecimal;
import java.util.List;

import com.erguidos.ichor.error.Errors;

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
    private static final BigDecimal LONGITUDE_MAX = BigDecimal.valueOf( 180);
    private static final BigDecimal LONGITUDE_MIN = BigDecimal.valueOf(-180);
    private static final BigDecimal LATITUDE_MAX  = BigDecimal.valueOf(  90);
    private static final BigDecimal LATITUDE_MIN  = BigDecimal.valueOf( -90);
    
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
    private void setName(String name) {
        if (name == null) {
            throw Errors.Hospital.NULL_NAME.asException();
        }
        if (name.isBlank()) {
            throw Errors.Hospital.BLANK_NAME.asException();
        }
        this.name = name.trim();
    }
    
    public String getAddress() { return this.address; }
    private void setAddress(String address) {
        if (address == null) {
            throw Errors.Hospital.NULL_ADDRESS.asException();
        }
        if (address.isBlank()) {
            throw Errors.Hospital.BLANK_ADDRESS.asException();
        }
        this.address = address.trim();
    }
    
    public BigDecimal getLongitude() { return this.longitude; }
    private void setLongitude(BigDecimal longitude) {
        if (longitude == null) {
            throw Errors.Hospital.NULL_LONGITUDE.asException();
        }
        this.longitude = this.wrapLongitudeAroundGlobe(longitude);
    }
    private BigDecimal wrapLongitudeAroundGlobe(final BigDecimal longitude) {
        BigDecimal transformed = longitude;
        while(transformed.compareTo(LONGITUDE_MAX) > 0) {
            transformed.subtract(LONGITUDE_MAX);
        }
        while(transformed.compareTo(LONGITUDE_MIN) < 0) {
            transformed.subtract(LONGITUDE_MIN);
        }
        return transformed;
    }
    
    public BigDecimal getLatitude() { return this.latitude; }
    private void setLatitude(BigDecimal latitude) {
        if (latitude == null) {
            throw Errors.Hospital.NULL_LATITUDE.asException();
        }
        if (latitude.compareTo(LATITUDE_MAX) > 0) {
            throw Errors.Hospital.OUT_OF_BOUNDS_LATITUDE.asException();
        }
        if (latitude.compareTo(LATITUDE_MIN) < 0) {
            throw Errors.Hospital.OUT_OF_BOUNDS_LATITUDE.asException();
        }
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
        private String     name      = null;
        private String     address   = null;
        private BigDecimal longitude = null;
        private BigDecimal latitude  = null;
        
        private HospitalBuilder() {}
        
        private HospitalBuilder(Hospital hospital) {
            this.name      = hospital.name;
            this.address   = hospital.address;
            this.longitude = hospital.longitude;
            this.latitude  = hospital.latitude;
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
    
	@Override
	public String toString() {
		return "name: " + name ;
	}
}
