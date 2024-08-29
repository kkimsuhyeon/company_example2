package com.cafe.coffeeOrder.purchase.service;

import com.cafe.coffeeOrder.beverage.domain.Beverage;
import com.cafe.coffeeOrder.beverage.exception.BeverageException;
import com.cafe.coffeeOrder.beverage.repository.BeverageRepository;
import com.cafe.coffeeOrder.common.dto.ApiResponse;
import com.cafe.coffeeOrder.common.exception.CustomException;
import com.cafe.coffeeOrder.payment.domain.constant.PaymentStatus;
import com.cafe.coffeeOrder.payment.dto.RequestRegistPayment;
import com.cafe.coffeeOrder.payment.exception.PaymentException;
import com.cafe.coffeeOrder.payment.service.PaymentService;
import com.cafe.coffeeOrder.purchase.domain.Purchase;
import com.cafe.coffeeOrder.purchase.dto.RequestCreatePurchase;
import com.cafe.coffeeOrder.purchase.dto.RequestPayPurchase;
import com.cafe.coffeeOrder.purchase.dto.ResponsePurchaseItem;
import com.cafe.coffeeOrder.purchase.exception.PurchaseException;
import com.cafe.coffeeOrder.purchase.repository.PurchaseRepository;
import com.cafe.coffeeOrder.receipt.domain.Receipt;
import com.cafe.coffeeOrder.receipt.exception.ReceiptException;
import com.cafe.coffeeOrder.receipt.repository.ReceiptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final ReceiptRepository receiptRepository;
    private final BeverageRepository beverageRepository;
    private final PaymentService paymentService;

    @Autowired
    public PurchaseServiceImpl(PurchaseRepository purchaseRepository, ReceiptRepository receiptRepository, BeverageRepository beverageRepository, PaymentService paymentService) {
        this.purchaseRepository = purchaseRepository;
        this.receiptRepository = receiptRepository;
        this.beverageRepository = beverageRepository;
        this.paymentService = paymentService;
    }

    @Override
    public ResponsePurchaseItem getPurchase(long id) {
        return purchaseRepository.selectPurchaseById(id)
                .map(ResponsePurchaseItem::fromEntity)
                .orElseThrow(() -> new CustomException(PurchaseException.NOT_FOUND));
    }

    @Override
    @Transactional
    public ResponsePurchaseItem createPurchase(RequestCreatePurchase request) {

        Receipt receipt = receiptRepository.selectReceiptById(request.getReceiptId())
                .orElseThrow(() -> new CustomException(ReceiptException.NOT_FOUND));

        Beverage beverage = beverageRepository.selectBeverageById(request.getBeverageId())
                .orElseThrow(() -> new CustomException(BeverageException.NOT_FOUND));

        Purchase newOrders = Purchase.of(receipt, beverage);
        purchaseRepository.insertPurchase(newOrders);

        return ResponsePurchaseItem.fromEntity(newOrders);
    }

    @Override
    public ResponsePurchaseItem payPurchase(RequestPayPurchase request) {
        Purchase purchase = purchaseRepository.selectPurchaseById(request.getPurchaseId())
                .orElseThrow(() -> new CustomException(PurchaseException.NOT_FOUND));

        if (purchase.getPrice() == request.getPrice()) {
            RequestRegistPayment requestRegistPayment = RequestRegistPayment.of(request.getPurchaseId(), request.getPrice(), request.getMethod(), PaymentStatus.SUCCESS);
            paymentService.registPayment(requestRegistPayment);
            purchase.success();
            return ResponsePurchaseItem.fromEntity(purchase);
        }

        RequestRegistPayment requestRegistPayment = RequestRegistPayment.of(request.getPurchaseId(), request.getPrice(), request.getMethod(), PaymentStatus.FAIL);
        paymentService.registPayment(requestRegistPayment);
        throw new CustomException(PaymentException.FAIL_PAY);
    }

    @Override
    public ResponsePurchaseItem sendBeverage(long id) {
        return purchaseRepository.selectPurchaseById(id).map((item) -> {
            item.complete();
            item.getReceipt().finish();
            return ResponsePurchaseItem.fromEntity(item);
        }).orElseThrow(() -> new CustomException(PurchaseException.NOT_FOUND));
    }
}