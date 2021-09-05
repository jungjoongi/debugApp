package com.jungjoongi.debugapp.domain.appmykt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.stream.Stream;

public interface AppMyKtRespository extends JpaRepository<AppMyKt,Long> {
    @Query("SELECT p " +
            "FROM APP_MYKT p " +
            "ORDER BY p.id DESC")
    Stream<AppMyKtRespository> findAllDesc();

}
