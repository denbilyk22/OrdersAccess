package com.project.orderaccess.feignclient.dto.request;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record OrderRequest(String name,
                           BigDecimal price,
                           UUID supplierId,
                           UUID consumerId) {
}
