package com.cafe.coffeeOrder.receipt.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
public class RequestCreateReceipt {

    @NotNull
    private final long customerId;

    @NotNull
    private final List<Long> beverageIds;

    private RequestCreateReceipt(Builder builder) {
        this.customerId = builder.customerId;
        this.beverageIds = builder.beverageIds;
    }

    @Getter
    public static class Builder {
        private Long customerId;
        private List<Long> beverageIds;

        public Builder(Long customerId, List<Long> beverageIds) {
            this.customerId = customerId;
            this.beverageIds = beverageIds;
        }

        public Builder customerId(Long customerId) {
            this.customerId = customerId;
            return this;
        }

        public Builder name(List<Long> beverageIds) {
            this.beverageIds = beverageIds;
            return this;
        }

        public RequestCreateReceipt build() {
            return new RequestCreateReceipt(this);
        }

    }

}