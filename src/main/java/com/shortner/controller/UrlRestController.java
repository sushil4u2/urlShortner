package com.shortner.controller;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.shortner.entities.Url;
import com.shortner.repo.UrlRepository;


@RestController
@RequestMapping("/UrlShortner")
public class UrlRestController {
	
	@Autowired UrlRepository urlRepository;
	
	@RequestMapping(method = RequestMethod.POST, value = "/long2short")
	String getLongToShort(@RequestBody String longUrl){
		Url url = new Url();
		url.setLongUrl(longUrl);
		String shortUrl = md5Hash(longUrl);
		url.setShortUrl(shortUrl);
		Collection<Url> c = this.urlRepository.findByShortUrl(url.getShortUrl());
		if(c.isEmpty())
			this.urlRepository.save(url);
		return shortUrl;
		
	}
	
	@RequestMapping("/getLong/{shortUrl}")
	Collection<Url> getLong(@PathVariable String shortUrl){
		return this.urlRepository.findByShortUrl(shortUrl);
	}
	
	
	private final static String salt="DGE$5SGr@3VsHYUMas2323E4d57vfBfFSTRU@!DSH(*%FDSdfg13sgfsg";
	public static String md5Hash(String message) {
        String md5 = "";
        if(null == message) 
            return null;
         
        message = message+salt;//adding a salt to the string before it gets hashed.
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");//Create MessageDigest object for MD5
            digest.update(message.getBytes(), 0, message.length());//Update input string in message digest
            md5 = new BigInteger(1, digest.digest()).toString(16);//Converts message digest value in base 16 (hex)
  
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return md5;
    }
	

}