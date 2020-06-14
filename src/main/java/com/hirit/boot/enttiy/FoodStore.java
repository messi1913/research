package com.hirit.boot.enttiy;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@Builder
@NoArgsConstructor @AllArgsConstructor
@ToString(of = {"id", "name", "rate", "address"})
public class FoodStore {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    private int rate;

    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_id")
    private FoodType foodType;


    public FoodStore(String name) {
        this(name, 0, null, null);
    }

    public FoodStore(String name, String address) {
        this(name, 0, address, null);
    }

    public FoodStore(String name, int rate, String address) {
        this(name, rate, address, null);
    }


    public FoodStore(String name, int rate, String address, FoodType foodType) {
        this.name = name;
        this.rate = rate;
        this.address = address;
        if (foodType != null) {
            changeFoodType(foodType);
        }
    }

    private void changeFoodType(FoodType foodType) {
        this.foodType = foodType;
        foodType.getFoodStoreList().add(this);
    }
}
