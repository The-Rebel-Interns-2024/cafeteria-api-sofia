package com.serbatic.coffeeshop.data.repositories;

import com.serbatic.coffeeshop.data.entities.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {
}
