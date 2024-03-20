package com.serbatic.coffeeshop.business.services.foodorder;

import com.serbatic.coffeeshop.data.entities.FoodOrder;
import com.serbatic.coffeeshop.presentation.dto.OrderDishesRequestDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FoodOrderService {

    List<FoodOrder> findAll();

    FoodOrder findById(Long id);

    FoodOrder save(List<OrderDishesRequestDTO> orderDishesRequestDTOList);

    ResponseEntity<String> deleteById(Long id);

    List<FoodOrder> findCurrentUnfinishedOrders();

    List<FoodOrder> findCurrentFinishedOrders();

    FoodOrder changeStatusOrder(Long id);

    FoodOrder changePaidOrder(Long id);

    FoodOrder addNewDishesToOrder(Long id, List<OrderDishesRequestDTO> orderDishesRequestDTOList);
}
