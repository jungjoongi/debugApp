package com.weolbu.works.web.user.service;


import com.weolbu.works.web.user.dto.UserReqDto;

public interface UserService {
    public void saveUser(UserReqDto userReqDto);
    public void updateUser(UserReqDto userReqDto);
    public void deleteUser(Long id);
}
