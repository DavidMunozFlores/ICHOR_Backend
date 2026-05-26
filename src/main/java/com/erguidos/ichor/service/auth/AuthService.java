package com.erguidos.ichor.service.auth;

import org.springframework.stereotype.Service;

import com.erguidos.ichor.dto.request.AuthCredentialsRequest;
import com.erguidos.ichor.dto.response.IsUserAuthorizedResponse;
import com.erguidos.ichor.entity.Coordinator;
import com.erguidos.ichor.entity.Doctor;
import com.erguidos.ichor.entity.Manager;
import com.erguidos.ichor.entity.User;
import com.erguidos.ichor.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AuthService implements AuthServiceInterface {
	private static final String USER_NOT_EXISTS_MSJ = "That user doesn't exist";
	private static final String PASSWORD_INCORRECT_MSJ = "The password isn't correct";
	private static final String ROL_NOT_EXISTS_MSJ = "The rol doesn't exist";
	
	private static final String DOCTOR = "DOCTOR";
	private static final String MANAGER = "MANAGER";
	private static final String COORDINATOR = "COORDINATOR";
	
	private UserRepository userRepository;
	
	AuthService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public IsUserAuthorizedResponse isAuthorized(AuthCredentialsRequest userRequestDTO) {
		
		User loggedUser = this.userRepository
				.findUserByUsername(userRequestDTO.username())
				.orElseThrow(() -> new EntityNotFoundException(USER_NOT_EXISTS_MSJ));
		
		if(!loggedUser.getPassword().equals(userRequestDTO.password()))
			throw new IllegalArgumentException(PASSWORD_INCORRECT_MSJ);

		
		return new IsUserAuthorizedResponse(rolOfUser(loggedUser));
	}

	
	private String rolOfUser(User loggedUser) {
		if(loggedUser instanceof Doctor) { return DOCTOR; }
		if(loggedUser instanceof Coordinator) { return COORDINATOR; }
		if(loggedUser instanceof Manager) { return MANAGER; }
		
		throw new IllegalArgumentException(ROL_NOT_EXISTS_MSJ);
	}
}
