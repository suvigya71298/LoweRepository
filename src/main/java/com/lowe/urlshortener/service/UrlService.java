package com.lowe.urlshortener.service;

import com.lowe.urlshortener.exception.ExpiredUrlException;
import com.lowe.urlshortener.exception.InvalidUrlException;
import com.lowe.urlshortener.exception.UrlNotExistException;
import com.lowe.urlshortener.dto.request.UrlDto;
import com.lowe.urlshortener.models.Url;
import java.util.Optional;


public interface UrlService {
	
	 	public  Url generateShortLink(UrlDto urlDto);
	    public  Url persistShortLink(Url url);
	    public  Optional<Url> getShortLink(String url);
	    public Url getOriginalLink(String shortUrl)throws UrlNotExistException, ExpiredUrlException, InvalidUrlException;;


}
