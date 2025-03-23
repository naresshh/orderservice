package com.ecom.order.client;

import com.ecom.order.dto.OrderItemDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class CartClient {

    private final RestClient restClient;
    @Value("${cart.service.base-url}")
    private String cartServiceBaseUrl;

    public CartClient(RestClient restClient) {
        this.restClient = restClient;
        //this.cartServiceBaseUrl = cartServiceBaseUrl;
    }

    public List<OrderItemDTO> getCartItems(Long customerId) {
        System.out.println(cartServiceBaseUrl+"/cart/"+customerId);
        return restClient.get()
                .uri(cartServiceBaseUrl + "/cart/" + customerId)
                .retrieve()
                .body(new ParameterizedTypeReference<List<OrderItemDTO>>() {});
    }

    public void clearCart(Long customerId) {
        restClient.delete()
                .uri(cartServiceBaseUrl + "/cart/clear/" + customerId)
                .retrieve()
                .toBodilessEntity();
    }
}