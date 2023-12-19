package com.EShop.EShop.web.anotacions.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import static com.EShop.EShop.constants.ValidationMessage.FAVICON;
import static com.EShop.EShop.constants.ValidationMessage.FAVICON_URL;

@Component
public class FaviconInterceptor implements HandlerInterceptor {

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) {
        if (modelAndView != null){
            modelAndView.addObject(FAVICON, FAVICON_URL);
        }

    }
}
