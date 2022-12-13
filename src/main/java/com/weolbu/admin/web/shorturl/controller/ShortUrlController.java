package com.weolbu.admin.web.shorturl.controller;

import com.weolbu.admin.common.util.PagingUtil;
import com.weolbu.admin.config.auth.LoginUser;
import com.weolbu.admin.config.auth.dto.SessionUser;
import com.weolbu.admin.domain.shortUrl.ShortUrl;
import com.weolbu.admin.domain.shortUrl.ShortUrlRepository;
import com.weolbu.admin.web.shorturl.dto.ShortUrlReqDto;
import com.weolbu.admin.web.shorturl.dto.ShortUrlResDto;
import com.weolbu.admin.web.shorturl.service.ShortUrlService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/shortUrl")
public class ShortUrlController {
    private static Logger log = LoggerFactory.getLogger(ShortUrlController.class);

    final private ShortUrlService shortUrlService;
    final private ShortUrlRepository shortUrlRepository;

    @RequestMapping(value = {"list"}, method= {RequestMethod.GET})
    public String list(
            HttpServletRequest request
            , HttpServletResponse response
            , HttpSession session
            , @LoginUser SessionUser user
            , Model model
            , @PageableDefault(size = 10, direction = Sort.Direction.DESC, sort = "shortUrlId") Pageable pageable
    ) {

        Page<ShortUrl> page = shortUrlRepository.findAll(pageable);
        model.addAttribute("content", page.getContent());
        model.addAttribute("html", PagingUtil.pagingHtml(page, request.getRequestURI()));

        return "view/web/shortUrl/list";
    }
    @RequestMapping(value = {"form"}, method= {RequestMethod.GET})
    public String form(HttpServletRequest request, HttpServletResponse response, HttpSession session, @LoginUser SessionUser user, Model model) {





        return "view/web/shortUrl/form";
    }

    @RequestMapping(value = {"save"}, method= {RequestMethod.POST})
    @Transactional
    public String save(HttpServletRequest request, HttpServletResponse response, HttpSession session, @LoginUser SessionUser user, Model model, ShortUrlReqDto shortUrlReqDto) {



        String shortUrl = shortUrlService.saveUrl(shortUrlReqDto);
        ShortUrlResDto resDto = new ShortUrlResDto();

        resDto.setShortUrl(shortUrl);
        model.addAttribute("res",resDto);
        model.addAttribute("result","SUCCESS");

        return "jsonview";
    }


}