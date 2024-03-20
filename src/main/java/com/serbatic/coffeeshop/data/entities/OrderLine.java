package com.serbatic.coffeeshop.data.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
@Table(name = "order_line")
public class OrderLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "dish_id")
    private Dish dish;


    @Column(name = "dish_quantity")
    @Schema(description = "Quantity of that dish on that OrderLine")
    private int quantity;

    public OrderLine() {
    }

    public OrderLine(Dish dish, int quantity) {
        this.dish = dish;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderLine{" +
                "dish=" + dish +
                ", quantity=" + quantity +
                '}';
    }
}
