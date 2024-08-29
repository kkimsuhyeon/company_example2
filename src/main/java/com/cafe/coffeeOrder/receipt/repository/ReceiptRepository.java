package com.cafe.coffeeOrder.receipt.repository;

import com.cafe.coffeeOrder.receipt.domain.Receipt;

import java.util.List;
import java.util.Optional;

public interface ReceiptRepository {
    public List<Receipt> selectReceipts();

    public Optional<Receipt> selectReceiptById(Long id);

    public Receipt insertReceipt(Receipt receipt);
}