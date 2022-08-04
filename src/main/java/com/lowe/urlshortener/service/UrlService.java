package com.lowe.urlshortener.service;

import org.springframework.stereotype.Service;

import com.lowe.urlshortener.dto.UrlDto;
import com.lowe.urlshortener.models.Url;

@Service
public interface UrlService {
	
	 	public Url generateShortLink(UrlDto urlDto);
	    public Url persistShortLink(Url url);
	    public Url getShortLink(String url);
	    public  void  deleteShortLink(Url url);
	    public Url getOriginalLink(String shortUrl);
	    
}
