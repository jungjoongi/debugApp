package com.jungjoongi.debugapp.web.admin.app.mykt.service.impl;

import com.jungjoongi.debugapp.domain.appmykt.AppMyKt;
import com.jungjoongi.debugapp.domain.appmykt.AppMyKtRespository;
import com.jungjoongi.debugapp.domain.code.CodeInfo;
import com.jungjoongi.debugapp.domain.code.CodeInfoRespository;
import com.jungjoongi.debugapp.web.admin.app.mykt.service.AppMyktService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AppMyKtServiceImpl implements AppMyktService {

    private final CodeInfoRespository codeInfoRespository;

    @Override
    public List<CodeInfo> getCodeList() {
        return null;
    }
}
