package com.erguidos.ichor.entity;

import java.time.LocalDateTime;

import com.erguidos.ichor.enums.TraceType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Unidirectional relationship between Trace and [Organ, Patient, OrganPetition], not need OnetoMany mark
 */
@Entity
@Table(name = "traces")
public class Trace {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 /*   
	@Column(name = "trace_type", nullable = false)
	@Enumerated(EnumType.STRING)
	private TraceType traceType;
	
	@Column(name = "time_action_occurs", nullable = false)
	private LocalDateTime timeActionOccurs;
	
	@Column(name = "id_annotating_user", nullable = false)
	private User annotatingUser;
	
	@ManyToOne
	@JoinColumn(name = "id_noted_user", nullable = true)
	private User notedUser;
	
	@ManyToOne
	@JoinColumn(name = "id_noted_patient", nullable = true)
	private Patient notedPatient;
	
	@ManyToOne
	@JoinColumn(name = "id_noted_organ", nullable = true)
	private Organ notedOrgan;
	
	@ManyToOne
	@JoinColumn(name = "id_noted_organPetition", nullable = true)
	private OrganPetition notedOrganPetition;
	
	protected Trace() {}

	public TraceType getTraceType() { return traceType; }
    
    public Long getId() { return this.id; }

	public LocalDateTime getTimeActionOccurs() { return timeActionOccurs; }

	public User getAnnotatingUser() { return annotatingUser; }

	public User getNotedUser() { return notedUser; }

	public Patient getNotedPatient() { return notedPatient; }

	public Organ getNotedOrgan() { return notedOrgan; }

	public OrganPetition getNotedOrganPetition() { return notedOrganPetition; }
	*/
}
