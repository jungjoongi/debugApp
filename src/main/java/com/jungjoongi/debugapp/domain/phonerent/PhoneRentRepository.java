package com.jungjoongi.debugapp.domain.phonerent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PhoneRentRepository extends JpaRepository<PhoneRent,Long> {
    @Query("SELECT p " +
            "FROM PHONE_RENT p " +
            "WHERE p.dueDate is null " +
            "ORDER BY p.dueDate ASC, p.rentDate DESC")
    List<PhoneRent> fileAllDueDateIsNull();

    @Query("SELECT p " +
            "FROM PHONE_RENT p " +
            "WHERE p.dueDate is not null " +
            "ORDER BY p.dueDate ASC, p.rentDate DESC")
    List<PhoneRent> fileAllDueDateIsNotNull();

    //List<PhoneRent> findAllByEmployeeNameAndEmployeeNumber(PhoneRent phoneRent);

}
