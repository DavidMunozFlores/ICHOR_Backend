package com.erguidos.ichor.entity;
 
import com.erguidos.ichor.enums.OrganType;

import jakarta.persistence.Column;
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
	
	/**
	 * Chain must be a six ordered hla markers to match compatibility
	 */
	@Column(name="hla_chain",  nullable = false, length = 255)
	private String hlaChain;
	
	@ManyToOne
	@JoinColumn(name = "id_hospital", nullable = false)
	private Hospital donorHospital;
	
	@ManyToOne
	@JoinColumn(name = "id_coordinator", nullable = false)
	private Coordinator coordinator;
	
	@Column(name="assigned", nullable = false)
	private boolean assigned;
	
	@OneToOne(mappedBy = "organ")
	private OrganPetition organPetition;
	
	protected Organ() {}
    
	public Long getId() { return this.id; }

	public OrganType getOrganType() { return organType; }

	public Double getWeightGrams() { return weightGrams; }

	public Double getVolumeCC() { return volumeCC; }

	public String getHlaChain() { return hlaChain; }

	public Hospital getDonorHospital() { return donorHospital; }

	public boolean isAssigned() { return assigned; }

	public OrganPetition getOrganPetition() { return organPetition; }

	public Coordinator getCoordinator() { return coordinator; }
}