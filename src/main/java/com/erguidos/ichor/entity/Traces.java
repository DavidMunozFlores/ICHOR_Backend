package com.erguidos.ichor.entity;

import com.erguidos.ichor.enums.TraceType;

import jakarta.persistence.Column;

public class Traces extends BaseEntity {
	// TODO
	@Column(name = "trace_type")
	private TraceType traceType;
	
	protected Traces() {}

	public TraceType getTraceType() {
		return traceType;
	}
	
	
}
