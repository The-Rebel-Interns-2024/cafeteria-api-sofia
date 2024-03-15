package com.serbatic.coffeeshop.presentation.controllers;

import com.serbatic.coffeeshop.presentation.dto.DishDTO;
import com.serbatic.coffeeshop.data.entities.Menu;
import com.serbatic.coffeeshop.business.services.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MenuController {

    @Autowired
    MenuService<Menu> menuService;

    @GetMapping("/menus")
    @Operation(
            summary = "Show current menu"
    )
    public List<DishDTO> findAllMenus() {
        List<DishDTO> dishes = new ArrayList<>();
        List<Menu> menus = menuService.getCurrentMenu();
        for (Menu menu : menus) {
            DishDTO dish = new DishDTO(menu.getDish(), menu.getMenuPrice());
            dishes.add(dish);
        }
        return dishes;
    }

    @GetMapping("/menus/{id}")
    @Operation(
            summary = "Find one menu by its id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(mediaType = "*/*", examples = {
                    @ExampleObject(name = "NotFoundExample", value = "{}")
            }))
    })
    public ResponseEntity<Menu> findMenuById(@PathVariable Long id) {
        Menu menu = menuService.findById(id);
        if (menu != null) {
            return ResponseEntity.ok(menu);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/menus")
    @Operation(
            summary = "Create a new menu"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "*/*", examples = {
                    @ExampleObject(name = "BadRequestExample", value = "{}")
            }))
    })
    public ResponseEntity<Menu> createMenu(@RequestBody Menu menu) {
        if (menu.getId() != null) {
            return ResponseEntity.badRequest().build();
        }
        Menu result = menuService.save(menu);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/menus/{id}")
    @Operation(
            summary = "Update an already existing menu"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(mediaType = "*/*", examples = {
                    @ExampleObject(name = "NotFoundExample", value = "{}")
            }))
    })
    public ResponseEntity<Menu> updateMenu(@PathVariable Long id, @RequestBody Menu menu) {
        Menu updateMenu = menuService.findById(id);
        if (updateMenu != null && updateMenu.getId() == menu.getId()) {
            updateMenu.setMenuDate(menu.getMenuDate());
            updateMenu.setDish(menu.getDish());
            return ResponseEntity.ok(menuService.save(updateMenu));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/menus/{id}")
    @Operation(
            summary = "Delete one menu by its id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content", content = @Content(mediaType = "*/*", examples = {
                    @ExampleObject(name = "NoContentExample", value = "{}")
            })),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(mediaType = "*/*", examples = {
                    @ExampleObject(name = "NotFoundExample", value = "{}")
            }))
    })
    public ResponseEntity<Menu> deleteMenuById(@PathVariable Long id) {
        Menu deleteMenu = menuService.findById(id);

        if (deleteMenu != null) {
            menuService.delete(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}