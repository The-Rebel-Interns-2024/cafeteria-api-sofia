package com.serbatic.coffeeshop.business.services.dish;


import com.serbatic.coffeeshop.data.entities.Dish;
import com.serbatic.coffeeshop.data.repositories.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class DishServiceImpl implements DishService {

    @Autowired
    DishRepository dishRep;

    @Override
    public List<Dish> findAll() {
        List<Dish> dishes = dishRep.findAll();
        if(dishes.size()==0){
            throw new NoSuchElementException("No dishes found");
        }
        return dishes;
    }

    @Override
    public Dish findById(Long id) {
        Optional<Dish> dishOpt = dishRep.findById(id);
        if (dishOpt.isPresent()) {
            Dish dish = dishOpt.get();
            return dish;
        } else {
            throw new NoSuchElementException("Dish not found with id " + id);
        }
    }

    @Override
    public Dish save(Dish item) {
        return dishRep.save(item);
    }

    @Override
    public ResponseEntity<String> deleteById(Long id) {
        Optional<Dish> dishOpt = dishRep.findById(id);
        if (dishOpt.isPresent()) {
            dishRep.deleteById(id);
            return new ResponseEntity<>("Dish deleted succesfully", HttpStatus.NO_CONTENT);

        } else {
            throw new NoSuchElementException("Dish not found with id " + id);
        }
    }

    @Override
    public List<Dish> getCurrentMenu() {
        List<Dish> currentMenu= dishRep.findByDishDate(LocalDate.now());
        if(currentMenu.size()==0){
            throw new NoSuchElementException("No menu for today found");
        }
        return currentMenu;
    }

    @Override
    public Dish getDishByName(String name) {
        Dish dish = dishRep.findByDishNameAndDishDate(name, LocalDate.now());
        return dish;
    }
}
