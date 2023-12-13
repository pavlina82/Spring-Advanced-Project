package com.EShop.EShop.controller;

import com.EShop.EShop.exception.ReceiptNotFoundException;
import com.EShop.EShop.model.service.ReceiptServiceModel;
import com.EShop.EShop.model.view.ReceiptViewModel;
import com.EShop.EShop.service.ReceiptService;
import com.EShop.EShop.web.anotacions.PageTitle;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/receipts")
public class ReceiptsController extends BaseController {

    private final ReceiptService receiptService;
    private final ModelMapper modelMapper;

    @Autowired
    public ReceiptsController(ReceiptService receiptService, ModelMapper modelMapper) {
        this.receiptService = receiptService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView createReceipt(Long id, Principal principal) {

        receiptService.createReceipt(id, principal.getName());
        return super.redirect("/receipts/my");
    }

    @GetMapping("/my")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView getMyOrders(ModelAndView modelAndView, Principal principal) {

        List<ReceiptViewModel> myReceipts = mapReceiptServiceToViewModel(
                receiptService.findAllReceiptsByUsername(principal.getName()));

        modelAndView.addObject("receipt", myReceipts);
        return view("receipt/receipts", modelAndView);
    }


    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PageTitle("receipt")
    public ModelAndView getAllReceipts(ModelAndView modelAndView) {

        List<ReceiptViewModel> allReceipt = mapReceiptServiceToViewModel(receiptService.findAllReceipts());
        modelAndView.addObject("receipt", allReceipt);
        return view("receipt/receipts", modelAndView);

    }

    @GetMapping("/all/details/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PageTitle("Receipt details")
    public ModelAndView allReceiptDetails(@PathVariable Long id, ModelAndView modelAndView) {

        ReceiptViewModel receiptViewModel = modelMapper.map(receiptService.findReceiptById(id), ReceiptViewModel.class);
        modelAndView.addObject("receipt", modelAndView);
        return super.view("receipt/receipt-details", modelAndView);
    }

    @GetMapping("/my/details/{id}")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Receipt details")
    public ModelAndView myOrderDetails(@PathVariable Long id, ModelAndView modelAndView) {
        ReceiptServiceModel receipt = receiptService.findReceiptById(id);
        modelAndView.addObject("receipt", modelMapper.map(receipt, ReceiptViewModel.class));
        return super.view("receipt/receipt-details", modelAndView);

    }

    private List<ReceiptViewModel> mapReceiptServiceToViewModel(List<ReceiptServiceModel> receiptServiceModels) {
        return receiptServiceModels.stream()
                .map(product -> modelMapper.map(product, ReceiptViewModel.class))
                .collect(Collectors.toList());
    }

    @ExceptionHandler({ReceiptNotFoundException.class})
    public ModelAndView handleProductNotFound(ReceiptNotFoundException e) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("message", e.getMessage());
        modelAndView.addObject("statusCode", e.getStatusCode());

        return modelAndView;

    }
}
