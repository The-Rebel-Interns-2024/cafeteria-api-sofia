package com.serbatic.Coffee_Shop.Repositories;

import com.serbatic.Coffee_Shop.Entities.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}
