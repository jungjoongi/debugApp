package com.weolbu.works.web.main.service.impl;

import com.weolbu.works.web.main.service.MainService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MainServiceImpl implements MainService {



    @Override
    public List<String> getAppDownloadList() {
        return new ArrayList<>();
    }
}
