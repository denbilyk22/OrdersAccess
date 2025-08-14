package com.project.orderaccess.exception;

import lombok.Builder;

@Builder
public record ExceptionResponse(int status, String message) {
}
