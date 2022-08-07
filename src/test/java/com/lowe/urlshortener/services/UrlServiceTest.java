package com.lowe.urlshortener.services;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.common.hash.Hashing;
import com.lowe.urlshortener.constants.Constants;
import com.lowe.urlshortener.dto.request.UrlDto;
import com.lowe.urlshortener.exception.ExpiredUrlException;
import com.lowe.urlshortener.exception.InvalidUrlException;
import com.lowe.urlshortener.exception.UrlNotExistException;
import com.lowe.urlshortener.models.Url;
import com.lowe.urlshortener.repository.UrlRepository;
import com.lowe.urlshortener.service.UrlService;
import com.lowe.urlshortener.service.UrlServiceImpl;
import org.checkerframework.checker.nullness.Opt;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UrlServiceTest {

    @Mock
    private UrlRepository urlRepository;

    @InjectMocks
    private UrlService urlService =new UrlServiceImpl();

    @Test
    public void testGeneratedShortLinkWhenLinkisNotExistedTest(){

        Url url=new Url(1001,"https://www.suvigya.com","http://localhost:8080/fhjfhhf",
                LocalDate.of(2020, Month.JANUARY, 18).atStartOfDay(),
                LocalDate.of(2025, Month.JANUARY, 18).atStartOfDay()
        );
        UrlDto urlDto=new UrlDto();
        urlDto.setUrl("https://www.suvigya.com");
        when(urlRepository.findByOriginalLink(Mockito.anyString())).thenReturn(Optional.empty());
        when(urlRepository.save(Mockito.any())).thenReturn(url);
        Url result=urlService.generateShortLink(urlDto);
        Assert.assertEquals(url.getShortLink(),result.getShortLink());
    }

    @Test
    public void testGenerateShortLinkWhenLinkExisted(){

        UrlDto urlDto=new UrlDto();
        urlDto.setUrl("https://www.suvigya.com");
        Optional<Url> mockedUrl = Optional.ofNullable(Url.builder()
                .originalUrl("https://www.suvigya.com")
                .shortLink("Yes")
                .creationDate(LocalDateTime.now())
                .expirationDate(LocalDate.of(2025, Month.JANUARY, 18).atStartOfDay())
                .build());
        when(urlRepository.findByOriginalLink(Mockito.anyString())).thenReturn(mockedUrl);
        Url result=urlService.generateShortLink(urlDto);
        Assert.assertEquals("Yes",result.getShortLink());
    }
}
