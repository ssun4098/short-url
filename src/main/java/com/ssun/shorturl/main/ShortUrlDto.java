package com.ssun.shorturl.main;

import lombok.Data;

@Data
public class ShortUrlDto {
    private Long id;
    private String shortUrl;
    private String originalUrl;
}
