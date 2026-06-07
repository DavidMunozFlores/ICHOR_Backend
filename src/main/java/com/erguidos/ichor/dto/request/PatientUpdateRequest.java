package com.erguidos.ichor.dto.request;

import com.erguidos.ichor.utils.Validations;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record PatientUpdateRequest(
    @NotNull @PositiveOrZero
    Long   id,
    
    @NotNull @DecimalMin(value = Validations.HEIGHT_MIN_STRING) @DecimalMax(value = Validations.HEIGHT_MAX_STRING)
    Double height,
    
    @NotNull @DecimalMin(value = Validations.WEIGHT_MIN_STRING) @DecimalMax(value = Validations.WEIGHT_MAX_STRING)
    Double weight
) implements DataRequestInterface {}
