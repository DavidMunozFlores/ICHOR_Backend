package com.erguidos.ichor.dto.types;

import com.erguidos.ichor.entity.Patient;
import com.erguidos.ichor.enums.BadRequest;

public sealed interface PatientCreationType
                permits PatientCreationType.Created,
                        PatientCreationType.Exists,
                        PatientCreationType.Failure {
    public record Created(Patient patient) implements PatientCreationType {}
    public record Exists() implements PatientCreationType {}
    public record Failure(BadRequest badRequest) implements PatientCreationType {}
}
