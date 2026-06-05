package com.erguidos.ichor.service.organ_petition;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.erguidos.ichor.component.HlaParser;
import com.erguidos.ichor.dto.mappers.OrganPetitionMapper;
import com.erguidos.ichor.dto.request.UpdateOrganPetitionRequest;
import com.erguidos.ichor.dto.request.AuthenticatedRequest;
import com.erguidos.ichor.dto.request.Gene;
import com.erguidos.ichor.dto.request.OrganPetitionRequest;
import com.erguidos.ichor.dto.request.OrganPetitionUpdateRequest;
import com.erguidos.ichor.dto.response.OPDeleted;
import com.erguidos.ichor.dto.response.OrganPetitionResponse;
import com.erguidos.ichor.entity.Doctor;
import com.erguidos.ichor.entity.OrganPetition;
import com.erguidos.ichor.entity.Patient;
import com.erguidos.ichor.enums.OrganPetitionState;
import com.erguidos.ichor.exceptions.OrganPetitionNotFoundException;
import com.erguidos.ichor.exceptions.UserNotFoundException;
import com.erguidos.ichor.repository.DoctorRepository;
import com.erguidos.ichor.repository.OrganPetitionRepository;
import com.erguidos.ichor.repository.PatientRepository;

@Service
public class OrganPetitionService implements OrganPetitionServiceInterface {
	private static String DOCTOR_NOT_FOUND = "Doctor doens't exist";
	private static String PATIENT_NOT_FOUND = "Patient doens't exist";
	private static String ORGAN_PETITION_NOT_FOUND = "Organ petition doens't exist";
	
	private OrganPetitionRepository organPetitionRepository;
	private DoctorRepository doctorRepository;
	private PatientRepository patientRepository;
	
	OrganPetitionService(
			OrganPetitionRepository organPetitionRepository,
			DoctorRepository doctorRepository,
			PatientRepository patientRepository
			) {
		this.organPetitionRepository = organPetitionRepository;
		this.doctorRepository = doctorRepository;
		this.patientRepository = patientRepository;
	}

	@Override
	public OrganPetitionResponse createOrganPetition(AuthenticatedRequest<OrganPetitionRequest> ar) {
		Doctor d = doctorRepository
				.findByUsername(ar.authCredentials().username())
				.orElseThrow(() -> new UserNotFoundException(DOCTOR_NOT_FOUND));
		
		OrganPetitionRequest opr = ar.data();
		
		Patient p = patientRepository
				.findById(opr.idPatient())
				.orElseThrow(() -> new UserNotFoundException(PATIENT_NOT_FOUND));
		
		List<Gene> hla = HlaParser.parse(opr.hla());
		
		OrganPetition op = OrganPetition.create(
				opr.organType(),
				opr.weightGrams(),
				opr.volumeCC(),
				hla,
				d,
				p
				);
		
		organPetitionRepository.save(op);
		
		return OrganPetitionMapper.toOrganPetitionResponse(op);
	}
	
	@Override
	public OrganPetitionResponse updateOrganPetition(AuthenticatedRequest<OrganPetitionUpdateRequest> ar) {
		OrganPetitionUpdateRequest d = ar.data();
		
		Doctor doc = doctorRepository
				.findByUsername(ar.authCredentials().username())
				.orElseThrow(() -> new UserNotFoundException(DOCTOR_NOT_FOUND));
		
		Patient p = patientRepository
				.findById(d.idPatient())
				.orElseThrow(() -> new UserNotFoundException(PATIENT_NOT_FOUND));
		
		OrganPetition op = organPetitionRepository
				.findById(d.idOrganPetition())
				.orElseThrow(() -> new OrganPetitionNotFoundException(ORGAN_PETITION_NOT_FOUND));
		
		List<Gene> hla = HlaParser.parse(d.hla());
		
		op.update(
				d.organType(),
				d.weightGrams(),
				d.volumeCC(),
				hla,
				doc,
				p
				);
		
		organPetitionRepository.save(op);
				
		return OrganPetitionMapper.toOrganPetitionResponse(op);
	}
	
	@Override
	public OPDeleted deleteOrganPetition(AuthenticatedRequest<UpdateOrganPetitionRequest> ar) {
		OrganPetition op = organPetitionRepository
				.findById(ar.data().idOrganPetition())
				.orElseThrow(() -> new OrganPetitionNotFoundException(ORGAN_PETITION_NOT_FOUND));
		
		organPetitionRepository.delete(op);
		
		return new OPDeleted("DELETED");
	}

	@Override
	public OrganPetitionResponse acceptOrganPetition(AuthenticatedRequest<UpdateOrganPetitionRequest> ar) {
		
		OrganPetition op = organPetitionRepository
				.findById(ar.data().idOrganPetition())
				.orElseThrow(() -> new OrganPetitionNotFoundException(ORGAN_PETITION_NOT_FOUND));
		
		// DRAFT -> WAITING
		op.accept();
		
		organPetitionRepository.save(op);
		
		return OrganPetitionMapper.toOrganPetitionResponse(op);
	}

	@Override
	public OrganPetitionResponse cancellOrganPetition(AuthenticatedRequest<UpdateOrganPetitionRequest> ar) {
		OrganPetition op = organPetitionRepository
				.findById(ar.data().idOrganPetition())
				.orElseThrow(() -> new OrganPetitionNotFoundException(ORGAN_PETITION_NOT_FOUND));
		
		// ASSIGNED -> CANCELLED
		op.cancell();
		
		organPetitionRepository.save(op);
		
		return OrganPetitionMapper.toOrganPetitionResponse(op);
	}

	@Override
	public OrganPetitionResponse checkOrganPetition(AuthenticatedRequest<UpdateOrganPetitionRequest> ar) {
		OrganPetition op = organPetitionRepository
				.findById(ar.data().idOrganPetition())
				.orElseThrow(() -> new OrganPetitionNotFoundException(ORGAN_PETITION_NOT_FOUND));
		
		// WAITING -> ASSIGNED
		op.check();
		
		organPetitionRepository.save(op);
		
		return OrganPetitionMapper.toOrganPetitionResponse(op);
	}
	
	@Override
	public Set<OrganPetitionResponse> findByOrganPetitionState(OrganPetitionState organPetitionState) {
		return organPetitionRepository
				.findByOrganPetitionState(organPetitionState)
				.stream()
				.map(OrganPetitionMapper::toOrganPetitionResponse)
				.collect(Collectors.toSet());
	}
}
