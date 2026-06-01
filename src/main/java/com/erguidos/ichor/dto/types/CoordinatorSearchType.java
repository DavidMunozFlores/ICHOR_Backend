package com.erguidos.ichor.dto.types;

import com.erguidos.ichor.entity.Coordinator;

public sealed interface CoordinatorSearchType
                permits CoordinatorSearchType.Found,
                        CoordinatorSearchType.Failed {
    public record Found(Coordinator coordinator) implements CoordinatorSearchType {}
    public record Failed() implements CoordinatorSearchType {}
}
