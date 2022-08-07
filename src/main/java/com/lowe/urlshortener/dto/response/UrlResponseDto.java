package com.lowe.urlshortener.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UrlResponseDto {
	private String shortLink;
    public LocalDateTime expirationDate;
	
}
