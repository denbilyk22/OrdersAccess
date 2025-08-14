package com.project.orderaccess.feignclient.dto.request;

import lombok.Builder;

@Builder
public record ClientRequest(String name, String email, String address) {
}
