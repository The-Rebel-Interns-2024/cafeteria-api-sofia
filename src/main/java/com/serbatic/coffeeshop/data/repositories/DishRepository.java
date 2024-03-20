package com.serbatic.coffeeshop.data.repositories;

import com.serbatic.coffeeshop.data.entities.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface DishRepository extends JpaRepository<Dish, Long> {

    List<Dish> findByDishDate(LocalDate date);
    Dish findByDishNameAndDishDate(String name, LocalDate date);
}
