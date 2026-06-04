package com.erguidos.ichor.entity;

import java.util.List;

import com.erguidos.ichor.dto.request.Gene;
import com.erguidos.ichor.enums.OrganPetitionState;
import com.erguidos.ichor.enums.OrganType;
import com.erguidos.ichor.exceptions.UnouthorizedOrganPetitionStateException;

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
@Table(name = "organ_petitions")
public class OrganPetition {
	private final static String INCORRECT_WEIGHT = "Weight grams must be in range of (10.0, 4000.0)";
	private final static String INCORRECT_VOLUME = "Volume cc must be in range of (10.0, 4000.0)";
	private final static String ORGAN_TYPE_NULL = "Organ type cannot be null";
	private final static String WEIGHT_NULL = "Weight cannot be null";
	private final static String VOLUME_NULL = "Volume cannot be null";
	private final static String HLA_NULL = "Hla cannot be null";
	private final static String DOCTOR_NULL = "Doctor cannot be null";
	private final static String PATIENT_NULL = "Patient cannot be null";
	private final static String ORGAN_NULL = "Compatible organ cannot be null";
	private final static String UNOUTHARIZED_STATE_ACCEPT = "Cannot change to WAITING if is not DRAFT";
	private final static String UNOUTHARIZED_STATE_CANCELL = "Cannot change to CANCELL if is not WAITING or ASSIGNED";
	
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
	
	@Column(name="volume_c_c", nullable = false)
	private Double volumeCC;
	
	@Column(name="hla",  nullable = false, length = 255)
	@ElementCollection
	@CollectionTable(name = "organ_petition_hla", joinColumns = @JoinColumn(name = "id_organ"))
	private List<Gene> hla;
	
	@ManyToOne
	@JoinColumn(name = "id_doctor", nullable = false)
	private Doctor doctor;
	
	// Thinking about create field Hospital patientHospital
	// to control if patient goes to other Hospital and keep history of organ petitions clean and reliable
	@ManyToOne
	@JoinColumn(name = "id_patient", nullable = false)
	private Patient patient;
	
	@Column(name = "organ_petition_state", nullable = false)
	@Enumerated(EnumType.STRING)
	private OrganPetitionState organPetitionState;
	
	@OneToOne
	@JoinColumn(name = "id_organ", nullable = true)
	private Organ organ;
	
	protected OrganPetition() {} 
	
	private OrganPetition(
			OrganType organType,
			Double weightGrams,
			Double volumeCC,
			List<Gene> hla,
			Doctor doctor,
			Patient patient
			) {
		setOrganType(organType);
		setWeightGrams(weightGrams);
		setVolumeCC(volumeCC);
		setHla(hla);
		setDoctor(doctor);
		setPatient(patient);
		this.organPetitionState = OrganPetitionState.DRAFT;
	} 
	
	public static OrganPetition create(
			OrganType organType,
			Double weightGrams,
			Double volumeCC,
			List<Gene> hla,
			Doctor doctor,
			Patient patient
			) {
		
		return new OrganPetition(organType, weightGrams, volumeCC, hla, doctor, patient);
	}
	
	private void setHla(List<Gene> hla) {
		validateNonNull(hla, HLA_NULL);
		
		this.hla = hla; 
	}

	private void setOrganType(OrganType organType) {
		validateNonNull(organType, ORGAN_TYPE_NULL);
		
		this.organType = organType; 
	}
	

	private void setWeightGrams(Double weightGrams) {
		validateNonNull(weightGrams, WEIGHT_NULL);
		
		validateDimension(weightGrams, INCORRECT_WEIGHT);
		
		this.weightGrams = weightGrams;
	}

	private void setVolumeCC(Double volumeCC) {
		validateNonNull(volumeCC, VOLUME_NULL);
		
		validateDimension(volumeCC, INCORRECT_VOLUME);
		
		this.volumeCC = volumeCC;
	}
	
	private void validateDimension(Double dim, String errMsj) {
		if(dim < MIN_DIMENSION || dim > MAX_DIMENSION)
			throw new IllegalArgumentException(errMsj);
	}
	
	private void validateNonNull(Object o, String errMsj) {
		if(o == null) throw new NullPointerException(errMsj);
	}
	
	private void setDoctor(Doctor doctor) {
		validateNonNull(doctor, DOCTOR_NULL);
		this.doctor = doctor;
	}

	private void setPatient(Patient patient) {
		validateNonNull(patient, PATIENT_NULL);
		this.patient = patient;
	}
	
	public void accept() {
		if(this.organPetitionState != OrganPetitionState.DRAFT)
			throw new UnouthorizedOrganPetitionStateException(UNOUTHARIZED_STATE_ACCEPT);
		
		this.organPetitionState = OrganPetitionState.WAITING;
	}
	
	public void cancell() {
		if(this.organPetitionState != OrganPetitionState.WAITING && this.organPetitionState != OrganPetitionState.ASSIGNED_ORGAN )
			throw new UnouthorizedOrganPetitionStateException(UNOUTHARIZED_STATE_CANCELL);
		
		this.organPetitionState = OrganPetitionState.CANCELLED;
	}
	
	public void assignOrgan(Organ compatibleOrgan) {
		validateNonNull(compatibleOrgan, ORGAN_NULL);
		
		this.organ = compatibleOrgan;
	}


	public Long getId() { return this.id; }

	public OrganType getOrganType() { return organType; }

	public Double getWeightGrams() { return weightGrams; }

	public Double getVolumeCC() { return volumeCC; }

	public List<Gene> getHla() { return hla; }

	public Patient getPatient() { return patient; }

	public OrganPetitionState getOrganPetitionState() { return organPetitionState; }

	public Organ getOrgan() { return organ; }

	public Doctor getDoctor() { return doctor; }
}
