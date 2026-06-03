package com.erguidos.ichor.dto.response;

import com.erguidos.ichor.enums.Role;

public record WorkerResponse(
    String name,
    String hospital,
    Role role
) {}
