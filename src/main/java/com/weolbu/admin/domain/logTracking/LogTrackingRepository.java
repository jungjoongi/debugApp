package com.weolbu.admin.domain.logTracking;

import com.weolbu.admin.domain.shortUrl.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LogTrackingRepository extends JpaRepository<LogTracking, Long> {
    Optional<LogTracking> findById(Long id);
}