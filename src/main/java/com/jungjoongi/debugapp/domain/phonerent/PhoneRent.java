package com.jungjoongi.debugapp.domain.phonerent;

import com.jungjoongi.debugapp.domain.BaseTimeEntity;
import com.jungjoongi.debugapp.domain.RentTimeEntity;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@Builder
@Entity(name="PHONE_RENT")
public class PhoneRent extends RentTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "RENT_ID")
    private Long rentId;

    @Column(length = 11, nullable = false, name = "EMPLOYEE_NUMBER")
    private int employeeNumber;

    @Column(length = 20, nullable = false, name = "EMPLOYEE_NAME")
    private String employeeName;

    @Column(length = 30, nullable = false, name = "MODEL_NAME")
    private String modelName;

    @Column(length = 1, nullable = false, name = "PHONE_GROUP")
    private String phoneGroup;

}