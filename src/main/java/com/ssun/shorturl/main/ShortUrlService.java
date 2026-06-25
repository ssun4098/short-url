package com.ssun.shorturl.main;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ShortUrlService {
    private final ShortUrlRepository repository;

    @Transactional
    public ShortUrlDto create(ShortUrlDto dto) {
        ShortUrlEntity result = repository.save(toEntity(dto));
        result.generateShortUrl(Base62Generator.encode(result.getId()));
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
