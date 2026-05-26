package com.erguidos.ichor.dto.response;

import java.time.LocalDateTime;

public record ErrorCodeResponse(int status, String message, LocalDateTime time) {}
