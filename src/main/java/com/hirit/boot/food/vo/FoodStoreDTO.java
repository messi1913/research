package com.hirit.boot.food.vo;

import com.querydsl.core.annotations.QueryProjection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.core.Relation;

@Data
@NoArgsConstructor
@Relation(collectionRelation = "foodStores")
public class FoodStoreDTO {

    private Integer id;
    private String storeName;
    private int rate;
    private String ownerName;
    private String foodTypeName;
    private int foodOrder;

    @QueryProjection
    public FoodStoreDTO(Integer id, String storeName, int rate, String ownerName, String foodTypeName, int foodOrder) {
        this.id = id;
        this.storeName = storeName;
        this.rate = rate;
        this.ownerName = ownerName;
        this.foodTypeName = foodTypeName;
        this.foodOrder = foodOrder;
    }
}
