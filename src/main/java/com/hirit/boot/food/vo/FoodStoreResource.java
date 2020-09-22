package com.hirit.boot.food.vo;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

public class FoodStoreResource extends Resource<FoodStoreDTO> {

    public FoodStoreResource(FoodStoreDTO content, Link... links) {
        super(content, links);
    }
}
