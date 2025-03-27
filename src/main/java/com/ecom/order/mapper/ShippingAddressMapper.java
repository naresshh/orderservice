package com.ecom.order.mapper;

import com.ecom.order.dto.ShippingAddressDTO;
import com.ecom.order.modal.ShippingAddress;
import org.springframework.stereotype.Component;

@Component
public class ShippingAddressMapper {

    public ShippingAddressDTO toDTO(ShippingAddress address) {
        return new ShippingAddressDTO(
            address.getId(),
            address.getName(),
            address.getEmail(),
            address.getPhone(),
            address.getStreet(),
            address.getCity(),
            address.getState(),
            address.getZipCode()
        );
    }

    public ShippingAddress toEntity(ShippingAddressDTO dto, Long customerId) {
        ShippingAddress address = new ShippingAddress();
        address.setCustomerId(customerId);
        address.setName(dto.getName());
        address.setEmail(dto.getEmail());
        address.setPhone(dto.getPhone());
        address.setStreet(dto.getStreet());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setZipCode(dto.getZipCode());
        return address;
    }
}