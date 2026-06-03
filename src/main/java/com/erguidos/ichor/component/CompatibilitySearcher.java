package com.erguidos.ichor.component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.erguidos.ichor.dto.request.Gene;
import com.erguidos.ichor.entity.Hospital;
import com.erguidos.ichor.entity.Organ;
import com.erguidos.ichor.entity.OrganPetition;
import com.erguidos.ichor.entity.Patient;
import com.erguidos.ichor.enums.BloodType;
import com.erguidos.ichor.enums.OrganPetitionState;
import com.erguidos.ichor.repository.OrganPetitionRepository;
import com.erguidos.ichor.repository.PatientRepository;
import com.erguidos.ichor.service.open_route_api.ORAPIServiceInterface;

@Component
public class CompatibilitySearcher {

	private OrganPetitionRepository opRep;
	private PatientRepository pRep;
	private ORAPIServiceInterface apiRep;
	
	CompatibilitySearcher(
			OrganPetitionRepository opRep,
			PatientRepository pRep,
			ORAPIServiceInterface apiRep
			){
		this.opRep = opRep;
		this.pRep = pRep;
		this.apiRep = apiRep;
	}
	
	public void addOrganToMostCompatibleOP(Organ o) {
		Set<OrganPetition> opWaiting = opRep.findByOrganPetitionState(OrganPetitionState.WAITING);
	
		Set<OrganPetition> opOrganType = opRep.findByOrganType(o.getOrganType());
		
		List<BloodType> compBloodTypes = BloodType.getCompatibleBloodTypes(o.getBloodType());
		
		Set<OrganPetition> opBloodType = opRep.findByCompatibleBlood(compBloodTypes);
		
		Set<OrganPetition> opBase = opWaiting.stream()
									.filter(opOrganType::contains)
									.filter(opBloodType::contains)
									.collect(Collectors.toSet());
		
		Set<OrganPetition> opHlaComp = opBase.stream()
									   .filter((op -> hlaCompatibility(op.getHla(), o.getHla()) > 2))
									   .collect(Collectors.toSet());
		
		Set<OrganPetition> opDimensionsOk = opHlaComp.stream()
											.filter(op -> dimensionsOK(
													op.getWeightGrams(),
													op.getVolumeCC(),
													o.getWeightGrams(),
													o.getVolumeCC()))
											.collect(Collectors.toSet());
		
		List<OrganPetition> opTimeOk = opDimensionsOk.stream()
									  .filter(op -> timeOK(op.getPatient(), o))
									  .toList();
		
		OrganPetition mostOPComp = opTimeOk.get(0);
		
		mostOPComp.assignOrgan(o);
	}
	
	private int hlaCompatibility(List<Gene> receptorHla, List<Gene> donorHla) {
		return (int) receptorHla.stream().filter(donorHla::contains).count();
	}
	
	private boolean dimensionsOK(Double receptorWeight, Double receptorVolume, Double donWeight, Double donVolume) {
		return (Math.abs(receptorWeight - donWeight) < 10) && (Math.abs(receptorVolume - donVolume) < 10);
	}
	
	private boolean timeOK(Patient p, Organ o) {
		Hospital hp = p.getHospital();
		Hospital ho = o.getDonorHospital();
		
		double duration = apiRep.getDurationBetweenTwoHospitalPoints(
												hp.getLongitude(),
												hp.getLatitude(),
												ho.getLongitude(),
												ho.getLatitude());
		
		return o.getOrganType().getTime() > duration;
		
	}
	
	public void addOrganToMostCompatibleOPTest(Organ o) {
		Set<OrganPetition> opWaiting = opRep.findByOrganPetitionState(OrganPetitionState.WAITING);
		List<OrganPetition> r = new ArrayList<>(opWaiting);
		r.get(0).assignOrgan(o);
	}
	

}
