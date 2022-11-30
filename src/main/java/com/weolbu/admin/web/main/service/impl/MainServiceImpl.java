package com.weolbu.admin.web.main.service.impl;

import com.weolbu.admin.web.main.domain.MainVO;
import com.weolbu.admin.web.main.mapper.MainMapper;
import com.weolbu.admin.web.main.service.MainService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MainServiceImpl implements MainService {

    final private MainMapper mainMapper;

    public MainServiceImpl(MainMapper mainMapper) {
        this.mainMapper = mainMapper;
    }

    @Override
    public List<MainVO> getAppDownloadList() {
        return mainMapper.selectAppList();
    }
}
