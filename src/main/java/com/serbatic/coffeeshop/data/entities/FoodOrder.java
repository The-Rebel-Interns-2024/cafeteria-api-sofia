package com.serbatic.coffeeshop.data.entities;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "orders")
public class FoodOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "FoodOrder ID")
    private Long id;

    @Column(name = "total_price")
    @Schema(description = "FoodOrder total price")
    private Double orderPrice;

    @Column(name = "order_date")
    @Schema(description = "FoodOrder placement date")
    private LocalDate orderDate;

    @Column(name = "is_finished")
    @Schema(description = "If true, indicates that the order is finished; if false, indicates that it is being prepared.")
    private Boolean finished;

    @Column(name = "is_paid")
    @Schema(description = "If true, indicates that the order has been paid; if false, indicates that it has to be paid.")
    private Boolean orderPaid;

    @OneToMany()
    @JoinColumn(name = "order_number")
    private List<OrderLine> orderLines;

    public List<OrderLine> getOrderDishes() {
        return orderLines;
    }

    public void setOrderDishes(List<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(Double orderPrice) {
        this.orderPrice = orderPrice;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public Boolean getOrderPaid() {
        return orderPaid;
    }

    public void setOrderPaid(Boolean orderPaid) {
        this.orderPaid = orderPaid;
    }

    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    @Override
    public String toString() {
        return "FoodOrder{" +
                "id=" + id +
                ", orderPrice=" + orderPrice +
                ", orderDate=" + orderDate +
                ", finished=" + finished +
                ", orderPaid=" + orderPaid +
                '}';
    }
}
