package com.ecom.order.controller;

import com.ecom.order.dto.OrderResponseDTO;
import com.ecom.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/place/{customerId}")
    public ResponseEntity<OrderResponseDTO> placeOrder(@PathVariable Long customerId) {
        OrderResponseDTO responseDTO = orderService.placeOrder(customerId);
        return ResponseEntity.ok(responseDTO);
    }
}