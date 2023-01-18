package com.weolbu.works.web.user.service.impl;

import com.weolbu.works.config.exception.BusinessException;
import com.weolbu.works.domain.auth.User;
import com.weolbu.works.domain.auth.UserRepository;
import com.weolbu.works.web.user.dto.UserReqDto;
import com.weolbu.works.web.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    final private UserRepository userRepository;

    final private PasswordEncoder passwordEncoder;
    final private String DEFAULT_PASSWORD = "a0123456789Z!";

    @Override
    public void saveUser(UserReqDto userReqDto) {

        if(userRepository.existsByEmail(userReqDto.getEmail())) {
            throw new BusinessException("중복된 이메일이 있습니다.\n다시 확인해주세요");
        }

        userRepository.save(
                User.builder()
                        .email(userReqDto.getEmail())
                        .name(userReqDto.getName())
                        .password(passwordEncoder.encode(userReqDto.getPassword()))
                        .nickname(userReqDto.getNickname())
                        .role(userReqDto.getRoleEnum())
                        .build()
        );

    }

    @Override
    @Transactional
    public void updateUser(UserReqDto userReqDto) {

        User user = userRepository.findById(userReqDto.getUserId())
                .orElseThrow(() -> new BusinessException("사용자를 찾을 수 없습니다."));


        log.debug("@@## pw : {}", userReqDto.getPassword());

        String password = DEFAULT_PASSWORD.equals(userReqDto.getPassword()) ?
                DEFAULT_PASSWORD : passwordEncoder.encode(userReqDto.getPassword());

        user.update(
                userReqDto.getName()
                , password
                ,userReqDto.getPicture()
                ,userReqDto.getNickname()
                ,userReqDto.getRoleEnum()
        );

    }

    @Override
    public void deleteUser(Long id) {

        userRepository.findById(id)
                .orElseThrow(() -> new BusinessException("사용자를 찾을 수 없습니다."));

        userRepository.deleteById(id);
    }

}
