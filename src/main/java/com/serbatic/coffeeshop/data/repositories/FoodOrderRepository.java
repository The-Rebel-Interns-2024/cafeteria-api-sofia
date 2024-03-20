package com.serbatic.coffeeshop.data.repositories;

import com.serbatic.coffeeshop.data.entities.Dish;
import com.serbatic.coffeeshop.data.entities.FoodOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface FoodOrderRepository extends JpaRepository<FoodOrder, Long> {
    List<FoodOrder> findByOrderDateAndFinished(LocalDate date, Boolean status);
}
