package com.urlshortener.url.service;

import com.urlshortener.exception.DuplicateResourceException;
import com.urlshortener.exception.ResourceNotFoundException;
import com.urlshortener.exception.UrlExpiredException;
import com.urlshortener.url.dto.CreateUrlRequest;
import com.urlshortener.url.dto.UrlResponse;
import com.urlshortener.url.entity.Url;
import com.urlshortener.url.repository.UrlRepository;
import com.urlshortener.user.entity.User;
import com.urlshortener.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UrlServiceImpl implements UrlService {

    private final UrlRepository urlRepository;
    private final UserRepository userRepository;

    @Override
    public UrlResponse createShortUrl(CreateUrlRequest request) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found")
                );

        String shortCode;

        if (request.getCustomCode() != null &&
                !request.getCustomCode().isBlank()) {

            if (urlRepository.existsByShortCode(
                    request.getCustomCode()
            )) {
                throw new DuplicateResourceException(
                        "Custom code already exists"
                );
            }

            shortCode = request.getCustomCode();

        } else {

            shortCode = UUID.randomUUID()
                    .toString()
                    .replace("-", "")
                    .substring(0, 8);
        }
        Url url = Url.builder()
                .originalUrl(request.getOriginalUrl())
                .shortCode(shortCode)
                .clickCount(0L)
                .createdAt(LocalDateTime.now())
                .expiresAt(request.getExpiresAt())
                .user(user)
                .build();
        urlRepository.save(url);

        return UrlResponse.builder()
                .originalUrl(url.getOriginalUrl())
                .shortCode(url.getShortCode())
                .shortUrl(
                        "http://localhost:8080/" + url.getShortCode()
                )
                .clickCount(url.getClickCount())
                .createdAt(url.getCreatedAt())
                .expiresAt(url.getExpiresAt())
                .build();
    }

    @Override
    public String getOriginalUrl(String shortCode) {

        Url url = urlRepository.findByShortCode(shortCode)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Short URL not found")
                );
        if (url.getExpiresAt() != null &&
                url.getExpiresAt().isBefore(LocalDateTime.now())) {

            throw new UrlExpiredException("URL has expired");
        }

        url.setClickCount(url.getClickCount() + 1);

        urlRepository.save(url);

        return url.getOriginalUrl();
    }

    @Override
    public List<UrlResponse> getMyUrls() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found")
                );

        return urlRepository.findByUser(user)
                .stream()
                .map(url -> UrlResponse.builder()
                        .originalUrl(url.getOriginalUrl())
                        .shortCode(url.getShortCode())
                        .shortUrl(
                                "http://localhost:8080/"
                                        + url.getShortCode()
                        )
                        .clickCount(url.getClickCount())
                        .createdAt(url.getCreatedAt())
                        .expiresAt(url.getExpiresAt())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public UrlResponse getUrlAnalytics(String shortCode) {

        Url url = urlRepository.findByShortCode(shortCode)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Short URL not found")
                );

        return UrlResponse.builder()
                .originalUrl(url.getOriginalUrl())
                .shortCode(url.getShortCode())
                .shortUrl(
                        "http://localhost:8080/" + url.getShortCode()
                )
                .clickCount(url.getClickCount())
                .createdAt(url.getCreatedAt())
                .expiresAt(url.getExpiresAt())
                .build();
    }
    @Override
    public void deleteUrl(String shortCode) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        )
                );

        Url url = urlRepository.findByShortCode(shortCode)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Short URL not found"
                        )
                );

        if (!url.getUser().getId().equals(user.getId())) {
            throw new ResourceNotFoundException(
                    "You do not own this URL"
            );
        }

        urlRepository.delete(url);
    }
}