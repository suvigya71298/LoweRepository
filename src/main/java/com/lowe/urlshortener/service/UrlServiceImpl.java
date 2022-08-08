package com.lowe.urlshortener.service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import com.google.common.hash.Hashing;
import com.lowe.urlshortener.validator.UrlValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.lowe.urlshortener.constants.Constants;
import com.lowe.urlshortener.dto.request.UrlDto;
import com.lowe.urlshortener.models.Url;
import com.lowe.urlshortener.repository.UrlRepository;
import org.springframework.util.ObjectUtils;
import java.util.Optional;

@Slf4j
@Component
public class UrlServiceImpl implements UrlService {


    @Autowired
    private UrlRepository urlRepository;

    @Override
    public Url generateShortLink(UrlDto urlDto) {
        log.info("Calling generateShortLink with url {}",urlDto.getUrl());
        Optional<Url> existingLink=Optional.empty();
        if (!ObjectUtils.isEmpty(urlDto.getUrl())) {
            existingLink = getShortLink(urlDto.getUrl());
        }
        return existingLink.orElseGet(() -> persistShortLink(Url.builder()
                .creationDate(LocalDateTime.now())
                .expirationDate(getExpirationDate(urlDto.getExpirationDate(),LocalDateTime.now()))
                .shortLink(encode(urlDto.getUrl()))
                .originalUrl(urlDto.getUrl())
                .build()));
    }

    //get Expiration Date
    private LocalDateTime getExpirationDate(String expirationDate, LocalDateTime creationDate) {
        log.info("Calling getExpirationDate with date {}",expirationDate);
        if (!ObjectUtils.isEmpty(expirationDate)) {
            return LocalDateTime.parse(expirationDate);
        }
        return creationDate.plusYears(5);
    }


    //insert short link into DB
    @Override
    public Url persistShortLink(Url url) {
        log.info("Calling persistShortLink with url {}",url.getShortLink());
        return urlRepository.saveUrl(url);

    }

    //get short link from the DB
    @Override
    public Optional<Url> getShortLink(String url) {
        log.info("Calling getShortLink with url {}",url);
        return urlRepository.findByOriginalLink(url);
    }

    //Delete the short link from DB

    //get original URL based on short URL
    @Override
    public Url getOriginalLink(String shortUrl) {
        log.info("Calling getOriginalLink with url {}",shortUrl);
        UrlValidator.findIfShortUrlIsEmpty(shortUrl);
        shortUrl = Constants.DOMAIN_NAME + shortUrl;
        final Optional<Url> url = urlRepository.findByShortLink(shortUrl);
        return UrlValidator.findIfShortUrlExists(url);
    }

    public static String encode(String url) {
        log.info("Calling encode with url {}",url);
        String encodedUrl;
        LocalDateTime time = LocalDateTime.now();
        encodedUrl = Hashing.murmur3_32()
                .hashString(url.concat(time.toString()), StandardCharsets.UTF_8)
                .toString();
        return Constants.DOMAIN_NAME + encodedUrl;
    }

}
