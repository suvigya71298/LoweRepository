package com.lowe.urlshortener.validator;

import com.lowe.urlshortener.exception.ExpiredUrlException;
import com.lowe.urlshortener.exception.InvalidUrlException;
import com.lowe.urlshortener.exception.UrlNotExistException;
import com.lowe.urlshortener.models.Url;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
public class UrlValidator {
    public static void findIfShortUrlIsEmpty(String shortUrl){
        if (ObjectUtils.isEmpty(shortUrl)) {
            log.error("Short URL {} is empty or null",shortUrl);
            throw new InvalidUrlException(HttpStatus.BAD_REQUEST,"Please Enter a valid URL");
        }
    }
    public static Url findIfShortUrlExists(Optional<Url> url){
        if (url.isPresent() && url.get().getExpirationDate().isBefore(LocalDateTime.now())) {
            log.error("Short URL {} is expired",url.get().getShortLink());
            throw new ExpiredUrlException(HttpStatus.NOT_FOUND,"URL is expired");
        } else if (url.isPresent()) {
            return url.get();
        } else {
            log.error("Short URL does not exist");
            throw new UrlNotExistException(HttpStatus.NOT_FOUND,"URL not found");
        }
    }
}
