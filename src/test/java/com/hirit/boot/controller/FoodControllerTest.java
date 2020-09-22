package com.hirit.boot.controller;


import com.hirit.boot.account.service.AccountService;
import com.hirit.boot.common.CommonControllerTest;
import com.hirit.boot.config.Oauth2Properties;
import com.hirit.boot.enttiy.Account;
import com.hirit.boot.enttiy.FoodStore;
import com.hirit.boot.enttiy.FoodType;
import com.hirit.boot.food.vo.FoodStoreDTO;
import com.hirit.boot.repository.AccountRepository;
import com.hirit.boot.repository.FoodStoreRepository;
import com.hirit.boot.repository.FoodTypeRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.headers.HeaderDescriptor;
import org.springframework.restdocs.snippet.Snippet;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.util.Jackson2JsonParser;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.MediaTypes.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FoodControllerTest extends CommonControllerTest {

    @Autowired
    FoodStoreRepository foodStoreRepository;
    @Autowired
    FoodTypeRepository foodTypeRepository;
    @Autowired
    Oauth2Properties properties;
    @Autowired
    PasswordEncoder encoder;


    @Before
    public void init() {
        FoodType korean = new FoodType("한식", 1);
        FoodType western = new FoodType("양식", 2);
        FoodType chinese = new FoodType("중식", 3);

        foodTypeRepository.saveAll(List.of(korean, western, chinese));

        FoodStore foodStore1 = new FoodStore("삼겹살", 9, "sangmessi", korean);
        FoodStore foodStore2 = new FoodStore("닭갈비", 2, "sangmessi", korean);
        FoodStore foodStore3 = new FoodStore("부대찌개", 3, "lake", korean);
        FoodStore foodStore4 = new FoodStore("순대국밥", 4, "lake", korean);
        FoodStore foodStore5 = new FoodStore("소고기", 5, "lake", korean);
        FoodStore foodStore6 = new FoodStore("스파게티", 6, "sangmessi", western);
        FoodStore foodStore7 = new FoodStore("피자", 7, "sangmessi", western);
        FoodStore foodStore8 = new FoodStore("중국집", 8, "hong", chinese);
        FoodStore foodStore9 = new FoodStore("중국집2", 9, "hong", chinese);
        FoodStore foodStore10 = new FoodStore("중국집3", 10, "hong", chinese);

        foodStoreRepository.saveAll(List.of(foodStore1, foodStore2, foodStore3, foodStore4, foodStore5, foodStore6, foodStore7, foodStore8, foodStore9, foodStore10));
    }

    private String getBearerToken() throws Exception {
        return "Bearer " + getAccessToken();
    }

    private String getAccessToken() throws Exception {
        ResultActions perform = this.mockMvc.perform(post("/oauth/token")
                .with(httpBasic(properties.getClientId(), properties.getClientSecret()))
                .param("username", "messi1913@gmail.com")
                .param("password", "12345")
                .param("grant_type", "password"))
                .andDo(print());

        var responseBody = perform.andReturn().getResponse().getContentAsString();
        Jackson2JsonParser parser = new Jackson2JsonParser();
        return parser.parseMap(responseBody).get("access_token").toString();
    }


    @Test
    public void 식당정보_한건조회() throws Exception {
        //Given
        List<FoodStore> allFoodStore = foodStoreRepository.findAll();
        FoodStore foodStore = allFoodStore.get(0);

        //When
        mockMvc.perform(get("/food/foodStores/{id}", foodStore.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("storeName").exists())
                .andExpect(jsonPath("rate").exists())
                .andExpect(jsonPath("ownerName").exists())
                // Create Document
                .andDo(document("get-foodStore",
                        links(
                                linkWithRel("self").description("Link to self information"),
                                linkWithRel("profile").description("Link to profile"),
                                linkWithRel("update-foodStore").description("Link to update foodstore information")
                        ),
                        responseFields(
                                fieldWithPath("id").type(Integer.class).description("Identification of foodstore"),
                                fieldWithPath("storeName").type(String.class).description("Name of store"),
                                fieldWithPath("rate").type(Integer.class).description("Rate food of store"),
                                fieldWithPath("ownerName").type(String.class).description("Name of owner"),
                                fieldWithPath("foodTypeName").type(String.class).description("Name of type of food"),
                                fieldWithPath("foodOrder").type(Integer.class).description("Order by foodTye"),
                                fieldWithPath("_links.self.href").type(String.class).description("Link to this"),
                                fieldWithPath("_links.profile.href").type(String.class).description("Link to this profile"),
                                fieldWithPath("_links.update-foodStore.href").type(String.class).description("Link to next step")
                        )
                ));

    }

    @Test
    public void 식당정보_다건조회() throws Exception {
        mockMvc.perform(get("/food/foodStores")
                    .param("page", "1")
                    .param("size", "3")
                    .param("sort", "storeName.asc")
                    .accept(HAL_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("_embedded.foodStores[0].storeName").exists())
                .andExpect(jsonPath("_embedded.foodStores[0].rate").exists())
                .andExpect(jsonPath("_embedded.foodStores[0].ownerName").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andExpect(jsonPath("_links.prev").exists())
                .andExpect(jsonPath("_links.next").exists())
                .andDo(document("get-foodStores",
                        links(
                                linkWithRel("self").description("Link to self information"),
                                linkWithRel("profile").description("Link to profile"),
                                linkWithRel("get-foodStore").description("Link to retrieve foodstore information"),
                                linkWithRel("first").description("Link to first page of reservation"),
                                linkWithRel("last").description("Link to last page of reservation"),
                                linkWithRel("prev").description("Link to previous page of reservation"),
                                linkWithRel("next").description("Link to next page of reservation")
                        ),
                        responseFields(
                                fieldWithPath("page.size").type(String.class).description("Size of page"),
                                fieldWithPath("page.totalElements").type(String.class).description("The total number of elements"),
                                fieldWithPath("page.totalPages").type(String.class).description("The total number of pages"),
                                fieldWithPath("page.number").type(String.class).description("The current page number"),
                                fieldWithPath("_embedded.foodStores").type(Collections.class).description("list of food store"),
                                fieldWithPath("_embedded.foodStores[].id").type(Integer.class).description("Identification of foodstore"),
                                fieldWithPath("_embedded.foodStores[].storeName").type(String.class).description("Name of store"),
                                fieldWithPath("_embedded.foodStores[].rate").type(Integer.class).description("Rate food of store"),
                                fieldWithPath("_embedded.foodStores[].ownerName").type(String.class).description("Name of owner"),
                                fieldWithPath("_embedded.foodStores[].foodTypeName").type(String.class).description("Name of type of food"),
                                fieldWithPath("_embedded.foodStores[].foodOrder").type(Integer.class).description("Order by foodTye"),
                                fieldWithPath("_links.self.href").type(String.class).description("Link to this"),
                                fieldWithPath("_links.profile.href").type(String.class).description("Link to this profile"),
                                fieldWithPath("_links.get-foodStore.href").type(String.class).description("Link to next step"),
                                fieldWithPath("_links.first.href").type(String.class).description("Link to first page of food store"),
                                fieldWithPath("_links.last.href").type(String.class).description("Link to last page of food store"),
                                fieldWithPath("_links.prev.href").type(String.class).description("Link to previous page of food store"),
                                fieldWithPath("_links.next.href").type(String.class).description("Link to next page of food store")
                            )
                        ));
    }



    @Test
    public void 식당_한건_생성() throws Exception {
        FoodType foodType = foodTypeRepository.findByFoodTypeName("한식");

        FoodStoreDTO foodStoreDTO = new FoodStoreDTO();
        foodStoreDTO.setFoodTypeName(foodType.getFoodTypeName());
        foodStoreDTO.setFoodOrder(foodType.getFoodOrder());
        foodStoreDTO.setOwnerName("sangmessi");
        foodStoreDTO.setStoreName("수원 곱창");
        foodStoreDTO.setRate(1);

        this.mockMvc.perform(post("/food/foodStore")
                .accept(HAL_JSON)
                .contentType(APPLICATION_JSON_UTF8)
                .header(HttpHeaders.AUTHORIZATION, getBearerToken())
                .content(objectMapper.writeValueAsString(foodStoreDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("storeName").exists())
                .andExpect(jsonPath("rate").exists())
                .andExpect(jsonPath("ownerName").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andExpect(jsonPath("_links.get-foodStore").exists())
                .andDo(document("create-foodStore",
                        links(
                                linkWithRel("self").description("Link to self information"),
                                linkWithRel("profile").description("Link to profile"),
                                linkWithRel("get-foodStore").description("Link to retrieve foodstore information")
                        ),
                        requestHeaders(
                                headerWithName(ACCEPT).description("Accept Type to Server")
                        ),
                        requestFields(
                                fieldWithPath("id").type(Integer.class).description("Identification of foodstore"),
                                fieldWithPath("storeName").type(String.class).description("Name of store"),
                                fieldWithPath("rate").type(Integer.class).description("Rate food of store"),
                                fieldWithPath("ownerName").type(String.class).description("Name of owner"),
                                fieldWithPath("foodTypeName").type(String.class).description("Name of type of food"),
                                fieldWithPath("foodOrder").type(Integer.class).description("Order by foodTye")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Contents Type to Client")
                        ),
                        responseFields(
                                fieldWithPath("id").type(Integer.class).description("Identification of foodstore"),
                                fieldWithPath("storeName").type(String.class).description("Name of store"),
                                fieldWithPath("rate").type(Integer.class).description("Rate food of store"),
                                fieldWithPath("ownerName").type(String.class).description("Name of owner"),
                                fieldWithPath("foodTypeName").type(String.class).description("Name of type of food"),
                                fieldWithPath("foodOrder").type(Integer.class).description("Order by foodTye"),
                                fieldWithPath("_links.self.href").type(String.class).description("Link to this"),
                                fieldWithPath("_links.profile.href").type(String.class).description("Link to this profile"),
                                fieldWithPath("_links.get-foodStore.href").type(String.class).description("Link to next step")
                        )

                ));


    }

    @Autowired
    AccountRepository accountService;

    @Test
    public void test() throws Exception {
        mockMvc.perform(get("/food/foodStoress")
                .param("page", "0")
                .param("size", "5")
                .param("sort", "storeName.asc")
                .accept(HAL_JSON))
                .andDo(print());

    }


}