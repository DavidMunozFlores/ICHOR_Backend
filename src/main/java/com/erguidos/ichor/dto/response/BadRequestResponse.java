package com.erguidos.ichor.dto.response;

import com.erguidos.ichor.enums.BadRequest;

import io.swagger.v3.oas.annotations.media.Schema;

public record BadRequestResponse(
    BadRequest reason
) {}
