package com.cafe.coffeeOrder.receipt.repository;

import com.cafe.coffeeOrder.purchase.domain.Purchase;
import com.cafe.coffeeOrder.receipt.domain.Receipt;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ReceiptRepositoryImpl implements ReceiptRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Receipt> selectReceipts() {
        String query = "SELECT r FROM Receipt AS r";

        return entityManager.createQuery(query, Receipt.class).getResultList();
    }

    @Override
    public Optional<Receipt> selectReceiptById(Long id) {
        String query = "SELECT r FROM Receipt AS r" +
                " INNER JOIN FETCH r.customer AS c" +
                " WHERE r.id = :id";

        String purchaseQuery = "SELECT purchase FROM Purchase AS purchase" +
                " LEFT JOIN FETCH purchase.payments AS pay" +
                " LEFT JOIN FETCH purchase.beverage AS b" +
                " WHERE purchase.receipt.id = :receiptId";

        List<Receipt> result = entityManager.createQuery(query, Receipt.class).setParameter("id", id).getResultList();
        result.forEach(receipt -> {

                    entityManager.createQuery(purchaseQuery, Purchase.class)
                            .setParameter("receiptId", receipt.getId())
                            .getResultList()
                            .forEach((receipt::addPurchase));

                }

        );

        return result.stream().findAny();
    }

    @Override
    public Receipt insertReceipt(Receipt receipt) {
        entityManager.persist(receipt);

        return receipt;
    }
}
