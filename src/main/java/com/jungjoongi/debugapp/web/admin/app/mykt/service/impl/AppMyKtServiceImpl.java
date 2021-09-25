package com.jungjoongi.debugapp.web.admin.app.mykt.service.impl;

import com.jungjoongi.debugapp.common.util.MD5Generator;
import com.jungjoongi.debugapp.domain.code.CodeInfo;
import com.jungjoongi.debugapp.web.admin.app.mykt.domain.AppMyKtVO;
import com.jungjoongi.debugapp.web.admin.app.mykt.mapper.MyktMapper;
import com.jungjoongi.debugapp.web.admin.app.mykt.service.AppMyktService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class AppMyKtServiceImpl implements AppMyktService {

    private MyktMapper myktMapper;

    public AppMyKtServiceImpl(MyktMapper myktMapper) {
        this.myktMapper = myktMapper;
    }

    private static Logger LOGGER = LoggerFactory.getLogger(AppMyKtServiceImpl.class);
    @Value("${properties.filepath.app}")
    String filepath;
    @Override
    public List<CodeInfo> getCodeList() {
        return null;
    }

    @Override
    public int save(AppMyKtVO appMyKtVO) {
        MD5Generator md5Generator = MD5Generator.getInstance();
        String originFileName = "";
        String encFileName = "";
        String originalFileExtension;
        if(appMyKtVO.getFiles() != null) {
            try {
                originFileName = appMyKtVO.getFiles().getOriginalFilename();
                originalFileExtension = originFileName.substring(originFileName.lastIndexOf("."));
                encFileName = md5Generator.makeFileName(originFileName).toString() + originalFileExtension;
                appMyKtVO.setOriginFileName(originFileName);
                appMyKtVO.setFileName(encFileName);
                File dir = new File(filepath);
                File file = new File(filepath.concat(encFileName));
                dir.mkdirs();
                appMyKtVO.getFiles().transferTo(file);

            } catch (UnsupportedEncodingException e) {
                LOGGER.error("[AppMyKtServiceImpl] (save) UnsupportedEncodingException : {}", e.getMessage());
            } catch (NoSuchAlgorithmException e) {
                LOGGER.error("[AppMyKtServiceImpl] (save) NoSuchAlgorithmException : {}", e.getMessage());
            } catch (Exception e) {
                LOGGER.error("[AppMyKtServiceImpl] (save) Exception : {}", e.getMessage());
            }
        }

        return myktMapper.save(appMyKtVO);
    }
}
