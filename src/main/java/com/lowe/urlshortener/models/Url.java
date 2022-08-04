package com.lowe.urlshortener.models;

import java.time.LocalDateTime;

import javax.persistence.*;

@Entity
@Table(name = "url")
public class Url {

	@Id
	@GeneratedValue
	private long id;
	
	
	@Column(name = "original_url")
	private String originalUrl;
	
	@Column(name = "short_url")
	private String shortLink;
	
	@Column(name = "created_on")
    private LocalDateTime creationDate;
	
	@Column(name = "expire_on")
    private LocalDateTime expirationDate;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getOriginalUrl() {
		return originalUrl;
	}
	public void setOriginalUrl(String originalUrl) {
		this.originalUrl = originalUrl;
	}
	public String getShortLink() {
		return shortLink;
	}
	public void setShortLink(String shortLink) {
		this.shortLink = shortLink;
	}
	public LocalDateTime getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}
	public LocalDateTime getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(LocalDateTime expirationDate) {
		this.expirationDate = expirationDate;
	}
}
