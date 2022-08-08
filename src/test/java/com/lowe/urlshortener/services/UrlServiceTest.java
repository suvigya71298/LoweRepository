package com.lowe.urlshortener.services;

import com.lowe.urlshortener.dto.request.UrlDto;
import com.lowe.urlshortener.exception.ExpiredUrlException;
import com.lowe.urlshortener.exception.InvalidUrlException;
import com.lowe.urlshortener.exception.UrlNotExistException;
import com.lowe.urlshortener.models.Url;
import com.lowe.urlshortener.repository.UrlRepository;
import com.lowe.urlshortener.service.UrlService;
import com.lowe.urlshortener.service.UrlServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UrlServiceTest {

    @Mock
    private UrlRepository urlRepository;

    @InjectMocks
    private UrlService urlService = new UrlServiceImpl();

    @Test
    public void generatedShortLinkWhenLinkIsNotExistedTest() {

        Url url = new Url(1001, "https://www.suvigya.com", "http://localhost:8080/090f5757",
                LocalDate.of(2020, Month.JANUARY, 18).atStartOfDay(),
                LocalDate.of(2025, Month.JANUARY, 18).atStartOfDay()
        );
        UrlDto urlDto = new UrlDto();
        urlDto.setUrl("https://www.suvigya.com");
        when(urlRepository.findByOriginalLink(Mockito.anyString())).thenReturn(Optional.empty());
        when(urlRepository.saveUrl(Mockito.any())).thenReturn(url);
        Url result = urlService.generateShortLink(urlDto);
        Assertions.assertEquals(url.getShortLink(), result.getShortLink());
    }

    @Test
    public void GenerateShortLinkWhenLinkExistedTest() {

        UrlDto urlDto = new UrlDto();
        urlDto.setUrl("https://www.suvigya.com");
        Optional<Url> mockedUrl = Optional.ofNullable(Url.builder()
                .originalUrl("https://www.suvigya.com")
                .shortLink("Yes")
                .creationDate(LocalDateTime.now())
                .expirationDate(LocalDate.of(2025, Month.JANUARY, 18).atStartOfDay())
                .build());
        when(urlRepository.findByOriginalLink(Mockito.anyString())).thenReturn(mockedUrl);
        Url result = urlService.generateShortLink(urlDto);
        Assertions.assertEquals("Yes", result.getShortLink());
    }

    @Test
    public void getOriginalLinkTest() throws UrlNotExistException, InvalidUrlException, ExpiredUrlException {
        Optional<Url> mockedUrl = Optional.ofNullable(Url.builder()
                .originalUrl("https://www.suvigya.com")
                .shortLink("Yes")
                .creationDate(LocalDateTime.now())
                .expirationDate(LocalDate.of(2025, Month.JANUARY, 18).atStartOfDay())
                .build());
        String shortUrl = "http://localhost:8080/ghjkldfg";
        when(urlRepository.findByShortLink(Mockito.anyString())).thenReturn(mockedUrl);
        Url result = urlService.getOriginalLink(shortUrl);
        Assertions.assertEquals("https://www.suvigya.com", result.getOriginalUrl());
    }

    @Test
    public void testExceptionWhenUrlExpired() {
        Url url= new Url(1001, "https://www.suvigya.com", "http://localhost:8080/fhjfhhf",
                LocalDate.of(2018, Month.JANUARY, 18).atStartOfDay(),
                LocalDate.of(2019, Month.JANUARY, 18).atStartOfDay()
        );
        when(urlRepository.findByShortLink(Mockito.anyString())).thenReturn(Optional.of(url));
        ExpiredUrlException exception = assertThrows(ExpiredUrlException.class,()->{
            urlService.getOriginalLink("http://localhost:8080/fhjfhhf")  ;
        });
    }
    @Test
    public void testExceptionWhenUrlNotExist() {
        Url url= new Url(1001, "https://www.suvigya.com", "http://localhost:8080/fhjfhhf",
                LocalDate.of(2018, Month.JANUARY, 18).atStartOfDay(),
                LocalDate.of(2019, Month.JANUARY, 18).atStartOfDay()
        );
        InvalidUrlException exception = assertThrows(InvalidUrlException.class,()->{
            urlService.getOriginalLink("")  ;
        });
    }
    @Test
    public void testExceptionWhenUrlIsInvalid() {
        Url url= new Url(1001, "https://www.suvigya.com", "http://localhost:8080/fhjfhhf",
                LocalDate.of(2018, Month.JANUARY, 18).atStartOfDay(),
                LocalDate.of(2019, Month.JANUARY, 18).atStartOfDay()
        );
        when(urlRepository.findByShortLink(Mockito.anyString())).thenReturn(Optional.empty());
        UrlNotExistException exception = assertThrows(UrlNotExistException.class,()->{
            urlService.getOriginalLink("http://localhost:8080/fhjfhhf")  ;
        });
    }
    }


