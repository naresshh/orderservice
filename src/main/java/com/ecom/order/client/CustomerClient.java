package com.ecom.order.client;

import com.ecom.order.dto.CustomerDTO;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CustomerClient {

    private static final Map<Long, CustomerDTO> MOCK_CUSTOMERS = new HashMap<>();

    static {
        MOCK_CUSTOMERS.put(1L, new CustomerDTO(1L, "John Doe", "john@example.com"));
        MOCK_CUSTOMERS.put(2L, new CustomerDTO(2L, "Jane Smith", "jane@example.com"));
    }

    public CustomerDTO getCustomerById(Long customerId) {
        CustomerDTO customer = MOCK_CUSTOMERS.get(customerId);
        if (customer == null) {
            throw new RuntimeException("Customer not found with ID: " + customerId);
        }
        return customer;
    }
}