package com.weolbu.works.domain.shortUrl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {
    Optional<ShortUrl> findById(Long id);
    Page<ShortUrl> findAllByDisplayYn(String disPlayYn, Pageable pageable);

}