package com.ecom.order.client;


import com.ecom.order.dto.ProductResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class ProductClient {

    private final RestClient restClient;
    private final String productServiceBaseUrl;

    public ProductClient(RestClient restClient, @Value("${product.service.base-url}") String productServiceBaseUrl) {
        this.restClient = restClient;
        this.productServiceBaseUrl = productServiceBaseUrl;
    }

    public ProductResponseDTO getProduct(Long productId) {
        return restClient.get()
                .uri(productServiceBaseUrl + "/api/products/" + productId)
                .retrieve()
                .body(ProductResponseDTO.class);
    }
}