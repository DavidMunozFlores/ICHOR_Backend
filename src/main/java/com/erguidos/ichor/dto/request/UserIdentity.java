package com.erguidos.ichor.dto.request;

import com.erguidos.ichor.enums.Role;

public record UserIdentity(
    Long   id,
    String username,
    Role   role
) {}
