//package com.hirit.boot.food;
//
//
//import java.util.List;
//
//import javax.transaction.Transactional;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.jpa.repository.query.JpaQueryCreator;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import com.hirit.boot.enttiy.FoodStore;
//import com.hirit.boot.enttiy.FoodType;
//import com.hirit.boot.repository.FoodStoreRepository;
//import com.hirit.boot.repository.FoodTypeRepository;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@Transactional
//public class FoodServiceTest {
//
//    @Autowired
//    JPAQueryFactory factory;
//
//    @Autowired
//    FoodStoreRepository foodStoreRepository;
//
//    @Autowired
//    FoodTypeRepository foodTypeRepository;
//
//
//    @Before
//    public void initData() {
//
//
//
//
//    }
//
//    @Test
//    public void select() {
//        FoodType korean = FoodType.builder()
//            .foodOrder(1)
//            .typeName("한식")
//            .build();
//
//        FoodType western = FoodType.builder()
//            .foodOrder(2)
//            .typeName("양식")
//            .build();
//
//        FoodType chinese = FoodType.builder()
//            .foodOrder(3)
//            .typeName("중식")
//            .build();
//
//        foodTypeRepository.saveAll(List.of(korean, western, chinese));
//
//        FoodStore foodStore1 = FoodStore.builder()
//            .name("삼겹살")
//            .address("수원")
//            .rate(9)
//            .foodType(korean)
//            .build();
//
//        FoodStore foodStore2 = FoodStore.builder()
//            .name("닭갈비")
//            .address("수원")
//            .rate(2)
//            .foodType(korean)
//            .build();
//
//        FoodStore foodStore3 = FoodStore.builder()
//            .name("부대찌개")
//            .address("수원")
//            .rate(3)
//            .foodType(korean)
//            .build();
//
//        FoodStore foodStore4 = FoodStore.builder()
//            .name("순대국밥")
//            .address("수")
//            .rate(4)
//            .foodType(korean)
//            .build();
//
//        FoodStore foodStore5 = FoodStore.builder()
//            .name("소고기")
//            .address("수원")
//            .rate(5)
//            .foodType(korean)
//            .build();
//
//        FoodStore foodStore6 = FoodStore.builder()
//            .name("스파게티")
//            .address("수원")
//            .rate(6)
//            .foodType(western)
//            .build();
//
//        FoodStore foodStore7 = FoodStore.builder()
//            .name("피자")
//            .address("수원")
//            .rate(7)
//            .foodType(western)
//            .build();
//
//        FoodStore foodStore8 = FoodStore.builder()
//            .name("중국집")
//            .address("수원")
//            .rate(8)
//            .foodType(western)
//            .build();
//
//        FoodStore foodStore9 = FoodStore.builder()
//            .name("중국집2")
//            .address("수원")
//            .rate(9)
//            .foodType(chinese)
//            .build();
//
//        FoodStore foodStore10 = FoodStore.builder()
//            .name("중국집3")
//            .address("수원")
//            .rate(10)
//            .foodType(chinese)
//            .build();
//
//        foodStoreRepository.saveAll(List.of(foodStore1, foodStore2, foodStore3, foodStore4, foodStore5, foodStore6, foodStore7, foodStore8, foodStore9, foodStore10));
//
//    }
//
//}