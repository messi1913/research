package com.hirit.boot.food;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FoodDAO {

    Page<FoodStoreDTO> retrieveStores(FoodRetrieveCondition condition, Pageable pageable);

    Page<FoodStoreDTO> retrieveStoresBetter(FoodRetrieveCondition condition, Pageable pageable);
}
