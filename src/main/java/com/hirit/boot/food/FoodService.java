package com.hirit.boot.food;

import com.hirit.boot.enttiy.FoodStore;
import com.hirit.boot.enttiy.FoodType;
import com.hirit.boot.food.vo.FoodRetrieveCondition;
import com.hirit.boot.food.vo.FoodStoreDTO;
import com.hirit.boot.repository.FoodTypeRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.hirit.boot.repository.FoodStoreRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FoodService {

    @Qualifier(value = "foodDAOImpl")
    private final FoodDAO foodDAOImpl;
    private final FoodStoreRepository foodStoreRepository;
    private final FoodTypeRepository foodTypeRepository;
    private final ModelMapper modelMapper;

    public FoodStoreDTO getFoodStore(Integer id) {
        return foodDAOImpl.retrieveStore(id);
    }

    public FoodStoreDTO saveFoodStore(FoodStoreDTO foodStoreDTO) {
        String foodTypeName = foodStoreDTO.getFoodTypeName();
        FoodType foodType = foodTypeRepository.findByFoodTypeName(foodTypeName);
        FoodStore foodStore = modelMapper.map(foodStoreDTO, FoodStore.class);
        foodStore.setFoodType(foodType);
        FoodStore savedFoodStore = foodStoreRepository.save(foodStore);

        FoodStoreDTO savedFoodStoreDTO = modelMapper.map(savedFoodStore, FoodStoreDTO.class);
        savedFoodStoreDTO.setFoodTypeName(foodTypeName);
        return savedFoodStoreDTO;
    }

    public Page<FoodStoreDTO> getFoodStores(Pageable pageable) {
        return foodDAOImpl.retrieveStoresBetter(new FoodRetrieveCondition(), pageable);
    }
}
