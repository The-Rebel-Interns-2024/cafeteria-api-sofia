package com.serbatic.coffeeshop.business.services;


import com.serbatic.coffeeshop.data.entities.Menu;
import com.serbatic.coffeeshop.data.repositories.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MenuServiceImpl implements MenuService<Menu> {

    @Autowired
    MenuRepository menuRep;

    @Override
    public List<Menu> findAll() {
        return menuRep.findAll();
    }

    @Override
    public Menu findById(Long id) {
        Optional<Menu> menuOpt = menuRep.findById(id);
        if (menuOpt.isPresent()) {
            Menu menu = menuOpt.get();
            return menu;
        } else {
            return null;
        }
    }

    @Override
    public Menu save(Menu item) {
        return menuRep.save(item);
    }

    @Override
    public void delete(Long id) {
        menuRep.deleteById(id);
    }

    @Override
    public List<Menu> getCurrentMenu(){
       return menuRep.findByMenuDate(LocalDate.now());
    }
}
