package com.serbatic.coffeeshop.presentation.dto;

import java.time.LocalDate;
import java.util.List;

public class OrderDTO {
    private Long orderId;
    private LocalDate orderDate;
    private Double totalPrice;
    private Boolean isPaid;
    private Boolean isFinished;
    private List<OrderDishesResponseDTO> dishes;


    public OrderDTO(Long orderId, LocalDate orderDate, Double totalPrice, Boolean isPaid, Boolean isFinished, List<OrderDishesResponseDTO> dishes) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.isPaid = isPaid;
        this.isFinished = isFinished;
        this.dishes = dishes;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Boolean getPaid() {
        return isPaid;
    }

    public void setPaid(Boolean paid) {
        isPaid = paid;
    }

    public Boolean getFinished() {
        return isFinished;
    }

    public void setFinished(Boolean finished) {
        isFinished = finished;
    }

    public List<OrderDishesResponseDTO> getDishes() {
        return dishes;
    }

    public void setDishes(List<OrderDishesResponseDTO> dishes) {
        this.dishes = dishes;
    }
}
