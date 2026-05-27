package com.erguidos.ichor.types;

import com.erguidos.ichor.entity.Coordinator;

public sealed interface CoordinatorCreationType
                permits CoordinatorCreationType.Created,
                        CoordinatorCreationType.Exists,
                        CoordinatorCreationType.NoHospital {
    public record Created(Coordinator coordinator) implements CoordinatorCreationType {}
    public record Exists() implements CoordinatorCreationType {}
    public record NoHospital(Long id) implements CoordinatorCreationType {}
}
