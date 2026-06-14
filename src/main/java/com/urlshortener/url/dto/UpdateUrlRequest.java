package com.urlshortener.url.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UpdateUrlRequest {

    @NotBlank(message = "Original URL is required")
    private String originalUrl;

    private LocalDateTime expiresAt;
}