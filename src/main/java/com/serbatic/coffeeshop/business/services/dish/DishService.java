package com.serbatic.coffeeshop.business.services.dish;

import com.serbatic.coffeeshop.data.entities.Dish;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DishService {
    List<Dish> findAll();

    Dish findById(Long id);

    Dish save(Dish item);

    public ResponseEntity<String> deleteById(Long id);

    List<Dish> getCurrentMenu();

    Dish getDishByName(String name);
}
