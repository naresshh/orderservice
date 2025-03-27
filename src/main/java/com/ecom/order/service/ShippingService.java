package com.ecom.order.service;

import com.ecom.order.client.CustomerClient;
import com.ecom.order.dto.CustomerDTO;
import com.ecom.order.dto.ShippingAddressDTO;
import com.ecom.order.mapper.ShippingAddressMapper;
import com.ecom.order.modal.ShippingAddress;
import com.ecom.order.repository.ShippingAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShippingService {

    @Autowired
    private ShippingAddressRepository repository;

    @Autowired
    private CustomerClient customerClient;

    @Autowired
    private ShippingAddressMapper mapper;

    public List<ShippingAddressDTO> getUserAddresses(Long customerId) {
        return repository.findByCustomerId(customerId)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public ShippingAddressDTO addShippingAddress(Long customerId, ShippingAddressDTO dto,String jwtToken) {
        CustomerDTO customer = customerClient.getCustomerById(customerId, jwtToken);
        if (customer == null) {
            throw new RuntimeException("Customer not found with ID: " + customerId);
        }
        ShippingAddress address = mapper.toEntity(dto, customerId);
        return mapper.toDTO(repository.save(address));
    }
}