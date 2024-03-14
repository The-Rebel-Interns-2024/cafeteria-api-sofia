package com.serbatic.Coffee_Shop.Services;


import com.serbatic.Coffee_Shop.Entities.Menu;
import com.serbatic.Coffee_Shop.Repositories.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuServiceImpl implements GenericService<Menu> {

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
}
