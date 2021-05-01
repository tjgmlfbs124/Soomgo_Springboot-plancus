package com.milestone.plancus.Interceptor;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class Config implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SigninInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns( // static 폴더
                        "/css/**",
                        "/fonts/**",
                        "/images/**",
                        "/js/**",
                        "/dev/**",
                        "/vendor/**"
                )
                .excludePathPatterns(
                        "/",
                        "/signin"
                ); // rest api;
    }
}
