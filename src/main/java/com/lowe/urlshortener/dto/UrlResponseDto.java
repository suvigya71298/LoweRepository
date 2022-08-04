package com.lowe.urlshortener.dto;

import java.time.LocalDateTime;

public class UrlResponseDto {
	
	
	private String shortLink;
    private LocalDateTime expirationDate;
    
    public UrlResponseDto( String shortLink, LocalDateTime expirationDate) {
		super();		
		this.shortLink = shortLink;
		this.expirationDate = expirationDate;
	}
	public String getShortLink() {
		return shortLink;
	}
	public void setShortLink(String shortLink) {
		this.shortLink = shortLink;
	}
	public LocalDateTime getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(LocalDateTime expirationDate) {
		this.expirationDate = expirationDate;
	}
	
}
