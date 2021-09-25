package com.jungjoongi.debugapp.web.admin.app.mykt.mapper;

import com.jungjoongi.debugapp.web.admin.app.mykt.domain.AppMyKtVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MyktMapper {
    public int save(AppMyKtVO appMyKtVO);
}
