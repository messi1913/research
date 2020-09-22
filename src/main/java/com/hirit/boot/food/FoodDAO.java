package com.hirit.boot.food;

import com.hirit.boot.food.vo.FoodRetrieveCondition;
import com.hirit.boot.food.vo.FoodStoreDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FoodDAO {

    Page<FoodStoreDTO> retrieveStores(FoodRetrieveCondition condition, Pageable pageable);

    Page<FoodStoreDTO> retrieveStoresBetter(FoodRetrieveCondition condition, Pageable pageable);

    FoodStoreDTO retrieveStore(Integer id);
}
