package com.erguidos.ichor.service.doctor;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.erguidos.ichor.component.HashInterface;
import com.erguidos.ichor.dto.mappers.DoctorMapper;
import com.erguidos.ichor.dto.request.CreateWorkerRequest;
import com.erguidos.ichor.dto.response.DoctorResponse;
import com.erguidos.ichor.dto.response.WorkerCreatedResponse;
import com.erguidos.ichor.entity.Doctor;
import com.erguidos.ichor.entity.Hospital;
import com.erguidos.ichor.exceptions.UserAlreadyExistsException;
import com.erguidos.ichor.exceptions.UserNotFoundException;
import com.erguidos.ichor.repository.DoctorRepository;
import com.erguidos.ichor.repository.HospitalRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class DoctorService implements DoctorServiceInterface {
	private static final String DOCTOR_EXISTS_MSJ = "The Doctor already exists";
	private static final String HOSPITAL_NOT_EXISTS_MSJ = "The Hospital doesn't exist";
	private static final String DOCTOR_CREATED_MSJ = "DOCTOR CREATED";
	private static final String DOCTOR_NOT_FOUND_MSJ = "That doctor doesn't exist";

	private DoctorRepository doctorRepository;
	private HospitalRepository hospitalRepository;
	private HashInterface hashComponent;
	
	DoctorService(
			DoctorRepository doctorRepository,
			HospitalRepository hospitalRepository,
			@Qualifier("bcryptPasswordEncoder") HashInterface hashComponent
			) {
		
		this.doctorRepository = doctorRepository;
		this.hospitalRepository = hospitalRepository;
		this.hashComponent = hashComponent;
	}

	@Transactional
	@Override
	public WorkerCreatedResponse createDoctor(CreateWorkerRequest createWorkerRequest) {
		if (doctorRepository.existsByUsername(createWorkerRequest.username()))
			throw new UserAlreadyExistsException(DOCTOR_EXISTS_MSJ);

		Hospital doctorHospital = hospitalRepository.findById(createWorkerRequest.idHospital())
				.orElseThrow(() -> new EntityNotFoundException(HOSPITAL_NOT_EXISTS_MSJ));

		doctorRepository.save(Doctor.create(
		        createWorkerRequest.username(),
				hashComponent.hashPassword(createWorkerRequest.password()),
				doctorHospital));
		
		
		return new WorkerCreatedResponse(DOCTOR_CREATED_MSJ);
	}

	@Override
	public List<DoctorResponse> getAllDoctors() {
		return this.doctorRepository
		.findAll()
		.stream()
		.map(DoctorMapper::toDoctorResponse)
		.toList();
	}

	@Override
	public DoctorResponse getDoctorById(Long id) {
		Doctor doctor = doctorRepository
				.findById(id)
				.orElseThrow(() -> new UserNotFoundException(DOCTOR_NOT_FOUND_MSJ));
				
		return DoctorMapper.toDoctorResponse(doctor);
	}

	@Override
	public List<DoctorResponse> getDoctorsByNameContainingIgnoreCase(String name) {
		return doctorRepository
				.findByUsernameContainingIgnoreCase(name)
				.stream()
				.map(DoctorMapper::toDoctorResponse)
				.toList();
	}
}
