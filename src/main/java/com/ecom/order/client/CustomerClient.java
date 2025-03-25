package com.ecom.order.client;

import com.ecom.order.dto.CustomerDTO;
import com.ecom.order.dto.ProductResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.HashMap;
import java.util.Map;

@Component
public class CustomerClient {


    private final RestClient restClient;
    private final String customerServiceBaseUrl;

    public CustomerClient(RestClient restClient, @Value("${customer.service.base-url}") String customerServiceBaseUrl) {
        this.restClient = restClient;
        this.customerServiceBaseUrl = customerServiceBaseUrl;
    }

    public CustomerDTO getCustomerById(Long customerId, String jwtToken) {
        // Create headers and set Authorization token
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken);

        // Create HttpEntity with headers
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Send GET request with headers using RestClient
        return restClient.get()
                .uri(customerServiceBaseUrl + "/" + customerId)
                .headers(httpHeaders -> httpHeaders.addAll(headers)) // Add the custom headers
                .retrieve()
                .body(CustomerDTO.class); // Retrieve the body as CustomerDTO
                 // Block to get the result synchronously
    }

//    private static final Map<Long, CustomerDTO> MOCK_CUSTOMERS = new HashMap<>();
//
//    static {
//        MOCK_CUSTOMERS.put(1L, new CustomerDTO(1L, "John Doe", "john@example.com"));
//        MOCK_CUSTOMERS.put(2L, new CustomerDTO(2L, "Jane Smith", "jane@example.com"));
//    }
//
//    public CustomerDTO getCustomerById(Long customerId) {
//        CustomerDTO customer = MOCK_CUSTOMERS.get(customerId);
//        if (customer == null) {
//            throw new RuntimeException("Customer not found with ID: " + customerId);
//        }
//        return customer;
//    }
}