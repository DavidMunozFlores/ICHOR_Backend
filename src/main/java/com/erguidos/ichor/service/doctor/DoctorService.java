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
import com.erguidos.ichor.entity.Manager;
import com.erguidos.ichor.exceptions.IncorrectPasswordException;
import com.erguidos.ichor.exceptions.UserNotFoundException;
import com.erguidos.ichor.repository.DoctorRepository;
import com.erguidos.ichor.repository.HospitalRepository;
import com.erguidos.ichor.repository.ManagerRepository;
import com.erguidos.ichor.service.key.KeyService;
import com.fasterxml.jackson.core.JsonProcessingException;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

@Service
public class DoctorService implements DoctorServiceInterface {
	private static final String DOCTOR_EXISTS_MSJ = "The Doctor already exists";
	private static final String HOSPITAL_NOT_EXISTS_MSJ = "The Hospital doesn't exist";
	private static final String USER_NOT_EXISTS_MSJ = "The User doesn't exist";
	private static final String PASSWORD_INCORRECT_MSJ = "The password isn't correct";
	private static final String DOCTOR_CREATED_MSJ = "DOCTOR CREATED";

	private DoctorRepository doctorRepository;
	private HospitalRepository hospitalRepository;
	private ManagerRepository managerRepository;
	private KeyService keyService;
	private HashInterface hashComponent;

	DoctorService(
			DoctorRepository doctorRepository,
			HospitalRepository hospitalRepository,
			ManagerRepository managerRepository,
			KeyService keyService,
			@Qualifier("bcryptPasswordEncoder") HashInterface hashComponent
			) {
		
		this.doctorRepository = doctorRepository;
		this.hospitalRepository = hospitalRepository;
		this.managerRepository = managerRepository;
		this.keyService = keyService;
		this.hashComponent = hashComponent;
	}

	@Override
	public WorkerCreatedResponse createDoctor(DecryptRequest decReq)
			throws JsonProcessingException, GeneralSecurityException {

		AuthenticatedRequest autReq =
				keyService.decryptToObject(decReq, AuthenticatedRequest.class);

		Manager manager = managerRepository.findManagerByUsername(autReq.authCredentials().username())
				.orElseThrow(() -> new UserNotFoundException(USER_NOT_EXISTS_MSJ));

		if (!manager.getPassword().equals(autReq.authCredentials().password()))
			throw new IncorrectPasswordException(PASSWORD_INCORRECT_MSJ);

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
