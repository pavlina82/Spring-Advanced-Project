package com.EShop.EShop.config;

import com.EShop.EShop.web.anotacions.interceptors.FaviconInterceptor;
import com.EShop.EShop.web.anotacions.interceptors.GreetingInterceptor;
import com.EShop.EShop.web.anotacions.interceptors.TitleInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    private final TitleInterceptor titleInterceptor;
    private final FaviconInterceptor faviconInterceptor;
    private final GreetingInterceptor greetingInterceptor;

    @Autowired
    public WebMvcConfiguration(TitleInterceptor titleInterceptor, FaviconInterceptor faviconInterceptor, GreetingInterceptor greetingInterceptor) {
        this.titleInterceptor = titleInterceptor;
        this.faviconInterceptor = faviconInterceptor;
        this.greetingInterceptor = greetingInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){

        registry.addInterceptor(this.titleInterceptor);
        registry.addInterceptor(this.faviconInterceptor);
        registry.addInterceptor(this.greetingInterceptor);
    }
}
