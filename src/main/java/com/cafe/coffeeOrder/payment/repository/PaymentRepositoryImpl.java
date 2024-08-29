package com.cafe.coffeeOrder.payment.repository;

import com.cafe.coffeeOrder.payment.domain.Payment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PaymentRepositoryImpl implements PaymentRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Optional<Payment> selectPaymentById(long id) {
        String query = "SELECT pay FROM Payment AS pay" +
                " JOIN FETCH pay.purchase AS purchase" +
                " WHERE pay.id = :id";

        Payment result = entityManager.createQuery(query, Payment.class)
                .setParameter("id", id)
                .getSingleResult();

        return Optional.ofNullable(result);
    }

    @Override
    public Payment insertPayment(Payment payment) {
        entityManager.persist(payment);
        return payment;
    }
}