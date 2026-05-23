package com.erguidos.ichor.dto.types;

import com.erguidos.ichor.entity.Coordinator;

public sealed interface CoordinatorSearchResponseType
                permits CoordinatorSearchResponseType.Found,
                        CoordinatorSearchResponseType.Failed {
    public record Found(Coordinator coordinator) implements CoordinatorSearchResponseType {}
    public record Failed() implements CoordinatorSearchResponseType {}
}
