package com.serbatic.coffeeshop.business.services;

import java.util.List;

public interface MenuService<Menu> {
    List<Menu> findAll();
    Menu findById(Long id);
    Menu save (Menu item);
    void delete(Long id);
    List<Menu> getCurrentMenu();
}
