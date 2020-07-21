package com.hirit.boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hirit.boot.enttiy.FoodStore;
import com.hirit.boot.food.FoodDAO;

public interface FoodStoreRepository extends JpaRepository<FoodStore, Integer>, FoodDAO {

}
