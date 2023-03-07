package com.weolbu.works.web.user.controller;

import com.weolbu.works.config.auth.LoginUser;
import com.weolbu.works.config.auth.dto.SessionUser;
import com.weolbu.works.web.common.dto.ResponseCommonDto;
import com.weolbu.works.web.common.dto.ResponseDataCode;
import com.weolbu.works.web.user.dto.UserReqDto;
import com.weolbu.works.web.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserApiController {

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

    @DeleteMapping(value = {"/api/v1/user/delete/{id}"})
    public ResponseCommonDto delete(
            HttpServletRequest request
            , HttpServletResponse response
            , HttpSession session
            , @LoginUser SessionUser user
            , @PathVariable Long id) {

        userService.deleteUser(id);

        return ResponseCommonDto.builder()
                .responseDataCode(ResponseDataCode.SUCCESS)
                .build();
    }


}