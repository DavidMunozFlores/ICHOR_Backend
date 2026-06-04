package com.erguidos.ichor.service.auth;

import com.erguidos.ichor.dto.request.AuthCredentialsRequest;
import com.erguidos.ichor.dto.request.RoleResponse;
import com.erguidos.ichor.dto.request.UserIdentity;
import com.erguidos.ichor.enums.Role;

public interface AuthServiceInterface {
    public UserIdentity identify(AuthCredentialsRequest credentials);
    
    public RoleResponse getRole(AuthCredentialsRequest credentials);
    
    public boolean hasValidRole(AuthCredentialsRequest acr, Role roleAllowed);
    
    public void validRoleOrThrow(AuthCredentialsRequest acr, Role roleAllowed);
}
