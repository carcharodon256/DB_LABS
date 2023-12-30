package com.orm.lab02.controller;

import com.orm.lab02.entity.CustomerOrder;
import com.orm.lab02.repository.CustomerOrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class CustomerOrderController {
    private final CustomerOrderRepository orderRepository;

    public CustomerOrderController(CustomerOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @PostMapping
    public ResponseEntity<CustomerOrder> addOrder(@RequestBody CustomerOrder order) {
        CustomerOrder createdOrder = orderRepository.save(order);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdOrder);
    }

    @GetMapping
    public ResponseEntity<List<CustomerOrder>> getOrders() {
        List<CustomerOrder> orders = orderRepository.findAll();
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerOrder> updateOrder(@PathVariable Long id, @RequestBody CustomerOrder order) {
        Optional<CustomerOrder> optionalExistingOrder = orderRepository.findById(id);

        if (optionalExistingOrder.isPresent()) {
            CustomerOrder existingOrder = optionalExistingOrder.get();

            existingOrder.setOrderNumber(order.getOrderNumber());
            existingOrder.setDeliveryAddress(order.getDeliveryAddress());
            existingOrder.setDeliveryDateTime(order.getDeliveryDateTime());

            CustomerOrder updatedOrder = orderRepository.save(existingOrder);

            return ResponseEntity.ok(updatedOrder);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteOrderById(@PathVariable Long id) {
        Optional<CustomerOrder> optionalOrder = orderRepository.findById(id);

        if (optionalOrder.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        orderRepository.deleteById(id);

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", true);

        return ResponseEntity.ok(response);
    }
}

