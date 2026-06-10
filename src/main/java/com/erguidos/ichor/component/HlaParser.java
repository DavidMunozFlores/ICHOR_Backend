package com.erguidos.ichor.component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.erguidos.ichor.dto.request.Gene;
import com.erguidos.ichor.enums.GenLetter;

@Component
public class HlaParser {
	private final static int LETTER = 0;
	private final static int ALLELE = 1;
	private final static int PROTEIIN = 2;
	private final static int HLA_SIZE = 6;
	private final static String EMPTY_HLA_ERROR = "Hla cannot be empty";
	private final static String INVALID_HLA_FORMAT = "Hla format is incorrect";
	private final static String HLA_HAS_DUPLICATED_GENES = "Hla cannot have duplicated genes";
	
	private HlaParser() {}
	
	private static void validate(String hlaStr) {
		Set<String> noDuplicatedGenes = new HashSet<>();
		
		if (hlaStr == null || hlaStr.strip().isEmpty())
			throw new IllegalArgumentException(EMPTY_HLA_ERROR);
		

		String hlaRegex = "^(A:\\d{2}:\\d{2} ){2}" +
						  "(B:\\d{2}:\\d{2} ){2}"  +
						  "DRB1:\\d{2}:\\d{2} DRB1:\\d{2}:\\d{2}$";

		if (!hlaStr.matches(hlaRegex)) 
			throw new IllegalArgumentException(INVALID_HLA_FORMAT);
		
		Arrays.stream(getGenes(hlaStr)).forEach(noDuplicatedGenes::add);
		
		if(noDuplicatedGenes.size() != HLA_SIZE)
			throw new IllegalArgumentException(HLA_HAS_DUPLICATED_GENES);
	}
	
	
	
	public static List<Gene> parse(String hlaStr) {
		validate(hlaStr);
				
		return Arrays
				.stream(getGenes(hlaStr))
				.map(HlaParser::getGeneInfo)
				.map(genInf -> Gene.create(GenLetter.valueOf(genInf[LETTER]), genInf[ALLELE], genInf[PROTEIIN]))
				.collect(Collectors.toList());
	}
	
	private static String[] getGenes(String hlaStr) { return hlaStr.split(" "); }
	
	private static String[] getGeneInfo(String gen) { return gen.split(":"); }
}
