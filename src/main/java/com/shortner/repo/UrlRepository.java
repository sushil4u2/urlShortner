package com.shortner.repo;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shortner.entities.Url;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long>{
	Collection<Url> findByShortUrl(String bookingName);
}