package com.erguidos.ichor.entity;

import java.util.List;
import java.util.Objects;

import com.erguidos.ichor.enums.BloodType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "patients")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
	@Column(name = "internal_id", nullable = false, length = 255)
	private String internalID;
	
	@Column(name = "name", nullable = false, length = 255)
	private String name;
	
	@Column(name = "identification", unique = true, nullable = false, length = 255)
	private String identification;
	
	@Column(name = "blood_type", nullable = false)
	@Enumerated(EnumType.STRING)
	private BloodType bloodType;
	
	@Column(name = "height", nullable = false)
	private Double height;
	
	@Column(name = "weight", nullable = false)
	private Double weight;
	
	@ManyToOne
	@JoinColumn(name = "id_hospital")
	private Hospital hospital;
	
	@OneToMany(mappedBy = "patient")
	private List<OrganPetition> organPetitions;
	
	protected Patient() {}
	
    private Patient(
        String internalID,
        String name,
        String identification,
        BloodType bloodType,
        Double height,
        Double weight,
        Hospital hospital
    ) {
        this.setInternalID(internalID);
        this.setName(name);
        this.setIdentification(identification);
        this.setBloodType(bloodType);
        this.setHeight(height);
        this.setWeight(weight);
        this.setHospital(hospital);
    }

    public Long getId() { return this.id; }

    public String getInternalID() { return this.internalID; }
    public void setInternalID(String internalID) {
        Objects.requireNonNull(internalID);
        this.internalID = internalID;
    }

    public String getName() { return this.name; }
    public void setName(String name) {
        Objects.requireNonNull(name);
        if (name.isBlank()) { throw new IllegalArgumentException("Name cannot be empty"); }
        this.name = name.trim();
    }

    public String getIdentification() { return this.identification; }
    public void setIdentification(String identification) {
        Objects.requireNonNull(identification);
        this.identification = identification;
    }

    public BloodType getBloodType() { return this.bloodType; }
    public void setBloodType(BloodType bloodType) {
        Objects.requireNonNull(bloodType);
        this.bloodType = bloodType;
    }

    public Double getHeight() { return this.height; }
    public void setHeight(Double height) {
        Objects.requireNonNull(height);
        if (height <= 0.0d) { throw new IllegalArgumentException("Height must be at least 1"); }
        this.height = height;
    }

    public Double getWeight() { return this.weight; }
    public void setWeight(Double weight) {
        Objects.requireNonNull(weight);
        if (weight <= 0.0d) { throw new IllegalArgumentException("Weight must be at least 1"); }
        this.weight = weight;
    }

    public Hospital getHospital() { return this.hospital; }
    
    public void setHospital(Hospital hospital) {
        Objects.requireNonNull(hospital);
        if (this.hospital != null) { this.hospital.getPatients().remove(this); }
        this.hospital = hospital;
    }

    public List<OrganPetition> getOrganPetitions() { return this.organPetitions; }

    public static PatientBuilder builder() {
        return new PatientBuilder();
    }

    public static final class PatientBuilder {
        private String    internalID;
        private String    name;
        private String    identification;
        private BloodType bloodType;
        private Double    height;
        private Double    weight;
        private Hospital  hospital;

        private PatientBuilder() {
            this.internalID     = null;
            this.name           = null;
            this.identification = null;
            this.bloodType      = null;
            this.height         = null;
            this.weight         = null;
            this.hospital       = null;
        }

        public PatientBuilder setInternalID(String internalID) {
            this.internalID = internalID;
            return this;
        }

        public PatientBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public PatientBuilder setIdentification(String identification) {
            this.identification = identification;
            return this;
        }

        public PatientBuilder setBloodType(BloodType bloodType) {
            this.bloodType = bloodType;
            return this;
        }

        public PatientBuilder setHeight(Double height) {
            this.height = height;
            return this;
        }

        public PatientBuilder setWeight(Double weight) {
            this.weight = weight;
            return this;
        }

        public PatientBuilder setHospital(Hospital hospital) {
            this.hospital = hospital;
            return this;
        }

        public Patient build() {
            return new Patient(
                this.internalID,
                this.name,
                this.identification,
                this.bloodType,
                this.height,
                this.weight,
                this.hospital
            );
        }
    }
}
