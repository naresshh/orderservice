package com.ecom.order.controller;

import com.ecom.order.dto.ShippingAddressDTO;
import com.ecom.order.service.OrderService;
import com.ecom.order.service.ShippingService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shipping")
@CrossOrigin(origins = "http://localhost:3000")
public class ShippingController {

    @Autowired
    private ShippingService shippingService;
    @Autowired
    private OrderService orderService;

    @GetMapping("/{customerId}")
    public ResponseEntity<List<ShippingAddressDTO>> getUserAddresses(@PathVariable Long customerId) {
        return ResponseEntity.ok(shippingService.getUserAddresses(customerId));
    }

    @PostMapping("/{customerId}")
    public ResponseEntity<ShippingAddressDTO> addShippingAddress(@PathVariable Long customerId,
                                                                 @RequestBody ShippingAddressDTO dto, HttpServletRequest request) {
        String jwtToken = orderService.extractJwtFromRequest(request);

        if (jwtToken == null) {
            return ResponseEntity.status(401).body(null);
        }
        return ResponseEntity.ok(shippingService.addShippingAddress(customerId, dto,jwtToken));
    }
}