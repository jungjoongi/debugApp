package com.jungjoongi.debugapp.domain.phonerent;

import com.jungjoongi.debugapp.domain.RentTimeEntity;
import lombok.*;
import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@Entity(name="PHONE_RENT")
public class PhoneRent extends RentTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 20, nullable = false, name = "RENT_ID")
    private Long rentId;

    @Column(length = 11, nullable = false, name = "EMPLOYEE_NUMBER")
    private Integer employeeNumber;

    @Column(length = 20, nullable = false, name = "EMPLOYEE_NAME")
    private String employeeName;

    @Column(length = 30, nullable = false, name = "MODEL_NAME")
    private String modelName;

    @Column(length = 1, nullable = false, name = "PHONE_GROUP")
    private char phoneGroup;

    @Builder
    public PhoneRent(Long rentId, Integer employeeNumber, String employeeName, String modelName, char phoneGroup) {
        this.rentId = rentId;
        this.employeeNumber = employeeNumber;
        this.employeeName = employeeName;
        this.modelName = modelName;
        this.phoneGroup = phoneGroup;
    }

}