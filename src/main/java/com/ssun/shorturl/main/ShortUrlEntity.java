package com.ssun.shorturl.main;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "short_url")
public class ShortUrlEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "short_url", unique = true)
    private String shortUrl;

    @Column(name = "original_url")
    private String originalUrl;

    @Builder
    public ShortUrlEntity(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public void generateShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }
}
