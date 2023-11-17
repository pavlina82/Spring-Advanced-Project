package com.EShop.EShop.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/receipts")
public class ReceiptsController {

    @PostMapping("/create")
    public ModelAndView createReceipt(Long id) {

        return new ModelAndView("/receipts/my");
    }

    @GetMapping("/my")
    public ModelAndView getMyOrders(ModelAndView  modelAndView){

        return new ModelAndView("receipt/receipts");
    }
}
