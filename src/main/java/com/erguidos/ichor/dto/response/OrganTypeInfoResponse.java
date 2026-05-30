package com.erguidos.ichor.dto.response;

import com.erguidos.ichor.enums.OrganType;

public record OrganTypeInfoResponse(
		OrganType organType,
		int organTime
		) {}
