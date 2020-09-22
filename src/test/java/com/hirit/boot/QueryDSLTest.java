//package com.hirit.boot;
//
//import java.util.List;
//import java.util.Objects;
//
//import javax.persistence.EntityManager;
//import javax.transaction.Transactional;
//
//import org.assertj.core.api.AssertionsForClassTypes;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Commit;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import com.hirit.boot.enttiy.FoodStore;
//import com.hirit.boot.enttiy.FoodType;
//import com.hirit.boot.enttiy.QFoodStore;
//import com.hirit.boot.enttiy.QFoodType;
//import com.hirit.boot.food.vo.FoodStoreDTO;
//import com.hirit.boot.food.QFoodStoreDTO;
//import com.hirit.boot.repository.FoodStoreRepository;
//import com.hirit.boot.repository.FoodTypeRepository;
//import com.querydsl.core.BooleanBuilder;
//import com.querydsl.core.QueryResults;
//import com.querydsl.core.Tuple;
//import com.querydsl.core.types.dsl.BooleanExpression;
//import com.querydsl.core.types.dsl.CaseBuilder;
//import com.querydsl.core.types.dsl.Expressions;
//import com.querydsl.jpa.JPAExpressions;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//
//import static com.hirit.boot.enttiy.QFoodStore.*;
//import static com.hirit.boot.enttiy.QFoodType.*;
//import static org.assertj.core.api.AssertionsForClassTypes.fail;
//import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@Transactional
//public class QueryDSLTest {
//
//    @Autowired
//    FoodTypeRepository foodTypeRepository;
//    @Autowired
//    FoodStoreRepository foodStoreRepository;
//    @Autowired
//    JPAQueryFactory query;
//
//    @Test
//    public void setData() {
//        FoodType korean = new FoodType("한식", 1);
//        FoodType western = new FoodType("양식", 2);
//        FoodType chinese = new FoodType("중식", 3);
//
//        foodTypeRepository.saveAll(List.of(korean, western, chinese));
//
//        FoodStore foodStore1 = new FoodStore("삼겹살", 9, "sangmessi", korean);
//        FoodStore foodStore2 = new FoodStore("닭갈비", 2, "sangmessi", korean);
//        FoodStore foodStore3 = new FoodStore("부대찌개", 3, "lake", korean);
//        FoodStore foodStore4 = new FoodStore("순대국밥", 4, "lake", korean);
//        FoodStore foodStore5 = new FoodStore("소고기", 5, "lake", korean);
//        FoodStore foodStore6 = new FoodStore("스파게티", 6, "sangmessi", western);
//        FoodStore foodStore7 = new FoodStore("피자", 7, "sangmessi", western);
//        FoodStore foodStore8 = new FoodStore("중국집", 8, "hong", chinese);
//        FoodStore foodStore9 = new FoodStore("중국집2", 9, "hong", chinese);
//        FoodStore foodStore10 = new FoodStore("중국집3", 10, "hong", chinese);
//
//        foodStoreRepository.saveAll(List.of(foodStore1, foodStore2, foodStore3, foodStore4, foodStore5, foodStore6, foodStore7, foodStore8, foodStore9, foodStore10));
//
//    }
//
//    @Test
//    public void 기본쿼리() {
//        List<FoodStore> results = query
//            .selectFrom(foodStore)
//            .fetch();
//
//        assertThat(results.size()).isEqualTo(10);
//
//    }
//
//    @Test
//    public void 기본쿼리_조건절() {
//        List<FoodStore> results = query
//            .selectFrom(foodStore)
//            .where(foodStore.rate.goe(5))
//            .fetch();
//
//        assertThat(results.size()).isEqualTo(7);
//
//    }
//
//    @Test
//    public void 기본쿼리_조건절2() {
//        List<FoodStore> results = query
//            .selectFrom(foodStore)
//            .where(
//                foodStore.rate.goe(5),
//                foodStore.storeName.startsWith("삼")
//                )
//            .fetch();
//
//        assertThat(results.size()).isEqualTo(1);
//    }
//
//    @Test
//    public void 기본쿼리_정렬() {
//        List<FoodStore> results = query
//            .selectFrom(foodStore)
//            .orderBy(foodStore.rate.desc())
//            .fetch();
//
//        assertThat(results.size()).isEqualTo(10);
//        assertThat(results.get(0).getRate()).isEqualTo(10);
//    }
//
//    @Test
//    public void 기본쿼리_페이징() {
//        QueryResults<FoodStore> fetchResults = query
//            .selectFrom(foodStore)
//            .offset(1)
//            .limit(3)
//            .fetchResults();
//
//        List<FoodStore> results = fetchResults.getResults();
//        long limit = fetchResults.getLimit();
//        long offset = fetchResults.getOffset();
//        long total = fetchResults.getTotal();
//
//        System.out.println("total = " + total);
//        System.out.println("offset = " + offset);
//        System.out.println("limit = " + limit);
//
//        assertThat(results.size()).isEqualTo(3);
//    }
//
//    @Test
//    public void join() {
//        List<FoodStore> fetch = query
//            .selectFrom(foodStore)
//            .join(foodStore.foodType, foodType)
//            .fetch();
//
//        fetch.forEach(System.out::println);
//    }
//
//    @Test
//    public void 연관관계_없는_조인() {
//        List<FoodStore> result = query
//            .select(foodStore)
//            .from(foodStore, foodType)
//            .where(foodStore.rate.eq(foodType.foodOrder))
//            .fetch();
//
//        assertThat(result.size()).isEqualTo(2);
//    }
//
//    @Test
//    public void join_on() {
//        List<FoodStore> result = query
//            .select(foodStore)
//            .from(foodStore)
//            .join(foodType).on(foodType.foodOrder.eq(foodStore.rate))
//            .fetch();
//
//        assertThat(result.size()).isEqualTo(2);
//
//    }
//
//    @Test
//    public void left_join() {
//        List<Tuple> result = query
//            .select(foodStore, foodType)
//            .from(foodStore)
//            .leftJoin(foodType).on(foodType.foodOrder.eq(foodStore.rate))
//            .fetch();
//
//        AssertionsForClassTypes.assertThat(result.size()).isEqualTo(10);
//
//        result.forEach(System.out::println);
//
//    }
//
//    @Test
//    public void subquery() {
//        List<FoodStore> result = query
//            .selectFrom(foodStore)
//            .where(foodStore.rate.in(
//                JPAExpressions
//                    .select(foodType.foodOrder.max())
//                    .from(foodType)
//            ))
//            .fetch();
//
//        assertThat(result.size()).isEqualTo(1);
//    }
//
//    @Test
//    public void caseQuery() {
//        List<String> fetch = query
//            .select(
//                foodStore.rate
//                    .when(10).then("존맛탱")
//                    .when(9).then("맛남")
//                    .otherwise("그럭저럭"))
//            .from(foodStore)
//            .orderBy(foodStore.rate.desc())
//            .fetch();
//
//        System.out.println("fetch = " + fetch);
//
//        List<String> fetch2 = query
//            .select(new CaseBuilder()
//                    .when(foodStore.rate.goe(7)).then("존맛탱")
//                    .when(foodStore.rate.goe(4)).then("맛남")
//                    .otherwise("그럭저럭"))
//            .from(foodStore)
//            .orderBy(foodStore.rate.desc())
//            .fetch();
//
//        System.out.println("fetch2 = " + fetch2);
//    }
//
//    @Test
//    public void 내가_원하는_객체리턴() {
//        List<FoodStoreDTO> foodStoreDTOList = query
//            .select(new QFoodStoreDTO(
//                foodStore.storeName,
//                foodStore.rate,
//                foodStore.ownerName,
//                foodType.foodTypeName,
//                foodType.foodOrder))
//            .from(foodStore)
//            .join(foodStore.foodType, foodType)
//            .fetch();
//
//        foodStoreDTOList.forEach(System.out::println);
//    }
//
//    @Test
//    public void dynamicQuery() {
//        List<FoodStoreDTO> 삼겹살 = 동적쿼리만들기("삼", 0);
//        List<FoodStoreDTO> lessThanFive = 동적쿼리만들기("대", 5);
//
//        assertThat(삼겹살.size()).isEqualTo(1);
//        assertThat(lessThanFive.size()).isEqualTo(2);
//        assertThat(삼겹살).extracting("storeName").containsExactly("삼겹살");
//    }
//
//    private List<FoodStoreDTO> 동적쿼리만들기(String storeName, int rate) {
//
//        BooleanBuilder builder = new BooleanBuilder();
//        if(!Objects.isNull(storeName)) {
//            builder.and(foodStore.storeName.contains(storeName));
//        }
//        if(rate > 0) {
//            builder.and(foodStore.rate.loe(rate));
//        }
//
//
//        return query
//            .select(new QFoodStoreDTO(
//                foodStore.storeName,
//                foodStore.rate,
//                foodStore.ownerName,
//                foodType.foodTypeName,
//                foodType.foodOrder))
//            .from(foodStore)
//            .join(foodStore.foodType, foodType)
//            .where(builder)
//            .fetch();
//    }
//
//    @Test
//    public void dynamicQuery2() {
//        List<FoodStoreDTO> 삼겹살 = 동적쿼리만들기2("삼", 0);
//        List<FoodStoreDTO> lessThanFive = 동적쿼리만들기2("대", 5);
//
//        assertThat(삼겹살.size()).isEqualTo(1);
//        assertThat(lessThanFive.size()).isEqualTo(2);
//        assertThat(삼겹살).extracting("storeName").containsExactly("삼겹살");
//    }
//
//    private List<FoodStoreDTO> 동적쿼리만들기2(String storeName, int rate) {
//        return query
//            .select(new QFoodStoreDTO(
//                foodStore.storeName,
//                foodStore.rate,
//                foodStore.ownerName,
//                foodType.foodTypeName,
//                foodType.foodOrder))
//            .from(foodStore)
//            .join(foodStore.foodType, foodType)
//            .where(
//                storeNameContains(storeName),
//                lessThan(rate))
//            .fetch();
//    }
//
//    private BooleanExpression storeNameContains(String storeName) {
//        return Objects.isNull(storeName) ? null : foodStore.storeName.contains(storeName);
//    }
//
//    private BooleanExpression lessThan(int rate) {
//        return rate <= 0 ? null : foodStore.rate.loe(rate);
//    }
//
//    @Test
//    public void 집합() {
//        List<Tuple> fetch = query
//            .select(
//                foodStore.rate.max().as("max"),
//                foodStore.rate.avg().as("avg"),
//                foodStore.rate.sum().as("sum"))
//            .from(foodStore)
//            .fetch();
//
//        Tuple tuple = fetch.get(0);
//        System.out.println("tuple = " + tuple);
//    }
//
//    @Autowired
//    EntityManager em;
//
//    @Test
//    @Commit
//    public void update() {
//
//        List<FoodStore> fetch = query.selectFrom(foodStore).fetch();
//
//        System.out.println("fetch.get(0).getOwnerName() = " + fetch.get(0).getOwnerName());
//
//        long count = query
//            .update(foodStore)
//            .set(foodStore.ownerName, "najung")
//            .execute();
//
//        System.out.println("count = " + count);
//
//        em.flush();
//        em.clear();
//
//        List<FoodStore> fetch2 = query.selectFrom(foodStore).fetch();
//        fetch2.forEach(System.out::println);
//    }
//
//
//
//
//
//}
