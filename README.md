# URL Shortener API

A production-ready URL shortening service built using Spring Boot, Spring Security, JWT Authentication, PostgreSQL, and Bucket4j Rate Limiting.

## Features

* User Registration & Login
* JWT Authentication
* URL Shortening
* Custom Short Codes
* URL Expiry
* Click Analytics
* User-specific URL Management
* Update URL
* Delete URL
* Rate Limiting
* Pagination
* Global Exception Handling

## Tech Stack

* Java 17
* Spring Boot 3
* Spring Security
* Spring Data JPA
* PostgreSQL
* JWT
* Bucket4j
* Swagger/OpenAPI

## API Endpoints

### Authentication

POST /api/v1/auth/register

POST /api/v1/auth/login

### URLs

POST /api/v1/urls

GET /api/v1/urls/my

GET /api/v1/urls/{shortCode}

PUT /api/v1/urls/{shortCode}

DELETE /api/v1/urls/{shortCode}

### Redirect

GET /{shortCode}

## Pagination

GET /api/v1/urls/my?page=0&size=5

## Rate Limiting

Users are limited to a fixed number of URL creation requests per minute using Bucket4j.

## Author

Sathya Dhanush
