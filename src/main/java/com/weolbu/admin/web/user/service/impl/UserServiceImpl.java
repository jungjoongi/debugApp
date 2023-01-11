package com.weolbu.admin.web.user.service.impl;

import com.weolbu.admin.config.exception.BusinessException;
import com.weolbu.admin.domain.auth.User;
import com.weolbu.admin.domain.auth.UserRepository;
import com.weolbu.admin.web.user.dto.UserReqDto;
import com.weolbu.admin.web.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    final private UserRepository userRepository;

    final private PasswordEncoder passwordEncoder;
    private static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public void saveUser(UserReqDto userReqDto) {

        userRepository.save(
                User.builder()
                        .email(userReqDto.getEmail())
                        .name(userReqDto.getName())
                        .password(passwordEncoder.encode(userReqDto.getEmail()))
                        .nickname(userReqDto.getNickname())
                        .role(userReqDto.getRoles())
                        .firstLoginYn("Y")
                        .build()
        );

    }

    @Override
    @Transactional
    public void updateUser(UserReqDto userReqDto) {

        User user = userRepository.findById(userReqDto.getId())
                .orElseThrow(() -> new BusinessException("사용자를 찾을 수 없습니다."));

        user.update(
                userReqDto.getName()
                ,passwordEncoder.encode(userReqDto.getPassword())
                ,userReqDto.getPicture()
                ,userReqDto.getNickname()
                ,userReqDto.getRoles()
        );

    }

    @Override
    public void deleteUser(UserReqDto userReqDto) {

        userRepository.findById(userReqDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        userRepository.deleteById(userReqDto.getId());
    }

}
