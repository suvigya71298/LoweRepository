package com.lowe.urlshortener.controller;


import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.annotation.JsonView;
import com.lowe.urlshortener.constants.Constants;
import com.lowe.urlshortener.exception.ExpiredUrlException;
import com.lowe.urlshortener.exception.InvalidUrlException;
import com.lowe.urlshortener.exception.UrlNotExistException;
import com.lowe.urlshortener.view.DtoView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lowe.urlshortener.dto.request.UrlDto;
import com.lowe.urlshortener.dto.response.UrlErrorResponseDto;
import com.lowe.urlshortener.dto.response.UrlResponseDto;
import com.lowe.urlshortener.models.Url;
import com.lowe.urlshortener.service.UrlService;

@RestController
public class UrlShortenerController {
	
	@Autowired
    private UrlService urlService;

	@PostMapping(value="/generate")
	public ResponseEntity<?> generateShortLink(@RequestBody @Validated({DtoView.UrlDtoView.class}) UrlDto urlDto){

		if(!ObjectUtils.isEmpty(urlDto.getUrl())) {
			final Url shortUrl = urlService.generateShortLink(urlDto);
			if (shortUrl != null) {
				UrlResponseDto urlResponseDto = UrlResponseDto.builder()
						.shortLink(shortUrl.getShortLink())
						.expirationDate(shortUrl.getExpirationDate().toString())
						.build();
				return new ResponseEntity<>(urlResponseDto, HttpStatus.OK);
			}
		}
			final UrlErrorResponseDto urlErrorResponseDto = UrlErrorResponseDto.builder()
					.status(String.valueOf(HttpStatus.BAD_REQUEST))
					.error(Constants.BAD_REQUEST_ERROR_MESSAGE)
					.build();
			return new ResponseEntity<>(urlErrorResponseDto, HttpStatus.OK);
		}

	
	@GetMapping(value="/{shortLink}")
	public ResponseEntity<?> getOriginalLink(@PathVariable String shortLink, HttpServletResponse response) {
		try {
			Url urlToRet = urlService.getOriginalLink(shortLink);
				if(!checkHttpHeader(urlToRet.getOriginalUrl()))
					response.sendRedirect(Constants.HTTPS + urlToRet.getOriginalUrl());
				else
					response.sendRedirect(urlToRet.getOriginalUrl());
		} catch (InvalidUrlException e) {
			UrlErrorResponseDto urlErrorResponseDto = UrlErrorResponseDto.builder()
					.error(e.getMessage())
					.status(String.valueOf(HttpStatus.BAD_REQUEST))
					.build();
			return new ResponseEntity<>(urlErrorResponseDto, HttpStatus.OK);
		} catch (UrlNotExistException | ExpiredUrlException e){
			UrlErrorResponseDto urlErrorResponseDto = UrlErrorResponseDto.builder()
					.error(e.getMessage())
					.status(String.valueOf(HttpStatus.NOT_FOUND))
					.build();
			return new ResponseEntity<>(urlErrorResponseDto, HttpStatus.OK);
		} catch (Exception e) {
			UrlErrorResponseDto urlErrorResponseDto = UrlErrorResponseDto.builder()
					.error(Constants.INTERNAL_SERVER_ERROR)
					.status(String.valueOf(HttpStatus.NOT_FOUND))
					.build();
			return new ResponseEntity<>(urlErrorResponseDto, HttpStatus.OK);
		}
		return null;
	}

	private boolean checkHttpHeader(String originalUrl) {
		return originalUrl.contains(Constants.HTTP);
	}

}
