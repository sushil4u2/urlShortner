package com.example.demo;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

//import org.apache.lucene.codecs.perfield.PerFieldPostingsFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;



@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}

/*@Component
class BookingCommandLineRunner implements CommandLineRunner{

	@Override
	public void run(String... arg0) throws Exception {
		// TODO Auto-generated method stub
		for(Booking b: this.bookingRepository.findAll())
			System.out.println(b.toString());
	}
	
	@Autowired bookingRepository bookingRepository;
}*/


interface UrlRepository extends JpaRepository<Url, Long>{
	Collection<Url> findByShortUrl(String bookingName);
}

@RestController
@RequestMapping("/UrlShortner")
class BookingRestController {
	
	@RequestMapping(method = RequestMethod.POST,value = "/long2short")
	String getLongToShort(@RequestBody String longUrl){
		Url url = new Url();
		url.setLongUrl(longUrl);
		String shortUrl = md5Hash(longUrl);
		url.setShortUrl(shortUrl);
		this.urlRepository.save(url);
		return shortUrl;
		
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
	
	@RequestMapping("/getLong/{shortUrl}")
	Collection<Url> getLong(@PathVariable String shortUrl){
		return this.urlRepository.findByShortUrl(shortUrl);
	}
	
	@Autowired UrlRepository urlRepository;
}


@Entity
class Url {
	@Id
	@GeneratedValue
	private Long id;
	
	private String longUrl;
	
	private String shortUrl;
	
/*	public Booking(String bookingName) {
		super();
		this.bookingName = bookingName;
	}*/
	
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