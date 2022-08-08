package com.lowe.urlshortener.repository;

import java.util.Optional;
import com.lowe.urlshortener.constants.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import com.lowe.urlshortener.models.Url;
@Repository
public class UrlRepository{

	@Autowired
	private MongoTemplate mongoTemplate;

	public  Optional<Url> findByShortLink(String shortLink){
		Query query=new Query();
		query.addCriteria(Criteria.where(Url.Fields.shortLink).is(shortLink));
		return Optional.ofNullable(mongoTemplate.findOne(query,Url.class, Constants.URL_COLLECTION));
	}
	public  Optional<Url> findByOriginalLink(String originalLink){
		Query query=new Query();
		query.addCriteria(Criteria.where(Url.Fields.originalUrl).is(originalLink));
		return Optional.ofNullable(mongoTemplate.findOne(query,Url.class, Constants.URL_COLLECTION));
	}

	public Url saveUrl(Url url){
		mongoTemplate.save(url,Constants.URL_COLLECTION);
		return url;
	}
}