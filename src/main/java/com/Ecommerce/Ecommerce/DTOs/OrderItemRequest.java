package com.Ecommerce.Ecommerce.DTOs;
import lombok.Data;

@Data
public class OrderItemRequest {

    private Long productId;
    private int quantity;
}
