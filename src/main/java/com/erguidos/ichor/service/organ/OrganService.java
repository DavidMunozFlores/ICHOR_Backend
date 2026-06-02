package com.erguidos.ichor.service.organ;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.erguidos.ichor.component.HlaParser;
import com.erguidos.ichor.dto.mappers.OrganMapper;
import com.erguidos.ichor.dto.request.AuthenticatedRequest;
import com.erguidos.ichor.dto.request.Gene;
import com.erguidos.ichor.dto.request.RegisterOrganRequest;
import com.erguidos.ichor.dto.response.OrganBloodTypeInfoResponse;
import com.erguidos.ichor.dto.response.OrganTypeInfoResponse;
import com.erguidos.ichor.dto.response.RegisterOrganResponse;
import com.erguidos.ichor.entity.Coordinator;
import com.erguidos.ichor.entity.Organ;
import com.erguidos.ichor.enums.BloodType;
import com.erguidos.ichor.enums.OrganType;
import com.erguidos.ichor.exceptions.UserNotFoundException;
import com.erguidos.ichor.repository.CoordinatorRepository;
import com.erguidos.ichor.repository.OrganRepository;

@Service
public class OrganService implements OrganServiceInterface {
	private static final String COORD_NOT_FOUND_MSSJ = "That coordinator doesn't exist";
 
	private OrganRepository organRepository;
	private CoordinatorRepository coordinatorRepository;
	
	OrganService(
			OrganRepository organRepository,
			CoordinatorRepository coordinatorRepository
			){
		
		this.organRepository = organRepository;
		this.coordinatorRepository = coordinatorRepository;
	}
	

	@Override
	public List<OrganTypeInfoResponse> getOrganTypeInfo() {
		return Arrays
				.stream(OrganType.values())
				.map(OrganMapper::toOrganTypeInfoResponse)
				.toList();
	}
	
	
	@Override
	public List<OrganBloodTypeInfoResponse> getOrganBloodTypeInfo() {
		return Arrays
				.stream(BloodType.values())
				.map(OrganMapper::toOrganBloodTypeInfoResponse)
				.toList();
	}
	

	@Override
	public RegisterOrganResponse registerOrgan(AuthenticatedRequest<RegisterOrganRequest> ar) {
		
		RegisterOrganRequest ror = ar.data();
		
		Coordinator c = coordinatorRepository
				.findByUsername(ar.authCredentials().username())
				.orElseThrow(() -> new UserNotFoundException(COORD_NOT_FOUND_MSSJ));
		
		List<Gene> hla = HlaParser.parse(ror.hla());
		
		Organ o = Organ.create(
				ror.organType(),
				ror.weightGrams(),
				ror.volumeCC(),
				hla,
				ror.bloodType(),
				c.getHospital(),
				c);
		
		organRepository.save(o);
		
		return OrganMapper.toRregisterOrganResponse(o);
	}

}
