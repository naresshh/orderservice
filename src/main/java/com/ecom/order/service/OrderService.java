package com.ecom.order.service;

import com.ecom.order.client.CartClient;
import com.ecom.order.client.CustomerClient;
import com.ecom.order.client.InventoryClient;
import com.ecom.order.dto.*;
import com.ecom.order.mapper.OrderMapper;
import com.ecom.order.modal.Order;
import com.ecom.order.modal.OrderItem;
import com.ecom.order.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartClient cartClient;
    private final InventoryClient inventoryClient;
    private final CustomerClient customerClient;
    private final OrderMapper orderMapper;

    public OrderService(OrderRepository orderRepository, CartClient cartClient, InventoryClient inventoryClient, CustomerClient customerClient, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.cartClient = cartClient;
        this.inventoryClient = inventoryClient;
        this.customerClient = customerClient;
        this.orderMapper = orderMapper;
    }

    @Transactional
    public OrderResponseDTO placeOrder(Long customerId) {

        // 1. Get customer details
        CustomerDTO customer = customerClient.getCustomerById(customerId);

        // 2. Get cart items from CartService
        List<OrderItemDTO> cartItems = cartClient.getCartItems(customerId);

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty for customerId: " + customerId);
        }

        double totalAmount = 0.0;
        List<OrderItem> orderItems = new ArrayList<>();
        List<InventoryRequestDTO> inventoryRequests = new ArrayList<>();

        for (OrderItemDTO itemDTO : cartItems) {
            double itemTotal = itemDTO.getPrice() * itemDTO.getQuantity();
            totalAmount += itemTotal;

            // Create and populate OrderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(itemDTO.getProductId());
            orderItem.setProductName(itemDTO.getProductName());
            orderItem.setPrice(itemDTO.getPrice());
            orderItem.setQuantity(itemDTO.getQuantity());
            orderItems.add(orderItem);

            // Create and populate InventoryRequestDTO for inventory deduction
            InventoryRequestDTO inventoryRequest = new InventoryRequestDTO();
            inventoryRequest.setProductId(itemDTO.getProductId());
            inventoryRequest.setQuantity(itemDTO.getQuantity());
            inventoryRequests.add(inventoryRequest);
        }

        // 3. Deduct inventory in bulk
        inventoryClient.deductInventory(inventoryRequests,customerId);

        // 4. Create and save order
        Order order = new Order();
        order.setCustomerId(customerId);   // Set the customerId
        order.setCustomerName(customer.getName());  // Set the customerName
        order.setTotalAmount(totalAmount);  // Set the totalAmount
        order.setStatus("PLACED");  // Set the order status
        order.setItems(orderItems);  // Set the order items

        // Attach order reference to each order item
        orderItems.forEach(item -> item.setOrder(order));

        orderRepository.save(order);

        inventoryRequests.forEach(request -> request.setOrderId(order.getId()));
        // 5. Clear the cart
        cartClient.clearCart(customerId);

        // 6. Return the order response
        return orderMapper.toResponseDTO(order);
    }
}