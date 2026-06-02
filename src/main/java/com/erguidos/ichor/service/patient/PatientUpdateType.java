package com.erguidos.ichor.service.patient;

import com.erguidos.ichor.entity.Patient;
import com.erguidos.ichor.enums.BadRequest;

public sealed interface PatientUpdateType {
    public record Failure(BadRequest badRequest) implements PatientUpdateType {}
    public record Success(Patient patient) implements PatientUpdateType {}
}
