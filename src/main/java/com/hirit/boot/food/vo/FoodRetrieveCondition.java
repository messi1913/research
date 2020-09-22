package com.hirit.boot.food.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
public class FoodRetrieveCondition {

    private String storeName;
    private int rate;
    private String storeTypeName;
}
