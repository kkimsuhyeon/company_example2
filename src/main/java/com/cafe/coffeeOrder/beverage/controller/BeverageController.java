package com.cafe.coffeeOrder.beverage.controller;

import com.cafe.coffeeOrder.beverage.dto.RequestCreateBeverage;
import com.cafe.coffeeOrder.beverage.dto.RequestSettingCategory;
import com.cafe.coffeeOrder.beverage.dto.ResponseBeverageItem;
import com.cafe.coffeeOrder.beverage.service.BeverageService;
import com.cafe.coffeeOrder.common.dto.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/beverages")
@Validated
public class BeverageController {

    private final BeverageService beverageService;

    @Autowired
    public BeverageController(BeverageService beverageService) {
        this.beverageService = beverageService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<ResponseBeverageItem>>> getBeverages() {
        List<ResponseBeverageItem> result = beverageService.getBeverages();
        return ApiResponse.success(HttpStatus.OK, result);
    }

    @GetMapping(value = "/{beverageId}")
    public ResponseEntity<ApiResponse<ResponseBeverageItem>> getBeverage(@PathVariable(value = "beverageId") Long id) {
        ResponseBeverageItem result = beverageService.getBeverage(id);
        return ApiResponse.success(HttpStatus.OK, result);
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse<ResponseBeverageItem>> createBeverage(@Valid @RequestBody RequestCreateBeverage request) {
        ResponseBeverageItem result = beverageService.createBeverage(request);
        return ApiResponse.success(HttpStatus.CREATED, result);
    }

    @PostMapping(value = "/{beverageId}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse<ResponseBeverageItem>> modifyCategory(@PathVariable(value = "beverageId") Long id, @Valid @RequestBody RequestSettingCategory request) {
        ResponseBeverageItem result = beverageService.settingCategory(id, request.getCategoryId());
        return ApiResponse.success(HttpStatus.OK, result);
    }
}