package com.serbatic.coffeeshop.presentation.dto;

import java.util.List;

public class OrderDishesKitchenResponseDTO {
    private Long orderId;
    private List<OrderDishesRequestDTO> dishNames;
    private Boolean orderStatus;

    public OrderDishesKitchenResponseDTO(Long orderId, List<OrderDishesRequestDTO> dishNames, Boolean orderStatus) {
        this.orderId = orderId;
        this.dishNames = dishNames;
        this.orderStatus = orderStatus;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public List<OrderDishesRequestDTO> getDishNames() {
        return dishNames;
    }

    public void setDishNames(List<OrderDishesRequestDTO> dishNames) {
        this.dishNames = dishNames;
    }

    public Boolean getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Boolean orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Override
    public String toString() {
        return "OrderDishesKitchenResponseDTO{" +
                "orderId=" + orderId +
                ", dishNames=" + dishNames +
                ", orderStatus=" + orderStatus +
                '}';
    }
}
