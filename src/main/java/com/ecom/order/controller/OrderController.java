package com.ecom.order.controller;

import com.ecom.order.dto.OrderResponseDTO;
import com.ecom.order.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;


@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/place/{customerId}")
    public ResponseEntity<OrderResponseDTO> placeOrder(@PathVariable Long customerId,
                                                       HttpServletRequest request) {
        // Extract token from Authorization header
        String jwtToken = orderService.extractJwtFromRequest(request);

        if (jwtToken == null) {
            return ResponseEntity.status(401).body(null);
        }

        // Pass token to service
        OrderResponseDTO responseDTO = orderService.placeOrder(customerId, jwtToken);
        return ResponseEntity.ok(responseDTO);
    }
}