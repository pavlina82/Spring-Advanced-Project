package com.EShop.EShop.service.impl;

import com.EShop.EShop.model.entity.Offer;
import com.EShop.EShop.model.entity.Product;
import com.EShop.EShop.model.service.OfferServiceModel;
import com.EShop.EShop.model.service.ProductServiceModel;
import com.EShop.EShop.repository.OfferRepository;
import com.EShop.EShop.service.OfferService;
import com.EShop.EShop.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;
    private final ProductService productService;
    private final ModelMapper modelMapper;

    @Autowired
    public OfferServiceImpl(OfferRepository offerRepository, ProductService productService, ModelMapper modelMapper) {
        this.offerRepository = offerRepository;
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<OfferServiceModel> findAllOffers() {

        return this.offerRepository.findAll().stream()
                .map(offer -> this.modelMapper.map(offer, OfferServiceModel.class))
                .collect(Collectors.toList());

    }

    @Scheduled(fixedRate = 300000)
    private void generateOffers() {
        this.offerRepository.deleteAll();
        List<ProductServiceModel> products = this.productService
                .findAllProducts().stream()
                .filter(product -> !product.isDeleted())
                .filter(product -> product.getCategories().stream().anyMatch(category -> !category.isDeleted()))
                .toList();

        if (products.isEmpty()) {
            return;
        }
        int n = Math.min(products.size(), 10);
        Random random = new Random();
        List<Offer> offers = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Offer offer = new Offer();
            offer.setProduct(this.modelMapper.map(products.get(random.nextInt(products.size())), Product.class));
            offer.setPrice(offer.getProduct().getPrice().multiply(new BigDecimal("0.75")));

            if (offers.stream().filter(offer1 -> offer1.getProduct().getId().equals(offer.getProduct().getId())).count() == 0) {
                offers.add(offer);
            }

        }
        this.offerRepository.saveAll(offers);
    }
}
