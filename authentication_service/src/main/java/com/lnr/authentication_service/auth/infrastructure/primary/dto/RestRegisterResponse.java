package com.lnr.authentication_service.auth.infrastructure.primary.dto;

import lombok.Builder;

import java.util.UUID;
public record RestRegisterResponse(UUID publicId,String message) {
}
