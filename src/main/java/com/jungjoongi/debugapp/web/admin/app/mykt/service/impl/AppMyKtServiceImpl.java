package com.jungjoongi.debugapp.web.admin.app.mykt.service.impl;

import com.jungjoongi.debugapp.common.util.MD5Generator;
import com.jungjoongi.debugapp.domain.appmykt.AppMyKt;
import com.jungjoongi.debugapp.domain.appmykt.AppMyKtRespository;
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

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AppMyKtServiceImpl implements AppMyktService {

    private MyktMapper myktMapper;
    private AppMyKtRespository appMyKtRespository;

    public AppMyKtServiceImpl(MyktMapper myktMapper, AppMyKtRespository appMyKtRespository) {
        this.myktMapper = myktMapper;
        this.appMyKtRespository = appMyKtRespository;
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
        MultipartFile[] files = appMyKtVO.getFiles();
        List<FileUploadVO> fileUPloadVoList = new ArrayList<>();
        String result = "SUCCESS";
        if(files != null) {
            try {
                fileUPloadVoList.addAll(this.fileSave(files));
                myktMapper.save(appMyKtVO);
                for(FileUploadVO list : fileUPloadVoList) {
                    list.setContentId(appMyKtVO.getId());
                }
                myktMapper.saveFile(fileUPloadVoList);

            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("[AppMyKtServiceImpl] (save) Exception : {}", e.getMessage());
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                result = "FAIL";
            }
        }

        return result;
    }

    @Override
    @Transactional
    public String update(AppMyKtVO appMyKtVO) {
        MultipartFile[] files = appMyKtVO.getFiles();
        List<FileUploadVO> fileUPloadVoList = new ArrayList<>();
        String result = "SUCCESS";
        if(files != null) {
            try {
                appMyKtRespository.save(AppMyKt.builder()
                        .id(appMyKtVO.getId())
                        .env(appMyKtVO.getEnv())
                        .os(appMyKtVO.getOs())
                        .version(appMyKtVO.getVersion())
                        .comment(appMyKtVO.getComment())
                        .managerId(appMyKtVO.getManagerId())
                        .build());
                fileUPloadVoList.addAll(this.fileSave(files));
                for(FileUploadVO list : fileUPloadVoList) {
                    list.setContentId(appMyKtVO.getId());
                }
                myktMapper.deleteFile(appMyKtVO.getId());
                myktMapper.saveFile(fileUPloadVoList);

            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("[AppMyKtServiceImpl] (save) Exception : {}", e.getMessage());
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                result = "FAIL";
            }
        }

        return result;
    }

    private List<FileUploadVO> fileSave(MultipartFile[] files) {
        List<FileUploadVO> fileUPloadVoList = new ArrayList<>();
        String originFileName = "";
        String originalFileExtension = "";
        String encFileName = "";
        MD5Generator md5Generator = MD5Generator.getInstance();
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
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("[AppMyKtServiceImpl] (save) UnsupportedEncodingException : {}", e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("[AppMyKtServiceImpl] (save) NoSuchAlgorithmException : {}", e.getMessage());
        } catch (IOException e) {
            LOGGER.error("[AppMyKtServiceImpl] (save) IOException : {}", e.getMessage());
        } catch (Exception e) {
            LOGGER.error("[AppMyKtServiceImpl] (save) IOException : {}", e.getMessage());
        }

        return fileUPloadVoList;
    }

    @Override
    public String delete(AppMyKtVO appMyKtVO) {
        AppMyKt AppMyKtRequest = AppMyKt.builder().id(appMyKtVO.getId()).build();
        String result = "SUCCESS";
        try {
            appMyKtRespository.delete(AppMyKtRequest);
        } catch (IllegalArgumentException e) {
            result = "FAIL";
            LOGGER.error("[AppMyKtServiceImpl] (delete) IllegalArgumentException : {}", e.getMessage());

        } catch (Exception e) {
            result = "FAIL";
            LOGGER.error("[AppMyKtServiceImpl] (delete) Exception : {}", e.getMessage());
        }


        return result;
    }
}
