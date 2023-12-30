package com.orm.lab02.controller;

import com.orm.lab02.entity.Dish;
import com.orm.lab02.repository.DishRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/dishes")
public class DishController {
    private final DishRepository dishRepository;

    public DishController(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    @PostMapping
    public ResponseEntity<Dish> addDish(@RequestBody Dish dish) {
        Dish createdDish = dishRepository.save(dish);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdDish);
    }

    @GetMapping
    public ResponseEntity<List<Dish>> getDishes() {
        List<Dish> dishes = dishRepository.findAll();
        return ResponseEntity.ok(dishes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Dish> updateDish(@PathVariable Long id, @RequestBody Dish dish) {
        Optional<Dish> optionalExistingDish = dishRepository.findById(id);

        if (optionalExistingDish.isPresent()) {
            Dish existingDish = optionalExistingDish.get();

            existingDish.setDishName(dish.getDishName());
            existingDish.setWeight(dish.getWeight());
            existingDish.setPrice(dish.getPrice());
            existingDish.setServingsAmount(dish.getServingsAmount());

            Dish updatedDish = dishRepository.save(existingDish);

            return ResponseEntity.ok(updatedDish);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteDishById(@PathVariable Long id) {
        Optional<Dish> optionalDish = dishRepository.findById(id);

        if (optionalDish.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        dishRepository.deleteById(id);

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", true);

        return ResponseEntity.ok(response);
    }
}

