package com.hirit.boot.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hirit.boot.enttiy.FoodStore;
import com.hirit.boot.food.FoodService;
import com.hirit.boot.food.vo.FoodStoreResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hirit.boot.food.vo.FoodRetrieveCondition;
import com.hirit.boot.food.vo.FoodStoreDTO;
import com.hirit.boot.repository.FoodStoreRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Objects;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping("/food")
@RequiredArgsConstructor
public class FoodController {

    private final FoodService service;
//
//    @GetMapping("/foodStores")
//    public Page<FoodStoreDTO> getFoodStores(FoodRetrieveCondition condition, Pageable pageable) {
//        return repository.retrieveStores(condition, pageable);
//    }
//
//    @GetMapping("/foodStoresBetter")
//    public Page<FoodStoreDTO> getFoodStoresBetter(FoodRetrieveCondition condition, Pageable pageable) {
//        return repository.retrieveStoresBetter(condition, pageable);
//    }

    @GetMapping("/foodStores/{id}")
    public ResponseEntity getFoodStore(@PathVariable Integer id) {
        FoodStoreDTO foodStoreDTO = service.getFoodStore(id);

        if (Objects.isNull(foodStoreDTO))
            return ResponseEntity.badRequest().body("We can't find this foodStore");

        FoodStoreResource resource = new FoodStoreResource(foodStoreDTO);
        resource.add(linkTo(FoodController.class).slash("foodStores").slash(foodStoreDTO.getId()).withSelfRel());
        resource.add(linkTo(FoodController.class).withRel("update-foodStore"));
        resource.add(new Link("/docs/foodStore.html#resources-foodStore-get").withRel("profile"));
        return ResponseEntity.ok(resource);
    }

    @PostMapping("/foodStore")
    public ResponseEntity createFoodStore(@RequestBody FoodStoreDTO foodStoreDTO) {
        FoodStoreDTO savedFoodStoreDTO = service.saveFoodStore(foodStoreDTO);
        FoodStoreResource resource = new FoodStoreResource(savedFoodStoreDTO);
        resource.add(linkTo(FoodController.class).slash("foodStore").withSelfRel());
        resource.add(linkTo(FoodController.class).withRel("get-foodStore"));
        resource.add(new Link("/docs/foodStore.html#resources-foodStore-create").withRel("profile"));
        return ResponseEntity.ok(resource);
    }

    @GetMapping("/foodStores")
    public ResponseEntity getFoodStores(Pageable pageable, PagedResourcesAssembler<FoodStoreDTO> assembler) throws JsonProcessingException {
        Page<FoodStoreDTO> foodStores = service.getFoodStores(pageable);
        var resource = assembler.toResource(foodStores, r -> new FoodStoreResource(r));
        resource.add(new Link("/docs/foodStore.html#resources-foodStore-list").withRel("profile"));
        resource.add(linkTo(FoodController.class).withRel("get-foodStore"));
        return ResponseEntity.ok(resource);
    }
}
