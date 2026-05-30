package com.erguidos.ichor.dto.request;

import java.util.List;

import jakarta.persistence.Embeddable;

@Embeddable
public record Hla(
		List<Gene> a,
		List<Gene> b,
		List<Gene> drb1
		) {}
