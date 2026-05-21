package com.erguidos.ichor.service;

import org.springframework.stereotype.Service;

import com.erguidos.ichor.dto.request.DoctorRequestDTO;
import com.erguidos.ichor.entity.Doctor;
import com.erguidos.ichor.entity.Hospital;
import com.erguidos.ichor.repository.DoctorRepository;
import com.erguidos.ichor.repository.HospitalRepository;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

@Service
public class DoctorServiceImpl implements DoctorService {
	private static final String DOCTOR_EXISTS_MSJ = "The Doctor already exists";
	private static final String HOSPITAL_NOT_EXISTS_MSJ = "The Hospital doesn't exist";
	
	private DoctorRepository doctorRepository;
	private HospitalRepository hospitalRepository;
	
	DoctorServiceImpl(DoctorRepository doctorRepository, HospitalRepository hospitalRepository) {
		this.doctorRepository = doctorRepository;
		this.hospitalRepository = hospitalRepository;
	}

	@Override
	public void createDoctor(DoctorRequestDTO doctorRequestDTO) {
		
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
}
