package com.urlshortener.url.service;

import com.urlshortener.url.dto.CreateUrlRequest;
import com.urlshortener.url.dto.UpdateUrlRequest;
import com.urlshortener.url.dto.UrlResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UrlService {

    UrlResponse createShortUrl(CreateUrlRequest request);

    String getOriginalUrl(String shortCode);

    Page<UrlResponse> getMyUrls(
            int page,
            int size
    );

    UrlResponse getUrlAnalytics(String shortCode);
    void deleteUrl(String shortCode);
    UrlResponse updateUrl(
            String shortCode,
            UpdateUrlRequest request
    );

}
