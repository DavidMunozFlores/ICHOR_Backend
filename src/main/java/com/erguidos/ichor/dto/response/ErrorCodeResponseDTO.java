package com.erguidos.ichor.dto.response;

import java.time.LocalDateTime;

public record ErrorCodeResponseDTO(int status, String message, LocalDateTime time) {}
