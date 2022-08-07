package com.lowe.urlshortener.models;

import java.time.LocalDateTime;

import javax.persistence.*;

import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "url")
@Builder
@Data
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

public Url(){

}
	public Url(long id, String originalUrl, String shortLink, LocalDateTime creationDate, LocalDateTime expirationDate) {
		this.id = id;
		this.originalUrl = originalUrl;
		this.shortLink = shortLink;
		this.creationDate = creationDate;
		this.expirationDate = expirationDate;
	}
}
