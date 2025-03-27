package com.ecom.order.dto;

public class ShippingInfoDTO {
    private String name;
    private String email;
    private String phone;
    private ShippingAddressDTO address;  // Now Address is a DTO, not an entity

    // Constructors
    public ShippingInfoDTO() {}

    public ShippingInfoDTO(String name, String email, String phone, ShippingAddressDTO address) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    // Getters and Setters


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public ShippingAddressDTO getAddress() {
        return address;
    }

    public void setAddress(ShippingAddressDTO address) {
        this.address = address;
    }
}