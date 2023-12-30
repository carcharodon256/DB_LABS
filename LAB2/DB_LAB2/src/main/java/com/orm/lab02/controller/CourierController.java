package com.orm.lab02.controller;

import com.orm.lab02.entity.Client;
import com.orm.lab02.entity.Courier;
import com.orm.lab02.repository.ClientRepository;
import com.orm.lab02.repository.CourierRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/couriers")
public class CourierController {
    CourierRepository courierRepository;

    public CourierController(CourierRepository courierRepository) {
        this.courierRepository = courierRepository;
    }

    @PostMapping
    public ResponseEntity<Courier> addCourier(@RequestBody Courier courier) {
        Courier createdCourier = courierRepository.save(courier);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdCourier);
    }

    @GetMapping
    public ResponseEntity<List<Courier>> getCouriers() {
        List<Courier> couriers = courierRepository.findAll();
        return ResponseEntity.ok(couriers);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Courier> updateCourier(@PathVariable Long id, @RequestBody Courier courier) {
        Optional<Courier> optionalExistingCourier = courierRepository.findById(id);

        if (optionalExistingCourier.isPresent()) {
            Courier existingCourier = optionalExistingCourier.get();

            existingCourier.setCourierName(courier.getCourierName());
            existingCourier.setCourierPhoneNumber(courier.getCourierPhoneNumber());
            existingCourier.setTransportKind(courier.getTransportKind());
            existingCourier.setCourierRating(courier.getCourierRating());

            Courier updatedCourier = courierRepository.save(existingCourier);

            return ResponseEntity.ok(updatedCourier);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteCourierById(@PathVariable Long id) {
        Optional<Courier> optionalCourier = courierRepository.findById(id);

        if (optionalCourier.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        courierRepository.deleteById(id);

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", true);

        return ResponseEntity.ok(response);
    }
}
