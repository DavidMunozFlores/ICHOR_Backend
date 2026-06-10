package com.erguidos.ichor.dto.request;

import com.erguidos.ichor.utils.Validations;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;

public record CoordinatorCreateRequest(
    @NotBlank @Pattern(regexp = Validations.USERNAME_PATTERN)
    String username,
    
    @NotBlank @Pattern(regexp = Validations.PASSWORD_PATTERN)
    String password,
    
    @NotNull @PositiveOrZero
    Long idHospital
) implements DataRequestInterface {}
