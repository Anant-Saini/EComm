package org.arth.ecomm.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.arth.ecomm.model.Order;
import org.arth.ecomm.model.OrderItem;
import org.arth.ecomm.model.Product;
import org.arth.ecomm.model.dto.OrderItemRequest;
import org.arth.ecomm.model.dto.OrderItemResponse;
import org.arth.ecomm.model.dto.OrderRequest;
import org.arth.ecomm.model.dto.OrderResponse;
import org.arth.ecomm.repository.OrderRepository;
import org.arth.ecomm.repository.ProductRepository;
import org.arth.ecomm.util.IdentifierUtils;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Transactional
    public OrderResponse placeOrder(OrderRequest req) {

        Order order = Order.builder()
                .orderId("ORD-"+IdentifierUtils.generateShortOrderId())
                .customerName(req.customerName())
                .email(req.email())
                .status("PLACED")
                .orderDate(LocalDate.now())
                .build();

        List<OrderItem> orderItems = new ArrayList<>();
        for(OrderItemRequest oir: req.items()) {
            Product prod = productRepository.findById(oir.productId())
                    .orElseThrow(() -> new RuntimeException("Product: "+oir.productId()+" not found!"));
            if(prod.getStockQuantity() < oir.quantity())
                throw new RuntimeException("Insufficient Stock for Product: "+prod.getName());
            prod.setStockQuantity(prod.getStockQuantity() - oir.quantity());
            productRepository.save(prod);
            OrderItem orderItem = OrderItem.builder()
                    .product(prod)
                    .quantity(oir.quantity())
                    .totalPrice(prod.getPrice().multiply(BigDecimal.valueOf(oir.quantity())))
                    .order(order)
                    .build();
            orderItems.add(orderItem);
        }
        order.setOrderItems(orderItems);
        Order savedOrder = orderRepository.save(order);
        return new OrderResponse(
                savedOrder.getOrderId(),
                savedOrder.getCustomerName(),
                savedOrder.getEmail(),
                savedOrder.getStatus(),
                savedOrder.getOrderDate(),
                getListOfOrderItemResponse(savedOrder));
    }

    public List<OrderResponse> getAllOrderResponse() {

        List<Order> orders = orderRepository.findAllWithOrderItems();

        return orders.stream().map(ord -> new OrderResponse(
                ord.getOrderId(),
                ord.getCustomerName(),
                ord.getEmail(),
                ord.getStatus(),
                ord.getOrderDate(),
                getListOfOrderItemResponse(ord))
        ).toList();
    }

    private List<OrderItemResponse> getListOfOrderItemResponse(Order order) {
        return order.getOrderItems().stream().map((oi) -> {
            return new OrderItemResponse(oi.getProduct().getName(), oi.getQuantity(), oi.getTotalPrice());
        }).toList();
    }
}
