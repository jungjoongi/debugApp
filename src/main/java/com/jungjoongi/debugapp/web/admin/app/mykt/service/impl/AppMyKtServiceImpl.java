package com.jungjoongi.debugapp.web.admin.app.mykt.service.impl;

import com.jungjoongi.debugapp.common.util.MD5Generator;
import com.jungjoongi.debugapp.domain.appmykt.AppMyKt;
import com.jungjoongi.debugapp.domain.appmykt.AppMyKtRepository;
import com.jungjoongi.debugapp.domain.code.CodeInfo;
import com.jungjoongi.debugapp.web.admin.app.mykt.domain.AppMyKtVO;
import com.jungjoongi.debugapp.web.admin.app.mykt.domain.FileUploadVO;
import com.jungjoongi.debugapp.web.admin.app.mykt.mapper.MyktMapper;
import com.jungjoongi.debugapp.web.admin.app.mykt.service.AppMyktService;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AppMyKtServiceImpl implements AppMyktService {

    private MyktMapper myktMapper;
    private AppMyKtRepository appMyKtRepository;

    public AppMyKtServiceImpl(MyktMapper myktMapper, AppMyKtRepository appMyKtRepository) {
        this.myktMapper = myktMapper;
        this.appMyKtRepository = appMyKtRepository;
    }

    private static Logger LOGGER = LoggerFactory.getLogger(AppMyKtServiceImpl.class);
    @Value("${properties.filepath.app}")
    String filepath;
    @Value("${properties.filepath.ipa}")
    String ipaPath;
    @Value("${properties.filepath.appWeb}")
    String appWeb;
    @Value("${properties.root}")
    String root;


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

        appMyKtRepository.save(AppMyKt.builder()
                .id(appMyKtVO.getId())
                .env(appMyKtVO.getEnv())
                .os(appMyKtVO.getOs())
                .version(appMyKtVO.getVersion())
                .comment(appMyKtVO.getComment())
                .managerId(appMyKtVO.getManagerId())
                .build());

        if(files != null && files.length > 0) {
            try {
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

                if(originalFileExtension.contains("ipa")) {
                    this.plistMaker(encFileName, "com.kt.ollehcs");
                }

                fileUPloadVo.setOriginFileName(originFileName);
                fileUPloadVo.setFileName(encFileName);
                fileUPloadVo.setDownloadYn("Y");
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
            LOGGER.error("[AppMyKtServiceImpl] (save) Exception : {}", e.getMessage());
        }
        return fileUPloadVoList;
    }

    private void plistMaker(String targetFilepath, String packagePath) throws IOException {

        String readFile = "";
        BufferedReader br = null;
        BufferedWriter bw = null;
        String saveFilePath = filepath+targetFilepath.replace("ipa", "plist");
        try {
            br = new BufferedReader(new FileReader(new File(ipaPath)));
            bw = new BufferedWriter(new FileWriter(new File(saveFilePath)));

            while((readFile = br.readLine()) != null) {
                readFile = readFile.replace("${target}", root+appWeb+targetFilepath);
                readFile = readFile.replace("${package}", packagePath);
                bw.write(readFile + "\r\n");
            }

        } catch (FileNotFoundException e) {
            LOGGER.error("FileNotFoundException : {}", e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(bw != null) {
                bw.flush();
                bw.close();
            }
            if (br != null) {
                br.close();
            }
        }

    }

    @Override
    public String delete(AppMyKtVO appMyKtVO) {
        AppMyKt AppMyKtRequest = AppMyKt.builder().id(appMyKtVO.getId()).build();
        String result = "SUCCESS";
        try {
            appMyKtRepository.delete(AppMyKtRequest);
            myktMapper.deleteFile(appMyKtVO.getId());
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
