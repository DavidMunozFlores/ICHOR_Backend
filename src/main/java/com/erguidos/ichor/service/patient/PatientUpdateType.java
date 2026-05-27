package com.erguidos.ichor.service.patient;

import com.erguidos.ichor.dto.response.PatientResponse;

public sealed interface PatientUpdateType {
    public record NotExists() implements PatientUpdateType {}
    public record Failure() implements PatientUpdateType {}
    public record Success(PatientResponse pr) implements PatientUpdateType {}
}
