package com.serbatic.coffeeshop.presentation.controllers;

import com.serbatic.coffeeshop.business.services.dish.DishService;
import com.serbatic.coffeeshop.business.services.foodorder.FoodOrderService;
import com.serbatic.coffeeshop.data.entities.Dish;
import com.serbatic.coffeeshop.data.entities.FoodOrder;
import com.serbatic.coffeeshop.data.entities.OrderLine;
import com.serbatic.coffeeshop.presentation.dto.*;
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

@RestController
@RequestMapping("/api")
public class FoodOrderController {

    @Autowired
    FoodOrderService orderService;
    @Autowired
    DishService dishService;

    @GetMapping("/orders")
    @Operation(
            summary = "Find the complete list of orders"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(mediaType = "*/*", examples = {
                    @ExampleObject(name = "NotFoundExample", value = "No orders found")
            }))
    })
    public ResponseEntity<List<OrderDTO>> findAllOrders() {
        List<FoodOrder> list = orderService.findAll();
        List<OrderDTO> mappedList = new ArrayList<>();
        for (FoodOrder order : list) {
            List<OrderDishesResponseDTO> dishesDTO = new ArrayList<>();
            for (OrderLine lines : order.getOrderLines()) {
                Dish dish = lines.getDish();
                OrderDishesResponseDTO dishDTO = new OrderDishesResponseDTO(dish.getId(), dish.getDishName(), dish.getDishPrice(), lines.getQuantity());
                dishesDTO.add(dishDTO);
            }
            OrderDTO orderDTO = new OrderDTO(order.getId(), order.getOrderDate(), order.getOrderPrice(), order.getOrderPaid(), order.getFinished(), dishesDTO);
            mappedList.add(orderDTO);
        }
        return ResponseEntity.ok(mappedList);
    }

    @GetMapping("/orders/{id}")
    @Operation(
            summary = "Find one order by its id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(mediaType = "*/*", examples = {
                    @ExampleObject(name = "NotFoundExample", value = "Order not found with id 0")
            }))
    })
    public ResponseEntity<OrderDTO> findOrderById(@PathVariable Long id) {
        FoodOrder foodOrder = orderService.findById(id);
        List<OrderDishesResponseDTO> dishesDTO = new ArrayList<>();
        for (OrderLine lines : foodOrder.getOrderLines()) {
            Dish dish = lines.getDish();
            OrderDishesResponseDTO dishDTO = new OrderDishesResponseDTO(dish.getId(), dish.getDishName(), dish.getDishPrice(), lines.getQuantity());
            dishesDTO.add(dishDTO);
        }
        OrderDTO orderDTO = new OrderDTO(foodOrder.getId(), foodOrder.getOrderDate(), foodOrder.getOrderPrice(), foodOrder.getOrderPaid(), foodOrder.getFinished(), dishesDTO);
        return ResponseEntity.ok(orderDTO);
    }

    @PostMapping("/orders")
    @Operation(
            summary = "Create a new order"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "*/*", examples = {
                    @ExampleObject(name = "BadRequestExample", value = "Not inserted dishes, bad request"),
                    @ExampleObject(name = "BadRequestExample2", value = "The order cant be created, dish not recognized in today's menu"),
                    @ExampleObject(name = "BadRequestExample3", value = "The order cant be created, dish quantity not valid")
            }))
    })

    public ResponseEntity<FoodOrder> createOrder(@RequestBody List<OrderDishesRequestDTO> orderDishesRequestDTOList) {
        FoodOrder foodOrder = orderService.save(orderDishesRequestDTOList);
        return ResponseEntity.ok(foodOrder);
    }


    @GetMapping("/orders/kitchen")
    @Operation(
            summary = "Find all unfinished today orders for the kitchen"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(mediaType = "*/*", examples = {
                    @ExampleObject(name = "NotFoundExample", value = "No unfinished orders found")
            }))
    })
    public ResponseEntity<List<OrderDishesKitchenResponseDTO>> showCurrentUnfinishedOrders() {
        List<FoodOrder> unfinishedOrders = orderService.findCurrentUnfinishedOrders();
        List<OrderDishesKitchenResponseDTO> kitchenDishes = new ArrayList<>();
        for (FoodOrder orders : unfinishedOrders) {
            List<OrderDishesRequestDTO> dishesOrder = new ArrayList<>();
            for (OrderLine lines : orders.getOrderDishes()) {
                OrderDishesRequestDTO dish = new OrderDishesRequestDTO(lines.getDish().getDishName(), lines.getQuantity());
                dishesOrder.add(dish);
            }
            OrderDishesKitchenResponseDTO kitchenResponse = new OrderDishesKitchenResponseDTO(orders.getId(), dishesOrder, orders.getFinished());
            kitchenDishes.add(kitchenResponse);
        }
        return ResponseEntity.ok(kitchenDishes);
    }

    @GetMapping("orders/waiter")
    @Operation(
            summary = "Find all today finished and unpaid orders for the waiter"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(mediaType = "*/*", examples = {
                    @ExampleObject(name = "NotFoundExample", value = "No finished orders found"),
                    @ExampleObject(name = "NotFoundExample2", value = "No unpaid orders found")
            }))
    })
    public ResponseEntity<List<OrderDishesWaiterResponseDTO>> showCurrentUnpaidFinishedOrders() {
        List<FoodOrder> finishedOrders = orderService.findCurrentFinishedOrders();
        List<OrderDishesWaiterResponseDTO> waiterOrders = new ArrayList<>();
        for (FoodOrder orders : finishedOrders) {
            List<OrderDishesRequestDTO> dishes = new ArrayList<>();
            for (OrderLine lines : orders.getOrderLines()) {
                OrderDishesRequestDTO dish = new OrderDishesRequestDTO(lines.getDish().getDishName(), lines.getQuantity());
                dishes.add(dish);
            }
            OrderDishesWaiterResponseDTO waiterResponse = new OrderDishesWaiterResponseDTO(orders.getId(), dishes, orders.getFinished(), orders.getOrderPaid(), orders.getOrderPrice());
            waiterOrders.add(waiterResponse);
        }
        return ResponseEntity.ok(waiterOrders);
    }

    @PutMapping("orders/finished/{id}")
    @Operation(
            summary = "Change the status of an order to finished."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(mediaType = "*/*", examples = {
                    @ExampleObject(name = "NotFoundExample", value = "No unfinished order found with id 1")
            }))
    })
    public ResponseEntity<FoodOrder> changeOrderStatus(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.changeStatusOrder(id));
    }

    @PutMapping("orders/paid/{id}")
    @Operation(
            summary = "Change a finished orders to paid."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "*/*", examples = {
                    @ExampleObject(name = "BadRequestExample", value = "No finished order found with id 1")
            }))
    })
    public ResponseEntity<FoodOrder> changeOrderPaid(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.changePaidOrder(id));
    }


    @DeleteMapping("/orders/{id}")
    @Operation(
            summary = "Delete one order by its id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content", content = @Content(mediaType = "*/*", examples = {
                    @ExampleObject(name = "NoContentExample", value = "Order deleted succesfully")
            })),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(mediaType = "*/*", examples = {
                    @ExampleObject(name = "NotFoundExample", value = "Order not found with id 1")
            }))
    })
    public ResponseEntity<String> deleteOrderById(@PathVariable Long id) {
        return orderService.deleteById(id);
    }

    @PutMapping("/orders/{id}")
    @Operation(
            summary = "Add new dishes to an unpaid order."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(mediaType = "*/*", examples = {
                    @ExampleObject(name = "NotFoundExample", value = "Order not found with id 1"),
                    @ExampleObject(name = "NotFoundExample2", value = "Dish not found in today's menu")
            })),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "*/*", examples = {
                    @ExampleObject(name = "BadRequestExample", value = "Order already paid"),
                    @ExampleObject(name = "BadRequestExample2", value = "Not inserted dishes"),
                    @ExampleObject(name = "BadRequestExample3", value = "The order cant be created, dish quantity not valid")
            }))
    })
    public ResponseEntity<FoodOrder> addNewDishesToOrder(@PathVariable Long id, @RequestBody List<OrderDishesRequestDTO> orderDishesRequestDTOList) {
        FoodOrder updatedOrder = orderService.addNewDishesToOrder(id, orderDishesRequestDTOList);
        return ResponseEntity.ok(updatedOrder);
    }
}
