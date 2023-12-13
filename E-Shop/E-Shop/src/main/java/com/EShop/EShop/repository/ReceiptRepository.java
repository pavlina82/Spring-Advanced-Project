package com.EShop.EShop.repository;

import com.EShop.EShop.model.entity.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Long> {
    List<Receipt> findAllReceiptsByRecipient_UsernameOrderByIssuedOn(String username);
}
