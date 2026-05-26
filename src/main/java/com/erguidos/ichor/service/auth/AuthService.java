package com.erguidos.ichor.service.auth;
 
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.erguidos.ichor.component.HashInterface;
import com.erguidos.ichor.dto.request.AuthCredentialsRequest;
import com.erguidos.ichor.dto.response.IsUserAuthorizedResponse;
import com.erguidos.ichor.entity.User;
import com.erguidos.ichor.exceptions.IncorrectPasswordException;
import com.erguidos.ichor.exceptions.UserNotFoundException;
import com.erguidos.ichor.repository.UserRepository;
 
 
@Service
public class AuthService implements AuthServiceInterface {
    private static final String USER_NOT_EXISTS_MSJ = "That user doesn't exist";
    private static final String PASSWORD_INCORRECT_MSJ = "The password isn't correct";
   
    private UserRepository userRepository;
    private HashInterface hashComponent;
    //TODO Inyect service to transform DcryptRequest dto to AuthCredentialsRequest dto
    
    AuthService(
    		UserRepository userRepository,
    		@Qualifier("bcryptPasswordEncoder") HashInterface hashComponent) {
    	
        this.userRepository = userRepository;
        this.hashComponent = hashComponent;
    }
   
    @Override
    public IsUserAuthorizedResponse isAuthorized(AuthCredentialsRequest userRequestDTO) {      
    	//TODO IsUserAuthorizedResponse with DcryptRequest dto
        User loggedUser = this.userRepository
                .findUserByUsername(userRequestDTO.username())
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_EXISTS_MSJ));
       
        if(!hashComponent.matchPasswords(userRequestDTO.password(), loggedUser.getPassword()))
            throw new IncorrectPasswordException(PASSWORD_INCORRECT_MSJ);
 
        return new IsUserAuthorizedResponse(loggedUser.getRole());
    }
}
 