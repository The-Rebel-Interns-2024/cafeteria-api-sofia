package com.serbatic.coffeeshop.data.entities;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "menus")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Menu ID")
    private Long id;

    @Column(name = "price")
    @Schema(description = "Price for the whole menu, including one drink")
    private Double menuPrice;

    @Column(name = "date")
    @Schema(description = "Date in which this menu is served")
    private LocalDate menuDate;

    @Column(name = "dishes")
    @Schema(description = "Dish of the menu")
    private String dish;

    public Menu(Long id, Double menuPrice, LocalDate menuDate, String dish) {
        this.id = id;
        this.menuPrice = menuPrice;
        this.menuDate = menuDate;
        this.dish = dish;
    }

    public Menu() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMenuPrice() {
        return menuPrice;
    }

    public void setMenuPrice(Double menuPrice) {
        this.menuPrice = menuPrice;
    }

    public LocalDate getMenuDate() {
        return menuDate;
    }

    public void setMenuDate(LocalDate menuDate) {
        this.menuDate = menuDate;
    }

    public String getDish() {
        return dish;
    }

    public void setDish(String dish) {
        this.dish = dish;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", menuPrice=" + menuPrice +
                ", menuDate=" + menuDate +
                ", dishes=" + dish +
                '}';
    }
}
