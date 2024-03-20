package com.serbatic.coffeeshop.business.services.foodorder;

import com.serbatic.coffeeshop.business.services.dish.DishService;
import com.serbatic.coffeeshop.data.entities.Dish;
import com.serbatic.coffeeshop.data.entities.FoodOrder;
import com.serbatic.coffeeshop.data.entities.OrderLine;
import com.serbatic.coffeeshop.data.repositories.FoodOrderRepository;
import com.serbatic.coffeeshop.data.repositories.OrderLineRepository;
import com.serbatic.coffeeshop.presentation.dto.OrderDishesRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class FoodOrderServiceImpl implements FoodOrderService {
    @Autowired
    FoodOrderRepository orderRep;

    @Autowired
    DishService dishService;

    @Autowired
    OrderLineRepository orderLineRepository;

    @Override
    public List<FoodOrder> findAll() {

        List<FoodOrder> orders = orderRep.findAll();
        if (orders.size() == 0) {
            throw new NoSuchElementException("No orders found");
        }
        return orders;
    }

    @Override
    public FoodOrder findById(Long id) {
        Optional<FoodOrder> orderOpt = orderRep.findById(id);
        if (orderOpt.isPresent()) {
            FoodOrder order = orderOpt.get();
            return order;
        } else {
            throw new NoSuchElementException("Order not found with id " + id);
        }
    }

    @Override
    public FoodOrder save(List<OrderDishesRequestDTO> orderDishesRequestDTOList) {
        if (orderDishesRequestDTOList.size() == 0) {
            throw new IllegalArgumentException("Not inserted dishes, bad request");
        }
        FoodOrder foodOrder = new FoodOrder();
        Double totalPrice = 0.0;
        foodOrder.setOrderDate(LocalDate.now());
        foodOrder.setFinished(Boolean.FALSE);
        foodOrder.setOrderPaid(Boolean.FALSE);
        List<OrderLine> orderLines = new ArrayList<>();
        for (OrderDishesRequestDTO dto : orderDishesRequestDTOList) {
            if (dto.getQuantity() <= 0) {
                throw new IllegalArgumentException("The order cant be created, dish quantity not valid");
            }
            Dish dish = dishService.getDishByName(dto.getDishName());
            if (dish == null) {
                throw new IllegalArgumentException("The order cant be created, dish not recognized in today's menu");
            }
            OrderLine orderLine = new OrderLine(dish, dto.getQuantity());
            OrderLine result = orderLineRepository.save(orderLine);
            totalPrice = totalPrice + (dish.getDishPrice() * dto.getQuantity());
            orderLines.add(result);
        }
        foodOrder.setOrderPrice(totalPrice);
        foodOrder.setOrderDishes(orderLines);
        return orderRep.save(foodOrder);
    }

    @Override
    public ResponseEntity<String> deleteById(Long id) {
        Optional<FoodOrder> foodOrderOpt = orderRep.findById(id);
        if (foodOrderOpt.isPresent()) {
            orderRep.deleteById(id);
            return new ResponseEntity<>("Order deleted succesfully", HttpStatus.NO_CONTENT);

        } else {
            throw new NoSuchElementException("Order not found with id " + id);
        }

    }


    @Override
    public List<FoodOrder> findCurrentUnfinishedOrders() {
        List<FoodOrder> unfinishedOrders = orderRep.findByOrderDateAndFinished(LocalDate.now(), Boolean.FALSE);
        if (unfinishedOrders.size() == 0) {
            throw new NoSuchElementException("No unfinished orders found");
        }
        return unfinishedOrders;
    }

    @Override
    public List<FoodOrder> findCurrentFinishedOrders() {
        List<FoodOrder> finishedOrders = orderRep.findByOrderDateAndFinished(LocalDate.now(), Boolean.TRUE);
        if (finishedOrders.size() == 0) {
            throw new NoSuchElementException("No finished orders found");
        }
        for (FoodOrder orders : finishedOrders) {
            if (orders.getOrderPaid()) {
                throw new NoSuchElementException("No unpaid orders found");
            }
        }
        return finishedOrders;
    }

    @Override
    public FoodOrder changeStatusOrder(Long id) {
        Optional<FoodOrder> foodOrderOpt = orderRep.findById(id);
        if (foodOrderOpt.isPresent()) {
            FoodOrder foodOrder = foodOrderOpt.get();
            foodOrder.setFinished(Boolean.TRUE);
            return orderRep.save(foodOrder);
        } else {
            throw new NoSuchElementException("No order found with id " + id);
        }
    }

    @Override
    public FoodOrder changePaidOrder(Long id) {
        Optional<FoodOrder> foodOrderOpt = orderRep.findById(id);
        if (foodOrderOpt.isPresent()) {
            FoodOrder foodOrder = foodOrderOpt.get();
            if (!foodOrder.getFinished()) {
                throw new NoSuchElementException("No finished order found with id " + id);
            }
            foodOrder.setOrderPaid(Boolean.TRUE);
            return orderRep.save(foodOrder);
        } else {
            throw new NoSuchElementException("Order not found with id " + id);
        }
    }

    @Override
    public FoodOrder addNewDishesToOrder(Long id, List<OrderDishesRequestDTO> orderDishesRequestDTOList) {
        Optional<FoodOrder> foodOrderOpt = orderRep.findById(id);
        if (orderDishesRequestDTOList.size() == 0) {
            throw new IllegalArgumentException("Not inserted dishes");
        }
        if (foodOrderOpt.isPresent()) {
            FoodOrder foodOrder = foodOrderOpt.get();
            if (!foodOrder.getOrderPaid()) {
                Double totalPrice = foodOrder.getOrderPrice();
                List<OrderLine> foodOrderLines = foodOrder.getOrderLines();
                for (OrderDishesRequestDTO dto : orderDishesRequestDTOList) {
                    if (dto.getQuantity() <= 0) {
                        throw new IllegalArgumentException("The order cant be created, dish quantity not valid");
                    }
                    Dish dish = dishService.getDishByName(dto.getDishName());
                    if (dish == null) {
                        throw new NoSuchElementException("Dish not found in today's menu");
                    }
                    OrderLine orderLine = new OrderLine(dish, dto.getQuantity());
                    OrderLine result = orderLineRepository.save(orderLine);
                    totalPrice = totalPrice + (dish.getDishPrice() * dto.getQuantity());
                    foodOrderLines.add(result);
                }
                foodOrder.setFinished(Boolean.FALSE);
                foodOrder.setOrderLines(foodOrderLines);
                foodOrder.setOrderPrice(totalPrice);
                return orderRep.save(foodOrder);
            } else {
                throw new IllegalArgumentException("Order already paid");
            }
        } else {
            throw new NoSuchElementException("Order not found with id " + id);
        }
    }

}
