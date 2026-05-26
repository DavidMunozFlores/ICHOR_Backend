package com.erguidos.ichor.service.auth;

import org.springframework.stereotype.Service;

import com.erguidos.ichor.dto.request.AuthCredentialsRequest;
import com.erguidos.ichor.dto.response.IsUserAuthorizedDTO;
import com.erguidos.ichor.entity.User;
import com.erguidos.ichor.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AuthService implements AuthServiceInterface {
    private static final String USER_NOT_EXISTS_MSJ = "That user doesn't exist";
    private static final String PASSWORD_INCORRECT_MSJ = "The password isn't correct";
    
    private UserRepository userRepository;
    
    AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @Override
    public IsUserAuthorizedDTO isAuthorized(AuthCredentialsRequest userRequestDTO) {       
        User loggedUser = this.userRepository
                .findUserByUsername(userRequestDTO.username())
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_EXISTS_MSJ));
        
        if(!loggedUser.getPassword().equals(userRequestDTO.password()))
            throw new IllegalArgumentException(PASSWORD_INCORRECT_MSJ);

        return new IsUserAuthorizedDTO(loggedUser.getRole());
    }
}
