package com.erguidos.ichor.dto.request;

import com.erguidos.ichor.enums.GenLetter;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Embeddable
public class Gene {
	
	@Enumerated(EnumType.STRING)
	private GenLetter letter;
	
	private String allele;
	
	private String protein;
	
	protected Gene() {}
	
	private Gene(GenLetter letter, String allele, String protein) {
		this.letter = letter;
		this.allele = allele;
		this.protein = protein;
	}
	
	public static Gene create(GenLetter letter, String allele, String protein) {
		return new Gene(letter, allele, protein);
	}
	
	public GenLetter getLetter() { return letter; }

	public String getAllele() { return allele; }

	public String getProtein() { return protein; }

	@Override
	public String toString() {
		return this.letter + " " + this.allele + " " + this.protein;
	}
}
