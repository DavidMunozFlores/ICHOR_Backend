package com.erguidos.ichor.dto.request;

import com.erguidos.ichor.utils.Validations;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;

public record PatientCreationRequest(
    @NotBlank
    String internalID,
    
    @NotBlank @Pattern(regexp = Validations.USERNAME_PATTERN)
    String name,
    
    @NotBlank
    String identification,
    
    @NotBlank
    String bloodType,

    @NotNull @DecimalMin(value = Validations.HEIGHT_MIN_STRING) @DecimalMax(value = Validations.HEIGHT_MAX_STRING)
    Double height,

    @NotNull @DecimalMin(value = Validations.WEIGHT_MIN_STRING) @DecimalMax(value = Validations.WEIGHT_MAX_STRING)
    Double weight,

    @NotNull @PositiveOrZero
    Long   idHospital
) implements DataRequestInterface {}
