package com.cafe.coffeeOrder.beverageCategory.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class BeverageCategoryServiceImpl implements BeverageCategoryService {

}