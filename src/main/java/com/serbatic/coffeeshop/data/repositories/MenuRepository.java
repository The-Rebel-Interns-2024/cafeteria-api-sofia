package com.serbatic.coffeeshop.data.repositories;

import com.serbatic.coffeeshop.data.entities.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    List<Menu> findByMenuDate(LocalDate date);
}
