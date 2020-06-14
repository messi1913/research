package com.hirit.boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hirit.boot.enttiy.FoodStore;

public interface FoodStoreRepository extends JpaRepository<FoodStore, Integer> {
}
