package com.erguidos.ichor.dto.request;

import java.util.Objects;

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

	@Override
	public int hashCode() {
		return Objects.hash(allele, letter, protein);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Gene other = (Gene) obj;
		
		return this.letter.equals(other.letter) &&
			   this.allele.equals(other.allele) &&
			   this.protein.equals(other.protein);
	}
	
	
	
}
