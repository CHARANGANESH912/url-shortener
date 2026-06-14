package com.urlshortener.url.service;

import com.urlshortener.url.dto.CreateUrlRequest;
import com.urlshortener.url.dto.UpdateUrlRequest;
import com.urlshortener.url.dto.UrlResponse;

import java.util.List;

public interface UrlService {

    UrlResponse createShortUrl(CreateUrlRequest request);

    String getOriginalUrl(String shortCode);

    List<UrlResponse> getMyUrls();

    UrlResponse getUrlAnalytics(String shortCode);
    void deleteUrl(String shortCode);
    UrlResponse updateUrl(
            String shortCode,
            UpdateUrlRequest request
    );

}
