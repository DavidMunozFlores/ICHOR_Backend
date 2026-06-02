package com.erguidos.ichor.entity;

import java.util.List;
import java.util.Objects;

import com.erguidos.ichor.enums.BloodType;
import com.erguidos.ichor.exceptions.BlankStringException;
import com.erguidos.ichor.exceptions.ImproperHeightException;
import com.erguidos.ichor.exceptions.ImproperWeightException;

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
    private static final Double HEIGHT_MIN = 100.0d;
    private static final Double HEIGHT_MAX = 250.0d;
    private static final Double WEIGHT_MIN =  25.0d;
    private static final Double WEIGHT_MAX = 400.0d;
    
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
    ) throws BlankStringException, ImproperHeightException, ImproperWeightException {
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
    private void setInternalID(String internalID) {
        if (internalID == null | internalID.isBlank()) {
            throw new IllegalArgumentException("Bad internalID: " + internalID);
        }
        this.internalID = internalID.trim();
    }

    public String getName() { return this.name; }
    private void setName(String name) throws BlankStringException {
        if (name == null) {
            throw new NullPointerException("Bad name: " + name);
        }
        if (name.isBlank()) {
            throw new BlankStringException("Bad name: " + name);
        }
        this.name = name.trim();
    }

    public String getIdentification() { return this.identification; }
    private void setIdentification(String identification) throws BlankStringException {
        if (identification == null) {
            throw new NullPointerException("Bad identification: " + identification);
        }
        if (identification.isBlank()) {
            throw new BlankStringException("Bad identification: " + identification);
        }
        this.identification = identification;
    }

    public BloodType getBloodType() { return this.bloodType; }
    private void setBloodType(BloodType bloodType) {
        if (bloodType == null) {
            throw new NullPointerException("Bad bloodType: " + bloodType);
        }
        this.bloodType = bloodType;
    }

    public Double getHeight() { return this.height; }
    private void setHeight(Double height) throws ImproperHeightException {
        if (height == null) {
            throw new NullPointerException("Bad height: " + height);
        }
        if (height < HEIGHT_MIN) {
            throw new ImproperHeightException("Height must be at least " + HEIGHT_MIN);
        }
        if (height > HEIGHT_MAX) {
            throw new ImproperHeightException("Height must be at most " + HEIGHT_MAX);
        }
        this.height = height;
    }

    public Double getWeight() { return this.weight; }
    private void setWeight(Double weight) throws ImproperWeightException {
        if (weight == null) {
            throw new NullPointerException("Bad weight: " + weight);
        }
        if (weight < WEIGHT_MIN) {
            throw new ImproperWeightException("Weight must be at least " + WEIGHT_MIN);
        }
        if (weight > WEIGHT_MAX) {
            throw new ImproperWeightException("Weight must be at most " + WEIGHT_MAX);
        }
        this.weight = weight;
    }
    
    public void updateHeightAndWeight(Double height, Double weight)
            throws ImproperHeightException, ImproperWeightException {
        this.setHeight(height);
        this.setWeight(weight);
    }

    public Hospital getHospital() { return this.hospital; }
    private void setHospital(Hospital hospital) {
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

        public Patient build() throws BlankStringException, ImproperHeightException, ImproperWeightException {
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
