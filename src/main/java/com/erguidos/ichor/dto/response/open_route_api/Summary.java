package com.erguidos.ichor.dto.response.open_route_api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public record Summary(double duration) {}
