package com.EShop.EShop.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/order")
public class OrderController {

    @GetMapping("/all")
    public ModelAndView getAllOrders(){

        return new ModelAndView("order/all-orders");
    }
}
