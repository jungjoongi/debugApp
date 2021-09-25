package com.jungjoongi.debugapp.web.front.main.service.impl;

import com.jungjoongi.debugapp.web.front.main.domain.MainVO;
import com.jungjoongi.debugapp.web.front.main.mapper.MainMapper;
import com.jungjoongi.debugapp.web.front.main.service.MainService;
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
