package com.hirit.boot.enttiy;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@Builder
@NoArgsConstructor @AllArgsConstructor
@ToString(of = {"id", "typeName", "order"})
public class FoodType {

    @Id @GeneratedValue
    @Column(name = "food_id")
    private Integer id;

    @Column(unique = true)
    private String typeName;

    private int foodOrder;

    @OneToMany(mappedBy = "foodType")
    private List<FoodStore> foodStoreList = new ArrayList<>();

}
