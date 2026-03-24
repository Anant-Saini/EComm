package org.arth.ecomm.model.dto;

public record OrderItemRequest(
        long productId,
        int quantity
        ) { }
