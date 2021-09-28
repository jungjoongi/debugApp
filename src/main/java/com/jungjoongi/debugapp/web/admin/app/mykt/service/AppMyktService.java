package com.jungjoongi.debugapp.web.admin.app.mykt.service;

import com.jungjoongi.debugapp.domain.code.CodeInfo;
import com.jungjoongi.debugapp.web.admin.app.mykt.domain.AppMyKtVO;

import java.util.List;

public interface AppMyktService {
    public List<CodeInfo> getCodeList();
    public String save(AppMyKtVO appMyKtVO);
}
