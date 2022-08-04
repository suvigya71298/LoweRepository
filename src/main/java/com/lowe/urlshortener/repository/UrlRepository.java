package com.lowe.urlshortener.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lowe.urlshortener.models.Url;


@Repository
public interface UrlRepository extends JpaRepository<Url, Long>{
	
	@Query(value = "SELECT * FROM url u WHERE u.short_url = ?1", 
			  nativeQuery = true)
	public Url findByShortLink(String shortLink);
	
	@Query(value = "SELECT * FROM url u WHERE u.original_url = ?1", 
			  nativeQuery = true)
	public Url findByOriginalLink(String originalLink);
}
