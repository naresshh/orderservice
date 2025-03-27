package com.ecom.order.client;

import com.ecom.order.dto.InventoryRequestDTO;
import com.ecom.order.exception.InventoryNotFoundException;
import com.ecom.order.exception.InventoryServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.List;

@Component
public class InventoryClient {
    private static final Logger logger = LoggerFactory.getLogger(InventoryClient.class);
    private final RestClient restClient;
    private final String inventoryServiceBaseUrl;

    public InventoryClient(RestClient restClient, @Value("${inventory.service.base-url}") String inventoryServiceBaseUrl) {
        this.restClient = restClient;
        this.inventoryServiceBaseUrl = inventoryServiceBaseUrl;
    }

    public void deductInventory(List<InventoryRequestDTO> requestDTOList, Long customerId) {
        try {
            restClient.post()
                    .uri(inventoryServiceBaseUrl + "/deduct?customerId=" + customerId)
                    .body(requestDTOList)
                    .retrieve()
                    .toBodilessEntity();

            logger.info("Inventory successfully deducted for customerId: {}", customerId);

        } catch (HttpClientErrorException ex) {
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                logger.error("Inventory not found for some products: {}", ex.getResponseBodyAsString());
                throw new InventoryNotFoundException("One or more products in the order are out of stock.", ex);
            } else {
                logger.error("Error while deducting inventory: {}", ex.getResponseBodyAsString(), ex);
                throw new InventoryServiceException("Failed to deduct inventory. Please try again later.", ex);
            }

        } catch (RestClientException ex) {
            logger.error("Inventory service communication error: {}", ex.getMessage(), ex);
            throw new InventoryServiceException("Unable to connect to the Inventory Service. Please try again later.", ex);
        }
    }

}