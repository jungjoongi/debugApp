package com.jungjoongi.debugapp.domain.phonerent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.stream.Stream;

public interface PhoneRentRespository extends JpaRepository<PhoneRent,Long> {
    @Query("SELECT p " +
            "FROM PHONE_RENT p " +
            "ORDER BY p.dueDate ASC, p.rentDate DESC")
    Stream<PhoneRentRespository> findAllDesc();

}
