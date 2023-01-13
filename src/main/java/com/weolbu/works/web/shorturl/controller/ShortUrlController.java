package com.weolbu.works.web.shorturl.controller;

import com.weolbu.works.common.util.PagingUtil;
import com.weolbu.works.config.auth.LoginUser;
import com.weolbu.works.config.auth.dto.SessionUser;
import com.weolbu.works.config.exception.BusinessException;
import com.weolbu.works.domain.shortUrl.ShortUrl;
import com.weolbu.works.domain.shortUrl.ShortUrlRepository;
import com.weolbu.works.web.shorturl.service.ShortUrlService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@RequestMapping("/shortUrl")
public class ShortUrlController {
    private static Logger log = LoggerFactory.getLogger(ShortUrlController.class);

    final private ShortUrlService shortUrlService;
    final private ShortUrlRepository shortUrlRepository;

    final private String SHORT_URL_ROOT = "https://link.weolbu.com/";

    @RequestMapping(value = {"list"}, method= {RequestMethod.GET})
    public String list(
            HttpServletRequest request
            , HttpServletResponse response
            , HttpSession session
            , @LoginUser SessionUser user
            , Model model
            , @PageableDefault(size = 10, direction = Sort.Direction.DESC, sort = "shortUrlId") Pageable pageable) {

        Page<ShortUrl> page = shortUrlRepository.findAll(pageable);
        model.addAttribute("content", page.getContent());
        model.addAttribute("html", PagingUtil.pagingHtml(page, request.getRequestURI()));

        return "view/web/shortUrl/list";
    }
    @GetMapping(value = {"form/{id}"})
    public String update(
            HttpServletRequest request
            , HttpServletResponse response
            , HttpSession session
            , @LoginUser SessionUser user
            , Model model
            , @PathVariable long id) {

        model.addAttribute("dto", shortUrlRepository.findById(id).map(o -> {
            o.setShortUrl(SHORT_URL_ROOT+o.getShortUrl());
            return o;
        }).orElseThrow(() -> new BusinessException("해당 게시물이 없습니다.")));


        return "view/web/shortUrl/form";
    }

    @GetMapping(value = {"form"})
    public String form(
            HttpServletRequest request
            , HttpServletResponse response
            , HttpSession session
            , @LoginUser SessionUser user
            , Model model) {

        return "view/web/shortUrl/form";
    }
}