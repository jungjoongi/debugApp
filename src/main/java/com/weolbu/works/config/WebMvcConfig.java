package com.weolbu.works.config;

import com.weolbu.works.config.auth.LoginUserArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.view.MustacheViewResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final com.weolbu.works.config.interceptor.ResponseInterceptor ResponseInterceptor;
    private final LoginUserArgumentResolver loginUserArgumentResolver;
    private final MustacheViewResolver mustacheViewResolver;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(ResponseInterceptor);
    }


    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(loginUserArgumentResolver);
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {

        registry.enableContentNegotiation(new MappingJackson2JsonView());
        registry.viewResolver(mustacheViewResolver);
    }

}