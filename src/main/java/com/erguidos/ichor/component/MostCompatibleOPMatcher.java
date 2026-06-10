package com.erguidos.ichor.component;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.erguidos.ichor.dto.request.Gene;
import com.erguidos.ichor.entity.Hospital;
import com.erguidos.ichor.entity.Organ;
import com.erguidos.ichor.entity.OrganPetition;
import com.erguidos.ichor.enums.BloodType;
import com.erguidos.ichor.enums.OrganPetitionState;
import com.erguidos.ichor.repository.HospitalRepository;
import com.erguidos.ichor.repository.OrganPetitionRepository;
import com.erguidos.ichor.repository.OrganRepository;
import com.erguidos.ichor.service.open_route_api.ORAPIServiceInterface;

@Component
public class MostCompatibleOPMatcher {
	private static final Double RANGE = 10.0;
	private static final int MIN_GENE = 2;

	private OrganPetitionRepository opRep;
	private OrganRepository organRep;
	private ORAPIServiceInterface apiRep;
	private HospitalRepository hospRep;
	
	
	 MostCompatibleOPMatcher(
			OrganPetitionRepository opRep,
			OrganRepository organRep,
			ORAPIServiceInterface apiRep,
			HospitalRepository hospRep
			){
		this.opRep = opRep;
		this.organRep = organRep;
		this.apiRep = apiRep;
		this.hospRep = hospRep;
	}
	
	
	public boolean addOrganToMostCompatibleOP(Organ o) {
		List<Hospital> compatibleHospitals = getCompatibleHospitals(o);
		
		List<BloodType> compatibleBloodTypes = BloodType.getCompatibleBloodTypes(o.getBloodType());
		
		Set<OrganPetition> compatibleOPs = opRep.findCompatibleOPs(
																   OrganPetitionState.WAITING,
																   o.getOrganType(),
																   compatibleBloodTypes,
																   compatibleHospitals,
																   o.getWeightGrams(),
																   o.getVolumeCC(),
																   RANGE
																   );
		
		List<OrganPetition> mostCompatibleOPs = compatibleOPs
											    .stream()
												.filter(op -> (sameGenesNumber(op.getHla(), o.getHla())) > MIN_GENE)
												.toList();
		
		if(mostCompatibleOPs == null || mostCompatibleOPs.isEmpty()) return false;
		
		
		mostCompatibleOPs.stream()
		.sorted(Comparator.comparingLong(op -> sameGenesNumber(op.getHla(), o.getHla())))
		.toList()
		.getLast()
		.assignOrgan(o);
		
		return true;
	}
	
	
	public void matchOrgansWithOP() {
		organRep.findByOrganPetitionIsNull()
				.stream()
				.filter(this::addOrganToMostCompatibleOP)
				.forEach(organRep::saveAndFlush);
	}
	
	
	private List<Hospital> getCompatibleHospitals(Organ o) {
		Hospital oh = o.getDonorHospital();
		BigDecimal ohLo = oh.getLongitude();
		BigDecimal ohLa = oh.getLatitude();
		
		double organTimeSeconds = (double) o.getOrganType().getTime() * 3600;
		
		return hospRep.findAll().stream()
		.filter(h -> (apiRep.getDurationBetweenTwoHospitalPoints(ohLo, ohLa, h.getLongitude(), h.getLatitude()) < organTimeSeconds))
		.toList();
	}
	
	private long sameGenesNumber(List<Gene> receptorHla, List<Gene> donorHla) {
		return  receptorHla.stream().filter(donorHla::contains).count();
	}
}
