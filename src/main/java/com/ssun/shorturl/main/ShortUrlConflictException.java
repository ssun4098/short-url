package com.ssun.shorturl.main;

import org.springframework.dao.DataIntegrityViolationException;

public class ShortUrlConflictException extends DataIntegrityViolationException {
    private final Long id;
    private final String shortUrl;

    public ShortUrlConflictException(String message, Long id, String shortUrl, Throwable cause) {
        super(message, cause);
        this.id = id;
        this.shortUrl = shortUrl;
    }

    public Long getId() {
        return id;
    }

    public String getShortUrl() {
        return shortUrl;
    }
}