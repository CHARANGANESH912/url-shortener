package com.urlshortener.url.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UrlResponse {

    private String originalUrl;

    private String shortCode;

    private String shortUrl;

    private Long clickCount;

}