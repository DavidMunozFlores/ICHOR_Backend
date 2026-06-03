package com.erguidos.ichor.dto.response.open_route_api;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public record ORAPIGetDurationResponse(List<Feature> features) {}
