package com.cafe.coffeeOrder.beverage.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequestSettingCategory {

    @NotNull(message = "categoryId를 입력해주세요")
    private Long categoryId;

    private RequestSettingCategory(Builder builder) {
        this.categoryId = builder.categoryId;
    }

    public static class Builder {
        private final Long categoryId;

        public Builder(Long categoryId) {
            this.categoryId = categoryId;
        }

        public RequestSettingCategory build() {
            return new RequestSettingCategory(this);
        }
    }
}
