package com.serbatic.coffeeshop.presentation.controllers;

import com.serbatic.coffeeshop.business.services.dish.DishService;
import com.serbatic.coffeeshop.data.entities.Dish;
import com.serbatic.coffeeshop.presentation.dto.DishDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api")
public class DishController {
    @Autowired
    DishService dishService;

    @GetMapping("/menu")
    @Operation(
            summary = "Show current menu"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(mediaType = "*/*", examples = {
                    @ExampleObject(name = "NotFoundExample", value = "No menu for today found")
            }))
    })
    public ResponseEntity<List<DishDTO>> findCurrentDishes() {
        List<DishDTO> dishes = new ArrayList<>();
        List<Dish> menus = dishService.getCurrentMenu();
        for (Dish menu : menus) {
            DishDTO dish = new DishDTO(menu.getDishName(), menu.getDishPrice());
            dishes.add(dish);
        }
        return ResponseEntity.ok(dishes);
    }

    @GetMapping("/dishes")
    @Operation(
            summary = "Find all dishes"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(mediaType = "*/*", examples = {
                    @ExampleObject(name = "NotFoundExample", value = "No dishes found")
            }))
    })
    public ResponseEntity<List<Dish>> findAllDishes() {
        return ResponseEntity.ok(dishService.findAll());
    }

    @GetMapping("/dishes/{id}")
    @Operation(
            summary = "Find one dish by its id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(mediaType = "*/*", examples = {
                    @ExampleObject(name = "NotFoundExample", value = "{}")
            }))
    })
    public ResponseEntity<Dish> findDishById(@PathVariable Long id) {
        return ResponseEntity.ok(dishService.findById(id));
    }

    @PostMapping("/dishes")
    @Operation(
            summary = "Create a new dish"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "*/*", examples = {
                    @ExampleObject(name = "BadRequestExample", value = "Trying to create a new dish with an id")
            }))
    })
    public ResponseEntity<Dish> createDish(@RequestBody Dish dish) {
        if (dish.getId() != null) {
            throw new IllegalArgumentException("Trying to create a new dish with an id");
        }
        Dish result = dishService.save(dish);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/dishes/{id}")
    @Operation(
            summary = "Update an already existing dish"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(mediaType = "*/*", examples = {
                    @ExampleObject(name = "NotFoundExample", value = "Dish not found with id 1")
            })),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "*/*", examples = {
                    @ExampleObject(name = "BadRequestExample", value = "The requested ID does not match with the Dish")
            }))
    })
    public ResponseEntity<Dish> updateDish(@PathVariable Long id, @RequestBody Dish dish) {
        Dish updateDish = dishService.findById(id);
        if (updateDish == null) {
            throw new NoSuchElementException("Dish not found with id " + id);
        }
        if (updateDish.getId() != dish.getId()) {
            throw new IllegalArgumentException("The requested ID does not match with the Dish");
        }
        updateDish.setDishDate(dish.getDishDate());
        updateDish.setDishName(dish.getDishName());
        updateDish.setDishPrice(dish.getDishPrice());
        return ResponseEntity.ok(dishService.save(updateDish));
    }

    @DeleteMapping("/dishes/{id}")
    @Operation(
            summary = "Delete one dish by its id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content", content = @Content(mediaType = "*/*", examples = {
                    @ExampleObject(name = "NoContentExample", value ="Dish deleted succesfully")
            })),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(mediaType = "*/*", examples = {
                    @ExampleObject(name = "NotFoundExample", value = "No dish found with ID 1")
            }))
    })
    public ResponseEntity<String> deleteDishById(@PathVariable Long id) {
        return dishService.deleteById(id);
    }
}
