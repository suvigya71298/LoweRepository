package com.lowe.urlshortener.models;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

@Builder
@Data
@FieldNameConstants
public class Url {

	private long id;
	

	private String originalUrl;

	private String shortLink;


    private LocalDateTime creationDate;

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
