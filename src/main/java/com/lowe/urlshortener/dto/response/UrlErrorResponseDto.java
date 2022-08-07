package com.lowe.urlshortener.dto.response;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UrlErrorResponseDto {

	private String status;
    private String error;
	
}
