package com.jungjoongi.debugapp.web.admin.app.mykt.service.impl;

import com.jungjoongi.debugapp.common.util.MD5Generator;
import com.jungjoongi.debugapp.domain.code.CodeInfo;
import com.jungjoongi.debugapp.web.admin.app.mykt.domain.AppMyKtVO;
import com.jungjoongi.debugapp.web.admin.app.mykt.domain.FileUploadVO;
import com.jungjoongi.debugapp.web.admin.app.mykt.mapper.MyktMapper;
import com.jungjoongi.debugapp.web.admin.app.mykt.service.AppMyktService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.Multipart;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
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
    private final String[] ALLOW_EXTENTION = {".apk", ".plist", ".ipa"};

    @Override
    @Transactional
    public String save(AppMyKtVO appMyKtVO) {
        MD5Generator md5Generator = MD5Generator.getInstance();
        String originFileName = "";
        String encFileName = "";
        String originalFileExtension;
        MultipartFile[] files = appMyKtVO.getFiles();
        List<FileUploadVO> fileUPloadVoList = new ArrayList<>();
        String successYn = "Y";
        if(files != null) {
            try {
                for(MultipartFile file : files) {
                    FileUploadVO fileUPloadVo = new FileUploadVO();
                    originFileName = file.getOriginalFilename();
                    originalFileExtension = originFileName.substring(originFileName.lastIndexOf("."));
                    encFileName = md5Generator.makeFileName(originFileName).toString() + originalFileExtension;
                    fileUPloadVo.setOriginFileName(originFileName);
                    fileUPloadVo.setFileName(encFileName);
                    fileUPloadVo.setDownloadYn(originalFileExtension.contains("ipa") ? "N" : "Y");
                    File dir = new File(filepath);
                    File saveFile = new File(filepath.concat(encFileName));
                    dir.mkdirs();
                    file.transferTo(saveFile);
                    fileUPloadVoList.add(fileUPloadVo);
                }
                myktMapper.save(appMyKtVO);
                for(FileUploadVO list : fileUPloadVoList) {
                    list.setContentId(appMyKtVO.getId());
                }
                myktMapper.saveFile(fileUPloadVoList);

            } catch (UnsupportedEncodingException e) {
                LOGGER.error("[AppMyKtServiceImpl] (save) UnsupportedEncodingException : {}", e.getMessage());
                successYn = "N";
            } catch (NoSuchAlgorithmException e) {
                LOGGER.error("[AppMyKtServiceImpl] (save) NoSuchAlgorithmException : {}", e.getMessage());
                successYn = "N";
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("[AppMyKtServiceImpl] (save) Exception : {}", e.getMessage());
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                successYn = "N";
            }
        }



        return successYn;
    }
}
