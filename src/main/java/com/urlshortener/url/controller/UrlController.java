package com.urlshortener.url.controller;

import com.urlshortener.url.dto.CreateUrlRequest;
import com.urlshortener.url.dto.UrlResponse;
import com.urlshortener.url.service.UrlService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/urls")
@RequiredArgsConstructor
public class UrlController {

    private final UrlService urlService;

    @PostMapping
    public UrlResponse createShortUrl(
            @Valid @RequestBody CreateUrlRequest request
    ) {
        return urlService.createShortUrl(request);
    }

    @GetMapping("/my")
    public List<UrlResponse> getMyUrls() {
        return urlService.getMyUrls();
    }
}