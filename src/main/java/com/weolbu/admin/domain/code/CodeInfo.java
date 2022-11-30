package com.weolbu.admin.domain.code;

import com.weolbu.admin.domain.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
//@Entity(name="CODE_INFO")
public class CodeInfo extends BaseTimeEntity {

    @Id
    @Column(length = 100, nullable = false)
    private String upCd;

    @Column(length = 100, nullable = false)
    private String cd;

    @Column(length = 100, nullable = false)
    private String cdNm;

    @Column(length = 4000, nullable = false)
    private String cdDesc;

    @Builder
    public CodeInfo(String upCd, String cd, String cdNm, String cdDesc) {
        this.upCd = upCd;
        this.cd = cd;
        this.cdNm = cdNm;
        this.cdDesc = cdDesc;
    }
}
