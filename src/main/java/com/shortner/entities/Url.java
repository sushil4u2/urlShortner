package com.shortner.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Url {
	@Id
	@GeneratedValue
	private Long id;
	private String longUrl;
	private String shortUrl;
	
	public Url() {
		
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getLongUrl() {
		return longUrl;
	}

	public void setLongUrl(String longUrl) {
		this.longUrl = longUrl;
	}

	public Url(String longUrl, String shortUrl) {
		super();
		this.longUrl = longUrl;
		this.shortUrl = shortUrl;
	}

	public String getShortUrl() {
		return shortUrl;
	}

	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}


}