package com.serbatic.coffeeshop.presentation.dto;

import java.util.List;

public class OrderDishesWaiterResponseDTO {
    private Long orderId;
    private List<OrderDishesRequestDTO> dishes;
    private Boolean orderStatus;
    private Boolean paid;
    private Double totalPrice;

    public OrderDishesWaiterResponseDTO(Long orderId, List<OrderDishesRequestDTO> dishes, Boolean orderStatus, Boolean paid, Double totalPrice) {
        this.orderId = orderId;
        this.dishes = dishes;
        this.orderStatus = orderStatus;
        this.paid = paid;
        this.totalPrice = totalPrice;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public List<OrderDishesRequestDTO> getDishes() {
        return dishes;
    }

    public void setDishes(List<OrderDishesRequestDTO> dishes) {
        this.dishes = dishes;
    }

    public Boolean getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Boolean orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Boolean getPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "OrderDishesWaiterResponseDTO{" +
                "orderId=" + orderId +
                ", dishes=" + dishes +
                ", orderStatus=" + orderStatus +
                ", paid=" + paid +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
