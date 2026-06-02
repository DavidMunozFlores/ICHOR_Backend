package com.erguidos.ichor.entity;
 
import java.util.List;

import com.erguidos.ichor.dto.request.Gene;
import com.erguidos.ichor.enums.BloodType;
import com.erguidos.ichor.enums.OrganType;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
 
@Entity
@Table(name="organs")
public class Organ {
	private final static String INCORRECT_WEIGHT = "Weight grams must be in range of (10.0, 4000.0)";
	private final static String INCORRECT_VOLUME = "Volume cc must be in range of (10.0, 4000.0)";
	private final static String ORGAN_TYPE_NULL = "Organ type cannot be null";
	private final static String BLOOD_TYPE_NULL = "Blood type cannot be null";
	private final static String WEIGHT_NULL = "Weight cannot be null";
	private final static String VOLUME_NULL = "Volume cannot be null";
	private final static String HLA_NULL = "Hla cannot be null";
	private final static String DONOR_HOSPITAL_NULL = "Donor hospital cannot be null";
	private final static String COORDINATOR_NULL = "Coordinator cannot be null";
	
	private final static Double MAX_DIMENSION = 4000.0;
	private final static Double MIN_DIMENSION = 10.0;
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
	@Column(name = "organ_type", nullable = false)
	@Enumerated(EnumType.STRING)
	private OrganType organType;
	
	@Column(name="weight_grams", nullable = false)
	private Double weightGrams;
	
	@Column(name="volume_cc", nullable = false)
	private Double volumeCC;
	
	@Column(name="hla",  nullable = false, length = 255)
	@ElementCollection
	@CollectionTable(name = "organ_hla", joinColumns = @JoinColumn(name = "id_organ"))
	private List<Gene> hla;
	
	@Column(name = "blood_type", nullable = false)
	@Enumerated(EnumType.STRING)
	private BloodType bloodType;
	
	@ManyToOne
	@JoinColumn(name = "id_hospital", nullable = false)
	private Hospital donorHospital;
	
	@ManyToOne
	@JoinColumn(name = "id_coordinator", nullable = false)
	private Coordinator coordinator;
	
	@OneToOne(mappedBy = "organ")
	private OrganPetition organPetition;
	
	protected Organ() {}
	
	private Organ(
			OrganType organType,
			Double weightGrams,
			Double volumeCC,
			List<Gene> hla,
			BloodType bloodType,
			Hospital donorHospital,
			Coordinator coordinator
			) {
		setOrganType(organType);
		setWeightGrams(weightGrams);
		setVolumeCC(volumeCC);
		setHla(hla);
		setBloodType(bloodType);
		setDonorHospital(donorHospital);
		setCoordinator(coordinator);
	}
	
	public static Organ create(
			OrganType organType,
			Double weightGrams,
			Double volumeCC,
			List<Gene> hla,
			BloodType bloodType,
			Hospital donorHospital,
			Coordinator coordinator
			) {
		
		return new Organ(organType, weightGrams, volumeCC, hla, bloodType, donorHospital, coordinator);
	}
	
	public boolean isAssigned() { return this.organPetition != null; }
    
	public Long getId() { return this.id; }

	public OrganType getOrganType() { return organType; }
	
	public BloodType getBloodType() { return bloodType; }

	public Double getWeightGrams() { return weightGrams; }

	public Double getVolumeCC() { return volumeCC; }

	public List<Gene> getHla() { return hla; }


	public Hospital getDonorHospital() { return donorHospital; }

	public OrganPetition getOrganPetition() { return organPetition; }

	public Coordinator getCoordinator() { return coordinator; }
	
	private void setHla(List<Gene> hla) {
		validateNonNull(hla, HLA_NULL);
		
		this.hla = hla; 
	}

	private void setOrganType(OrganType organType) {
		validateNonNull(organType, ORGAN_TYPE_NULL);
		
		this.organType = organType; 
	}
	
	private void setBloodType(BloodType bloodType) {
		validateNonNull(bloodType, BLOOD_TYPE_NULL);
		
		this.bloodType = bloodType; 
	}

	private void setWeightGrams(Double weightGrams) {
		validateNonNull(weightGrams, WEIGHT_NULL);
		
		validateDimension(weightGrams, INCORRECT_WEIGHT);
		
		this.weightGrams = weightGrams;
	}

	private void setVolumeCC(Double volumeCC) {
		validateNonNull(volumeCC, VOLUME_NULL);
		
		validateDimension(weightGrams, INCORRECT_VOLUME);
		
		this.volumeCC = volumeCC;
	}
	
	private void validateDimension(Double dim, String errMsj) {
		if(dim < MIN_DIMENSION || dim > MAX_DIMENSION)
			throw new IllegalArgumentException(errMsj);
	}
	
	private void validateNonNull(Object o, String errMsj) {
		if(o == null) throw new NullPointerException(errMsj);
	}

	private void setDonorHospital(Hospital donorHospital) {
		validateNonNull(donorHospital, DONOR_HOSPITAL_NULL);
		this.donorHospital = donorHospital;
	}

	private void setCoordinator(Coordinator coordinator) {
		validateNonNull(coordinator, COORDINATOR_NULL);
		this.coordinator = coordinator;
	}

	private void setOrganPetition(OrganPetition organPetition) { this.organPetition = organPetition; }
}