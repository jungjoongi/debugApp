package com.weolbu.admin.web.user.controller;

import com.weolbu.admin.config.auth.LoginUser;
import com.weolbu.admin.config.auth.dto.SessionUser;
import com.weolbu.admin.web.common.dto.ResponseCommonDto;
import com.weolbu.admin.web.common.dto.ResponseDataCode;
import com.weolbu.admin.web.user.dto.UserReqDto;
import com.weolbu.admin.web.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
public class UserApiController {
    private static Logger log = LoggerFactory.getLogger(UserApiController.class);

    final private UserService userService;

    @PostMapping(value = {"/api/v1/user/save"})
    public ResponseCommonDto save(
            HttpServletRequest request
            , HttpServletResponse response
            , HttpSession session
            , @LoginUser SessionUser user
            , UserReqDto userReqDto) {

        userService.saveUser(userReqDto);

        return ResponseCommonDto.builder()
                .responseDataCode(ResponseDataCode.SUCCESS)
                .build();
    }

    @PutMapping(value = {"/api/v1/user/update"})
    public ResponseCommonDto update(
            HttpServletRequest request
            , HttpServletResponse response
            , HttpSession session
            , @LoginUser SessionUser user
            , UserReqDto userReqDto) {

        userService.updateUser(userReqDto);

        return ResponseCommonDto.builder()
                .responseDataCode(ResponseDataCode.SUCCESS)
                .build();
    }

    @DeleteMapping(value = {"/api/v1/user/delete"})
    public ResponseCommonDto delete(
            HttpServletRequest request
            , HttpServletResponse response
            , HttpSession session
            , @LoginUser SessionUser user
            , UserReqDto userReqDto) {

        userService.deleteUser(userReqDto);

        return ResponseCommonDto.builder()
                .responseDataCode(ResponseDataCode.SUCCESS)
                .build();
    }


}