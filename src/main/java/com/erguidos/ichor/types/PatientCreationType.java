package com.erguidos.ichor.types;

import com.erguidos.ichor.dto.response.PatientResponse;

public sealed interface PatientCreationType {
    public record Created(PatientResponse patientResponse) implements PatientCreationType {}
    public record Exists() implements PatientCreationType {}
    public record NoHospital() implements PatientCreationType {}
    public record BadBlood() implements PatientCreationType {}
    
}
