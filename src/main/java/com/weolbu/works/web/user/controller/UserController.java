package com.weolbu.works.web.user.controller;

import com.weolbu.works.common.util.PagingUtil;
import com.weolbu.works.config.auth.LoginUser;
import com.weolbu.works.config.auth.dto.SessionUser;
import com.weolbu.works.config.exception.BusinessException;
import com.weolbu.works.domain.auth.Role;
import com.weolbu.works.domain.auth.User;
import com.weolbu.works.domain.auth.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {


    private final UserRepository userRepository;

    @GetMapping(value = {"list"})
    public String list(
            HttpServletRequest request
            , HttpServletResponse response
            , HttpSession session
            , @LoginUser SessionUser user
            , Model model
            , @PageableDefault(size = 10, direction = Sort.Direction.DESC, sort = "userId") Pageable pageable) {

        Role[] role = {Role.ADMIN, Role.MANAGER, Role.GUEST};
        Page<User> page = userRepository.findAllByRoleIn(role, pageable);
        model.addAttribute("userList", page.getContent());
        model.addAttribute("pagination", PagingUtil.pagingHtml(page, request.getRequestURI()));

        return "view/web/user/list";
    }
    @GetMapping(value = {"form/{id}"})
    public String update(
            HttpServletRequest request
            , HttpServletResponse response
            , HttpSession session
            , @LoginUser SessionUser user
            , Model model
            , @PathVariable long id) {

        model.addAttribute("dto", userRepository.findById(id)
                .orElseThrow(() -> new BusinessException("해당 게시물이 없습니다.")));


        return "view/web/user/form";
    }

    @GetMapping(value = {"form"})
    public String form(
            HttpServletRequest request
            , HttpServletResponse response
            , HttpSession session
            , @LoginUser SessionUser user
            , Model model) {

        return "view/web/user/form";
    }
}