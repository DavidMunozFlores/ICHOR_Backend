package com.erguidos.ichor.service;

import org.springframework.stereotype.Service;

import com.erguidos.ichor.dto.request.CreateUserRequestDTO;
import com.erguidos.ichor.dto.request.CreateUserRequestDTOV2;
import com.erguidos.ichor.entity.Doctor;
import com.erguidos.ichor.entity.Hospital;
import com.erguidos.ichor.entity.Manager;
import com.erguidos.ichor.entity.User;
import com.erguidos.ichor.exceptions.UserAlreadyExistsException;
import com.erguidos.ichor.exceptions.UserNotFoundException;
import com.erguidos.ichor.repository.DoctorRepository;
import com.erguidos.ichor.repository.HospitalRepository;
import com.erguidos.ichor.repository.ManagerRepository;
import com.erguidos.ichor.repository.UserRepository;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

@Service
public class DoctorServiceImpl implements DoctorService {
	private static final String DOCTOR_EXISTS_MSJ = "The Doctor already exists";
	private static final String HOSPITAL_NOT_EXISTS_MSJ = "The Hospital doesn't exist";
	private static final String USER_NOT_EXISTS_MSJ = "The User doesn't exist";
	private static final String ROL_NOT_CORRECT_MSJ = "That rol cannot create users";
	private static final String PASSWORD_INCORRECT_MSJ = "The password isn't correct";
	
	private DoctorRepository doctorRepository;
	private HospitalRepository hospitalRepository;
	private ManagerRepository managerRepository;
	
	DoctorServiceImpl(DoctorRepository doctorRepository, HospitalRepository hospitalRepository, ManagerRepository managerRepository) {
		this.doctorRepository = doctorRepository;
		this.hospitalRepository = hospitalRepository;
		this.managerRepository = managerRepository;
	}

	@Override
	public void createDoctor(CreateUserRequestDTO doctorRequestDTO) {
		
		if(doctorRepository.existsByUsername(doctorRequestDTO.username()))
			throw new EntityExistsException(DOCTOR_EXISTS_MSJ);
		
		Hospital doctorHospital = hospitalRepository
				.findById(doctorRequestDTO.idHospital())
				.orElseThrow(() -> new EntityNotFoundException(HOSPITAL_NOT_EXISTS_MSJ));
		
		// TODO bycrypt
		doctorRepository.save(
				Doctor.create(doctorRequestDTO.username(), doctorRequestDTO.password(), doctorHospital)
				);
	}
	
	@Override
	public void createDoctorV2(CreateUserRequestDTOV2 createUserRequestDTO) {
		
		isValidManager(createUserRequestDTO);
		
		if(doctorRepository.existsByUsername(createUserRequestDTO.username()))
			throw new UserAlreadyExistsException(DOCTOR_EXISTS_MSJ);
		
		Hospital doctorHospital = hospitalRepository
				.findById(createUserRequestDTO.idHospital())
				.orElseThrow(() -> new EntityNotFoundException(HOSPITAL_NOT_EXISTS_MSJ));
		
		// TODO bycrypt
		doctorRepository.save(
				Doctor.create(createUserRequestDTO.username(), createUserRequestDTO.password(), doctorHospital)
				);
	}

	private void isValidManager(CreateUserRequestDTOV2 createUserRequestDTO) {
		Manager manager = managerRepository
				.findManagerByUsername(createUserRequestDTO.managerData().username())
				.orElseThrow(() -> new UserNotFoundException(USER_NOT_EXISTS_MSJ));
		
		if(!manager.getPassword().equals(createUserRequestDTO.managerData().password()))
			throw new IllegalArgumentException(PASSWORD_INCORRECT_MSJ);
	}
}
