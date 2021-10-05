package com.jungjoongi.debugapp.web.front.rent.domain;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PhoneRentVO {
    private Long rentId;
    private Integer employeeNumber;
    private String employeeName;
    private String modelName;
    private String phoneGroup;
}
