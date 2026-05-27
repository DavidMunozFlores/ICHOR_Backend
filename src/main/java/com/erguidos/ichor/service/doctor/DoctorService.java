package com.erguidos.ichor.service.doctor;

import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.erguidos.ichor.component.HashInterface;
import com.erguidos.ichor.dto.request.AuthenticatedRequest;
import com.erguidos.ichor.dto.request.DecryptRequest;
import com.erguidos.ichor.dto.response.WorkerCreatedResponse;
import com.erguidos.ichor.entity.Doctor;
import com.erguidos.ichor.entity.Hospital;
import com.erguidos.ichor.exceptions.NotAuthorizedExecption;
import com.erguidos.ichor.repository.DoctorRepository;
import com.erguidos.ichor.repository.HospitalRepository;
import com.erguidos.ichor.service.Role;
import com.erguidos.ichor.service.auth.AuthServiceInterface;
import com.erguidos.ichor.service.key.KeyService;
import com.fasterxml.jackson.core.JsonProcessingException;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

@Service
public class DoctorService implements DoctorServiceInterface {
	private static final String DOCTOR_EXISTS_MSJ = "The Doctor already exists";
	private static final String HOSPITAL_NOT_EXISTS_MSJ = "The Hospital doesn't exist";
	private static final String DOCTOR_CREATED_MSJ = "DOCTOR CREATED";
	private static final String NOT_AUTHORIZED_MSJ = "This role is not authorized";

	private DoctorRepository doctorRepository;
	private HospitalRepository hospitalRepository;
	private KeyService keyService;
	private HashInterface hashComponent;
	private AuthServiceInterface authService;
	
	DoctorService(
			DoctorRepository doctorRepository,
			HospitalRepository hospitalRepository,
			KeyService keyService,
			@Qualifier("bcryptPasswordEncoder") HashInterface hashComponent,
			AuthServiceInterface authService
			) {
		
		this.doctorRepository = doctorRepository;
		this.hospitalRepository = hospitalRepository;
		this.keyService = keyService;
		this.hashComponent = hashComponent;
		this.authService = authService;
	}

	@Override
	public WorkerCreatedResponse createDoctor(DecryptRequest decReq)
			throws JsonProcessingException, GeneralSecurityException {

		AuthenticatedRequest autReq =
				keyService.decryptToObject(decReq, AuthenticatedRequest.class);
		
		Role authCredentialsRole = authService.isAuthorized(autReq.authCredentials()).role();
		
		if(authCredentialsRole != Role.MANAGER)
			throw new NotAuthorizedExecption(NOT_AUTHORIZED_MSJ);


		if (doctorRepository.existsByUsername(autReq.data().username()))
			throw new EntityExistsException(DOCTOR_EXISTS_MSJ);

		Hospital doctorHospital = hospitalRepository.findById(autReq.data().idHospital())
				.orElseThrow(() -> new EntityNotFoundException(HOSPITAL_NOT_EXISTS_MSJ));

		doctorRepository.save(Doctor.create(
				autReq.data().username(),
				hashComponent.hashPassword(autReq.data().password()),
				doctorHospital));
		
		
		return new WorkerCreatedResponse(DOCTOR_CREATED_MSJ);
	}
}
