package com.cafe.coffeeOrder.receipt.service;

import com.cafe.coffeeOrder.beverage.domain.Beverage;
import com.cafe.coffeeOrder.beverage.exception.BeverageException;
import com.cafe.coffeeOrder.beverage.repository.BeverageRepository;
import com.cafe.coffeeOrder.common.exception.CustomException;
import com.cafe.coffeeOrder.customer.domain.Customer;
import com.cafe.coffeeOrder.customer.exception.CustomerException;
import com.cafe.coffeeOrder.customer.repository.CustomerRepository;
import com.cafe.coffeeOrder.purchase.domain.Purchase;
import com.cafe.coffeeOrder.purchase.dto.RequestCreatePurchase;
import com.cafe.coffeeOrder.purchase.dto.RequestPayPurchase;
import com.cafe.coffeeOrder.purchase.exception.PurchaseException;
import com.cafe.coffeeOrder.purchase.repository.PurchaseRepository;
import com.cafe.coffeeOrder.purchase.service.PurchaseService;
import com.cafe.coffeeOrder.receipt.domain.Receipt;
import com.cafe.coffeeOrder.receipt.dto.RequestCreateReceipt;
import com.cafe.coffeeOrder.receipt.dto.RequestPayReceipt;
import com.cafe.coffeeOrder.receipt.dto.ResponseReceiptItem;
import com.cafe.coffeeOrder.receipt.exception.ReceiptException;
import com.cafe.coffeeOrder.receipt.repository.ReceiptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
public class ReceiptServiceImpl implements ReceiptService {

    private final ReceiptRepository receiptRepository;
    private final CustomerRepository customerRepository;
    private final BeverageRepository beverageRepository;
    private final PurchaseRepository purchaseRepository;
    private final PurchaseService purchaseService;

    @Autowired
    public ReceiptServiceImpl(ReceiptRepository receiptRepository,
                              CustomerRepository customerRepository,
                              PurchaseRepository purchaseRepository,
                              BeverageRepository beverageRepository,
                              PurchaseService purchaseService) {
        this.receiptRepository = receiptRepository;
        this.customerRepository = customerRepository;
        this.purchaseRepository = purchaseRepository;
        this.beverageRepository = beverageRepository;
        this.purchaseService = purchaseService;
    }

    @Override
    public ResponseReceiptItem getReceipt(long id) {
        Receipt receipt = receiptRepository.selectReceiptById(id)
                .orElseThrow(() -> new CustomException(ReceiptException.NOT_FOUND));

        return ResponseReceiptItem.fromEntity(receipt);
    }

    @Override
    @Transactional
    public ResponseReceiptItem createReceipt(RequestCreateReceipt request) {
        Customer customer = customerRepository.selectCustomerById(request.getCustomerId())
                .orElseThrow(() -> new CustomException(CustomerException.NOT_FOUND));
        Receipt newReceipt = Receipt.of(customer);

        Receipt receipt = receiptRepository.insertReceipt(newReceipt);

        request.getBeverageIds().forEach(beverageId -> {
            Beverage beverage = beverageRepository.selectBeverageById(beverageId)
                    .orElseThrow(() -> new CustomException(BeverageException.NOT_FOUND));
            purchaseRepository.insertPurchase(Purchase.of(receipt, beverage));
        });

        return ResponseReceiptItem.fromEntity(receipt);
    }

    @Override
    public ResponseReceiptItem payReceipt(RequestPayReceipt request) {

        Receipt receipt = receiptRepository.selectReceiptById(request.getReceiptId()).orElseThrow(() -> new CustomException(ReceiptException.NOT_FOUND));

        int totalPrice = 0;

        for (Long purchaseId : request.getPurchaseIds()) {
            Purchase purchase = purchaseRepository.selectPurchaseById(purchaseId).orElseThrow(() -> new CustomException(PurchaseException.NOT_FOUND));
            totalPrice = totalPrice + purchase.getPrice();
        }

        if (request.getPrice() == totalPrice) {
            for (Long purchaseId : request.getPurchaseIds()) {
                receipt.getPurchases()
                        .stream()
                        .filter((purchase -> purchase.getId().equals(purchaseId)))
                        .forEach((purchase -> {
                            RequestPayPurchase requestPayPurchase = RequestPayPurchase.of(purchaseId, purchase.getPrice(), request.getMethod());
                            purchaseService.payPurchase(requestPayPurchase);
                        }));
            }
            return ResponseReceiptItem.fromEntity(receipt);
        }

        for (Long purchaseId : request.getPurchaseIds()) {
            receipt.getPurchases()
                    .stream()
                    .filter((purchase -> purchase.getId().equals(purchaseId)))
                    .forEach((purchase -> {
                        RequestPayPurchase requestPayPurchase = RequestPayPurchase.of(purchaseId, 0, request.getMethod());
                        purchaseService.payPurchase(requestPayPurchase);
                    }));
        }
        return null;
    }
}