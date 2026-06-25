package com.ssun.shorturl.main;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/short-urls")
public class ShortUrlApi {
    private final ShortUrlService shortUrlService;

    @GetMapping("/{shortUrl}")
    public ResponseEntity<Void> redirect(@PathVariable String shortUrl) {
        // 수정 기능을 요구할 경우 302, 요구하지 않을 경우 301
        return shortUrlService.findByUrl(shortUrl)
                .map(result -> ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
                        .location(URI.create(result.getOriginalUrl()))
                        .<Void>build())
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<ShortUrlDto> create(@RequestBody ShortUrlDto body) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(shortUrlService.create(body));
    }

    @ExceptionHandler(exception = IllegalArgumentException.class)
    public ResponseEntity<String> badRequest() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("URL에 잘못된 문자열이 있습니다.\n 0~9, a~z, A~Z만 허용합니다.");
    }
}
