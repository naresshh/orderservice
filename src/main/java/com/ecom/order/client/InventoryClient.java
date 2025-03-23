package com.ecom.order.client;

import com.ecom.order.dto.InventoryRequestDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class InventoryClient {

    private final RestClient restClient;
    private final String inventoryServiceBaseUrl;

    public InventoryClient(RestClient restClient, @Value("${inventory.service.base-url}") String inventoryServiceBaseUrl) {
        this.restClient = restClient;
        this.inventoryServiceBaseUrl = inventoryServiceBaseUrl;
    }

    public void deductInventory(List<InventoryRequestDTO> requestDTOList,Long customerId) {
        restClient.post()
                .uri(inventoryServiceBaseUrl + "/deduct?customerId=" + customerId)
                .body(requestDTOList)  // This is equivalent to BodyInserters
                .retrieve()
                .toBodilessEntity();
    }

}