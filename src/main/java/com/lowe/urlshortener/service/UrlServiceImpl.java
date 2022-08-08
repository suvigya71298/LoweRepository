package com.lowe.urlshortener.service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.apache.logging.log4j.util.Strings;
import com.lowe.urlshortener.constants.Constants;
import com.lowe.urlshortener.dto.request.UrlDto;
import com.lowe.urlshortener.exception.ExpiredUrlException;
import com.lowe.urlshortener.exception.InvalidUrlException;
import com.lowe.urlshortener.exception.UrlNotExistException;
import com.lowe.urlshortener.models.Url;
import com.lowe.urlshortener.repository.UrlRepository;
import java.util.Optional;

@Component
public class UrlServiceImpl implements UrlService {

	
	@Autowired
	private UrlRepository urlRepository;

	@Override
	public Url generateShortLink(UrlDto urlDto) {
		if (Strings.isNotBlank(urlDto.getUrl())) {
			Optional<Url> existingLink = getShortLink(urlDto.getUrl());
			return existingLink.orElseGet(() -> persistShortLink(Url.builder()
					.creationDate(LocalDateTime.now())
					.expirationDate(getExpirationDate(
							urlDto.getExpirationDate(),
							LocalDateTime.now()))
					.shortLink(encode(urlDto.getUrl()))
					.originalUrl(urlDto.getUrl())
					.build()));
		}
		return null;
	}
	
	//get Expiration Date
	private LocalDateTime getExpirationDate(String expirationDate, LocalDateTime creationDate)
    {
        if(expirationDate==null)
        {
            return creationDate.plusYears(5);
        }
		return LocalDateTime.parse(expirationDate);
    }
	
	
	//insert short link into DB
	@Override
	public Url persistShortLink(Url url) {
		return urlRepository.save(url);
	}

	//get short link from the DB
	@Override
	public Optional<Url> getShortLink(String url) {
		return urlRepository.findByOriginalLink(url);
	}

	//Delete the short link from DB 

	//get original URL based on short URL
	@Override
	public Url getOriginalLink(String shortUrl)throws UrlNotExistException, ExpiredUrlException, InvalidUrlException {
		if (shortUrl.length() == 0) {
			throw new InvalidUrlException("Url is short of length");
		}
		shortUrl=Constants.DOMAIN_NAME+shortUrl;
		final Optional<Url> url = urlRepository.findByShortLink(shortUrl);
		if (url.isPresent() && url.get().getExpirationDate().isBefore(LocalDateTime.now())) {
			throw new ExpiredUrlException("Url Link is expired");
		} else if (url.isPresent()) {
			return url.get();
		} else {
			throw new UrlNotExistException("Url for this link is not present");
		}
	}
		public static String encode(String url) {
		String encodedUrl;
		LocalDateTime time = LocalDateTime.now();
		encodedUrl = Hashing.murmur3_32()
				.hashString(url.concat(time.toString()), StandardCharsets.UTF_8)
				.toString();
		return  Constants.DOMAIN_NAME+encodedUrl;
	}

}
