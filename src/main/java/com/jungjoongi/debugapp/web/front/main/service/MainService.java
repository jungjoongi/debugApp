package com.jungjoongi.debugapp.web.front.main.service;

import com.jungjoongi.debugapp.domain.code.CodeInfo;
import com.jungjoongi.debugapp.web.front.main.domain.MainVO;

import java.util.List;

public interface MainService {
    public List<MainVO> getAppDownloadList();
}
