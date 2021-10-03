package com.jungjoongi.debugapp.web.admin.app.mykt.mapper;

import com.jungjoongi.debugapp.web.admin.app.mykt.domain.AppMyKtVO;
import com.jungjoongi.debugapp.web.admin.app.mykt.domain.FileUploadVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MyktMapper {
    public int save(AppMyKtVO appMyKtVO);
    public int saveFile(List<FileUploadVO> fileUploadVOList);
    public int deleteFile(Long id);
}
