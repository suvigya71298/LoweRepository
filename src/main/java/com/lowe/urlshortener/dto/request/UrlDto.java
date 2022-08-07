package com.lowe.urlshortener.dto.request;

import lombok.Data;

@Data
public class UrlDto {
	private String url;
    private String expirationDate;
}
