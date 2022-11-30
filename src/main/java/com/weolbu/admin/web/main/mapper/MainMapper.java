package com.weolbu.admin.web.main.mapper;

import com.weolbu.admin.web.main.domain.MainVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MainMapper {
    public List<MainVO> selectAppList();
}
