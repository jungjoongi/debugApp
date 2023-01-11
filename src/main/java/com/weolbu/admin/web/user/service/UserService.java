package com.weolbu.admin.web.user.service;


import com.weolbu.admin.web.user.dto.UserReqDto;

public interface UserService {
    public void saveUser(UserReqDto userReqDto);
    public void updateUser(UserReqDto userReqDto);
    public void deleteUser(UserReqDto userReqDto);
}
