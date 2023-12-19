package com.EShop.EShop.web.anotacions.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;



@Component
public class GreetingInterceptor implements HandlerInterceptor {

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception{
        if (modelAndView == null){
            modelAndView = new ModelAndView();
        } else  {
            LocalDateTime time = LocalDateTime.now();
            int hrs = time.getHour();
            if (hrs <= 12){
                modelAndView.addObject("greeting","Good morning");
            } else if (hrs <= 17) {
                modelAndView.addObject("greeting","Good afternoon");
            }else {
                modelAndView.addObject("greeting","Good evening");
            }

        }

    }
}
