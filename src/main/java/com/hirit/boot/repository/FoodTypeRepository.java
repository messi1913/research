package com.hirit.boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hirit.boot.enttiy.FoodType;

public interface FoodTypeRepository extends JpaRepository<FoodType, Integer> {

    FoodType findByFoodTypeName(String foodTypeName);
}
