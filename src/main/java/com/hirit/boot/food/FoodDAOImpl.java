package com.hirit.boot.food;

import com.hirit.boot.food.vo.FoodRetrieveCondition;
import com.hirit.boot.food.vo.FoodStoreDTO;
import com.hirit.boot.food.vo.QFoodStoreDTO;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

import static com.hirit.boot.enttiy.QFoodStore.foodStore;
import static com.hirit.boot.enttiy.QFoodType.foodType;

@Repository(value = "foodDAOImpl")
@RequiredArgsConstructor
public class FoodDAOImpl implements FoodDAO {

    private final JPAQueryFactory query;

    private BooleanExpression storeNameContains(String storeName) {
        return Objects.isNull(storeName) ? null : foodStore.storeName.contains(storeName);
    }

    private BooleanExpression lessThan(int rate) {
        return rate <= 0 ? null : foodStore.rate.loe(rate);
    }


    @Override
    public Page<FoodStoreDTO> retrieveStores(FoodRetrieveCondition condition, Pageable pageable) {
        QueryResults<FoodStoreDTO> queryResults = query
            .select(new QFoodStoreDTO(
                foodStore.id,
                foodStore.storeName,
                foodStore.rate,
                foodStore.ownerName,
                foodType.foodTypeName,
                foodType.foodOrder))
            .from(foodStore)
            .join(foodStore.foodType, foodType)
            .where(
                storeNameContains(condition.getStoreName()),
                lessThan(condition.getRate()))
            .orderBy(foodStore.rate.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetchResults();

        List<FoodStoreDTO> contents = queryResults.getResults();
        long total = queryResults.getTotal();
        return new PageImpl<>(contents, pageable, total);
    }

    @Override
    public Page<FoodStoreDTO> retrieveStoresBetter(FoodRetrieveCondition condition, Pageable pageable) {
        List<FoodStoreDTO> contents = query
            .select(new QFoodStoreDTO(
                foodStore.id,
                foodStore.storeName,
                foodStore.rate,
                foodStore.ownerName,
                foodType.foodTypeName,
                foodType.foodOrder))
            .from(foodStore)
            .join(foodStore.foodType, foodType)
            .where(
                storeNameContains(condition.getStoreName()),
                lessThan(condition.getRate()))
            .orderBy(foodStore.rate.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        JPAQuery<FoodStoreDTO> countQuery = query
            .select(new QFoodStoreDTO(
                foodStore.id,
                foodStore.storeName,
                foodStore.rate,
                foodStore.ownerName,
                foodType.foodTypeName,
                foodType.foodOrder))
            .from(foodStore)
            .join(foodStore.foodType, foodType)
            .where(
                storeNameContains(condition.getStoreName()),
                lessThan(condition.getRate()));

        return PageableExecutionUtils.getPage(contents, pageable, countQuery::fetchCount);
    }

    @Override
    public FoodStoreDTO retrieveStore(Integer id) {
        return query
                .select(new QFoodStoreDTO(
                        foodStore.id,
                        foodStore.storeName,
                        foodStore.rate,
                        foodStore.ownerName,
                        foodType.foodTypeName,
                        foodType.foodOrder))
                .from(foodStore)
                .join(foodStore.foodType, foodType)
                .where(foodStore.id.eq(id))
                .fetchFirst();
    }
}
