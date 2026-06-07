package com.erguidos.ichor.service.auth;
 
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.erguidos.ichor.component.HashInterface;
import com.erguidos.ichor.dto.request.AuthCredentialsRequest;
import com.erguidos.ichor.dto.request.RoleResponse;
import com.erguidos.ichor.dto.request.UserIdentity;
import com.erguidos.ichor.entity.User;
import com.erguidos.ichor.enums.Role;
import com.erguidos.ichor.error.Errors;
import com.erguidos.ichor.repository.UserRepository;
import com.erguidos.ichor.exceptions.IncorrectPasswordException;
import com.erguidos.ichor.exceptions.UserNotFoundException;


@Service
public class AuthService implements AuthServiceInterface {
    private static final String USER_NOT_EXISTS_MSJ = "That user doesn't exist";
    private static final String PASSWORD_INCORRECT_MSJ = "The password isn't correct";
   
    private final UserRepository userRepository;
    private final HashInterface hashing;
   
    AuthService(
        UserRepository userRepository,
        @Qualifier("bcryptPasswordEncoder") HashInterface hashing
    ) {
        this.userRepository = userRepository;
        this.hashing = hashing;
    }
    
    @Override
    public UserIdentity identify(AuthCredentialsRequest credentials) {
        User loggedUser = this.userRepository
                .findUserByUsername(credentials.username())
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_EXISTS_MSJ));
        
        if(! this.hashing.matchPasswords(credentials.password(), loggedUser.getPassword()))
            throw new IncorrectPasswordException(PASSWORD_INCORRECT_MSJ);
        
        return new UserIdentity(
            loggedUser.getId(),
            loggedUser.getUsername(),
            loggedUser.getRole()
        );
    }
    
    @Override
    public RoleResponse getRole(AuthCredentialsRequest credentials) {
        return new RoleResponse(this.identify(credentials).role());
    }
    
    @Override
    public boolean hasValidRole(AuthCredentialsRequest acr, Role roleAllowed) {
        RoleResponse userRole = this.getRole(acr);
        return roleAllowed == userRole.role();
    }

    @Override
    public void validRoleOrThrow(AuthCredentialsRequest acr, Role roleAllowed) {
        if (! this.hasValidRole(acr, roleAllowed)) {
            throw Errors.Auth.UNAUTHORIZED.asException();
        }
    }
}
 