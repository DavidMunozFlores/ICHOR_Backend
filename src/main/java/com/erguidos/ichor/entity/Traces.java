package com.erguidos.ichor.entity;

import com.erguidos.ichor.enums.TraceType;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Traces {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    
	// TODO
	@Column(name = "trace_type")
	@Enumerated(EnumType.STRING)
	private TraceType traceType;
	
	protected Traces() {}

	public TraceType getTraceType() {
		return traceType;
	}
    
    public Long getId() { return this.id; }
}
