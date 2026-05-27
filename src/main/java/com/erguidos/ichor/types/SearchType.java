package com.erguidos.ichor.types;

public sealed interface SearchType<T>
                permits SearchType.Found,
                        SearchType.Failed {
    public record Found<T>(T found) implements SearchType<T> {}
    public record Failed<T>() implements SearchType<T> {}
}
