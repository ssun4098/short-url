package com.ssun.shorturl.main;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ShortUrlService {
    private final ShortUrlRepository repository;

    @Transactional
    public ShortUrlDto create(ShortUrlDto dto) {
        ShortUrlEntity result = repository.save(toEntity(dto));
        try {
            repository.flush();
            log.info("short url entity created. id={}, originalUrl={}", result.getId(), result.getOriginalUrl());

            String shortUrl = Base62Generator.encode(result.getId());
            result.generateShortUrl(shortUrl);
            repository.flush();
            log.info("short url updated. id={}, shortUrl={}", result.getId(), shortUrl);
        } catch (DataIntegrityViolationException e) {
            throw new ShortUrlConflictException(e.getMessage(), result.getId(), result.getShortUrl(), e);
        }

        return toDto(result);
    }

    @Transactional(readOnly = true)
    public Optional<ShortUrlDto> findByUrl(String shortUrl) {
        Long id = Base62Generator.decode(shortUrl);
        return repository.findById(id)
                .map(this::toDto);
    }

    private ShortUrlDto toDto(ShortUrlEntity entity) {
        ShortUrlDto dto = new ShortUrlDto();
        dto.setId(entity.getId());
        dto.setShortUrl(entity.getShortUrl());
        dto.setOriginalUrl(entity.getOriginalUrl());
        return dto;
    }

    private ShortUrlEntity toEntity(ShortUrlDto dto) {
        return ShortUrlEntity.builder()
                .originalUrl(dto.getOriginalUrl())
                .build();
    }
}
