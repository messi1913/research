package com.hirit.boot.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirit.boot.food.FoodRetrieveCondition;
import com.hirit.boot.food.FoodStoreDTO;
import com.hirit.boot.repository.FoodStoreRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/food")
@RequiredArgsConstructor
public class FoodController {

    private final FoodStoreRepository repository;

    @GetMapping("/foodStores")
    public Page<FoodStoreDTO> getFoodStores(FoodRetrieveCondition condition, Pageable pageable) {
        return repository.retrieveStores(condition, pageable);
    }

    @GetMapping("/foodStoresBetter")
    public Page<FoodStoreDTO> getFoodStoresBetter(FoodRetrieveCondition condition, Pageable pageable) {
        return repository.retrieveStoresBetter(condition, pageable);
    }

}
