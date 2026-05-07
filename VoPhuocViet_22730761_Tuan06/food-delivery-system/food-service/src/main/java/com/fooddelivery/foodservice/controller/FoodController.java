package com.fooddelivery.foodservice.controller;

import com.fooddelivery.foodservice.model.Food;
import com.fooddelivery.foodservice.service.FoodService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/foods")
public class FoodController {

    private final FoodService foodService;

    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    @GetMapping
    public ResponseEntity<List<Food>> getAllFoods() {
        List<Food> foods = foodService.getAllFoods();
        return ResponseEntity.ok(foods);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Food> getFoodById(@PathVariable String id) {
        Food food = foodService.getFoodById(id);
        return ResponseEntity.ok(food);
    }

    @PostMapping
    public ResponseEntity<Food> addFood(@RequestBody Food food) {
        Food savedFood = foodService.addFood(food);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedFood);
    }
}
