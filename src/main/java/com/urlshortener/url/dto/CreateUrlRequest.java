package com.urlshortener.url.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateUrlRequest {

    @NotBlank
    private String originalUrl;

}