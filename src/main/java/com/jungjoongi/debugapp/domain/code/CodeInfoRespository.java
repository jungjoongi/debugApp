package com.jungjoongi.debugapp.domain.code;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.stream.Stream;

public interface CodeInfoRespository extends JpaRepository<CodeInfo,Long> {
    @Query("SELECT p " +
            "FROM CODE_INFO p " +
            "ORDER BY p.cd DESC")
    Stream<CodeInfoRespository> findAllDesc();


    @Query("SELECT p FROM CODE_INFO p WHERE p.upCd = ?1 ORDER BY p.cd DESC")
    Stream<CodeInfoRespository> fileAllByUpCd(String upCd);

}
