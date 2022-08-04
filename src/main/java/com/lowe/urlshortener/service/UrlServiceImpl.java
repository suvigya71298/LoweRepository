package com.lowe.urlshortener.service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.lowe.urlshortener.dto.UrlDto;
import com.lowe.urlshortener.models.Url;
import com.lowe.urlshortener.repository.UrlRepository;

@Component
public class UrlServiceImpl implements UrlService {

	
	@Autowired
	private UrlRepository urlRepository;

	@Override
	public Url generateShortLink(UrlDto urlDto) {
		if(urlDto.getUrl()!=null) {				
			Url existingLink=getShortLink(urlDto.getUrl());
			if(existingLink!=null)
			return existingLink;
		
			String encodedUrl = encode(urlDto.getUrl());
			Url urlToPersist = new Url();
			urlToPersist.setCreationDate(LocalDateTime.now());
            urlToPersist.setOriginalUrl(urlDto.getUrl());
            urlToPersist.setShortLink(encodedUrl);
            urlToPersist.setExpirationDate(getExpirationDate(urlDto.getExpirationDate(),urlToPersist.getCreationDate()));
            
            Url urlToRet = persistShortLink(urlToPersist);
            if(urlToRet != null)
                return urlToRet;
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
        LocalDateTime expirationDateToRet = LocalDateTime.parse(expirationDate);
        return expirationDateToRet;
    }
	
	
	//insert short link into DB
	@Override
	public Url persistShortLink(Url url) {
		Url urlToRet = urlRepository.save(url);
        return urlToRet;
	}

	//get short link from the DB
	@Override
	public Url getShortLink(String url) {
		 Url urlToRet = urlRepository.findByOriginalLink(url);
	        return urlToRet;
	}

	//Delete the short link from DB 
	@Override
	public void deleteShortLink(Url url) {
		urlRepository.delete(url);
		
	}
	
	//Creates Encoded Short link of 8 characters
	private String encode(String url) {
		String encodedUrl = "";
        LocalDateTime time = LocalDateTime.now();
        encodedUrl = Hashing.murmur3_32()
                .hashString(url.concat(time.toString()), StandardCharsets.UTF_8)
                .toString();
        return  "http://localhost:8080/"+encodedUrl;
	}
	
	//get original URL based on short URL
	@Override
	public Url getOriginalLink(String shortUrl) {
		
		shortUrl="http://localhost:8080/"+shortUrl;		
		Url originalLink=urlRepository.findByShortLink(shortUrl);
		if(originalLink!=null)
		return originalLink;
		
		return null;
	}
}
