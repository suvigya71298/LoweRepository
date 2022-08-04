package com.lowe.urlshortener.controller;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.lowe.urlshortener.dto.UrlDto;
import com.lowe.urlshortener.dto.UrlErrorResponseDto;
import com.lowe.urlshortener.dto.UrlResponseDto;
import com.lowe.urlshortener.models.Url;
import com.lowe.urlshortener.service.UrlService;

@RestController
public class UrlShortenerController {
	
	@Autowired
    private UrlService urlService;
	
	
	
	@PostMapping(value="/generate")
	public ResponseEntity<?> generateShortLink(@RequestBody UrlDto urlDto){
		
		Url urlToRet = urlService.generateShortLink(urlDto);
		if(urlToRet!=null) {
			UrlResponseDto urlResponseDto = 
					new UrlResponseDto(urlToRet.getShortLink(),urlToRet.getExpirationDate());
			return new ResponseEntity<UrlResponseDto>(urlResponseDto,HttpStatus.OK);
		}
		
		UrlErrorResponseDto urlErrorResponseDto = new UrlErrorResponseDto("","");
        urlErrorResponseDto.setStatus("404");
        urlErrorResponseDto.setError("There was an error processing your request. please try again.");
        return new ResponseEntity<UrlErrorResponseDto>(urlErrorResponseDto,HttpStatus.OK);

		
	}
	
	@GetMapping(value="/{shortLink}")
	public ResponseEntity<?> getOriginalLink(@PathVariable String shortLink)throws IOException, URISyntaxException{
		if(shortLink.length()==0) {
			UrlErrorResponseDto urlErrorResponseDto=new UrlErrorResponseDto("", "");
			urlErrorResponseDto.setError("Invalid Url");
			urlErrorResponseDto.setStatus("400");
			return new ResponseEntity<UrlErrorResponseDto>(urlErrorResponseDto,HttpStatus.OK);
		}
		
		Url urlToRet = urlService.getOriginalLink(shortLink);
		//System.out.println(urlToRet);
		 if(urlToRet == null)
	        {
	            UrlErrorResponseDto urlErrorResponseDto = new UrlErrorResponseDto("","");
	            urlErrorResponseDto.setError("Url does not exist or it might have expired!");
	            urlErrorResponseDto.setStatus("400");
	            return new ResponseEntity<UrlErrorResponseDto>(urlErrorResponseDto,HttpStatus.OK);
	        }
		 if(urlToRet.getExpirationDate().isBefore(LocalDateTime.now()))
	        {
	            urlService.deleteShortLink(urlToRet);
	            UrlErrorResponseDto urlErrorResponseDto = new UrlErrorResponseDto("","");
	            urlErrorResponseDto.setError("Url Expired. Please try generating a fresh one.");
	            urlErrorResponseDto.setStatus("200");
	            return new ResponseEntity<UrlErrorResponseDto>(urlErrorResponseDto,HttpStatus.OK);
	        }
		 	
		return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(urlToRet.getOriginalUrl()));
	}
	
}
