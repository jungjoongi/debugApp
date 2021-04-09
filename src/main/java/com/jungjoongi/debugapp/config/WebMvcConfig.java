package com.jungjoongi.debugapp.config;

import com.jungjoongi.debugapp.config.auth.LoginUserArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.view.MustacheViewResolver;
import org.springframework.context.annotation.Bean;
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

    private final com.jungjoongi.debugapp.config.interceptor.CsrfInterceptor CsrfInterceptor;
    private final com.jungjoongi.debugapp.config.interceptor.ResponseInterceptor ResponseInterceptor;
    private final LoginUserArgumentResolver loginUserArgumentResolver;
    private final MustacheViewResolver mustacheViewResolver;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(CsrfInterceptor);
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


/*    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {

        registry.enableContentNegotiation(new MappingJackson2JsonView());
    }*/

//    @Bean
//    public MappingJackson2JsonView jsonView() {
//        return new MappingJackson2JsonView();
//    }


/*    @Bean
    public MappingJackson2JsonView jsonView() {
        return new MappingJackson2JsonView();
    }*/
}
