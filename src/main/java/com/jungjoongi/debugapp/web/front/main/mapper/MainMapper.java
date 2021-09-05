package com.jungjoongi.debugapp.web.front.main.mapper;

import com.jungjoongi.debugapp.web.front.main.domain.MainVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MainMapper {
    public List<MainVO> selectAppList();
}
