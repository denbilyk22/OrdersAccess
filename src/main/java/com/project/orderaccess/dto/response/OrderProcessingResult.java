package com.project.orderaccess.dto.response;

import lombok.Builder;

@Builder
public record OrderProcessingResult(int numberOfSucceeds, int numberOfErrors) {
}
