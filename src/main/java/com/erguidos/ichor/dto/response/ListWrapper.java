package com.erguidos.ichor.dto.response;

public record ListWrapper<T>(
    java.util.List<T> data
) {}
