package org.arth.ecomm.service;

import lombok.RequiredArgsConstructor;
import org.arth.ecomm.model.Order;
import org.arth.ecomm.model.OrderItem;
import org.arth.ecomm.model.Product;
import org.arth.ecomm.model.dto.OrderItemRequest;
import org.arth.ecomm.model.dto.OrderRequest;
import org.arth.ecomm.model.dto.OrderResponse;
import org.arth.ecomm.repository.OrderRepository;
import org.arth.ecomm.repository.ProductRepository;
import org.arth.ecomm.util.IdentifierUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderResponse placeOrder(OrderRequest req) {

        List<OrderItem> orderItems = new ArrayList<>();
        for(OrderItemRequest oir: req.items()) {
            Product prodProxy = productRepository.getReferenceById(oir.productId());
            OrderItem orderItem = OrderItem.builder()
                    .product(prodProxy)
                    .quantity(oir.quantity())
                    .totalPrice(prodProxy.getPrice().multiply(BigDecimal.valueOf(oir.quantity())))
                    .build();
            orderItems.add(orderItem);
        }

        Order order = Order.builder()
                .orderId("ORD-"+IdentifierUtils.generateShortOrderId())
                .customerName(req.customerName())
                .email(req.email())
                .status("PLACED")
                .orderDate(LocalDate.now())
                .orderItems(orderItems)
                .build();

        orderRepository.save(order);
        return new OrderResponse(null, null, null, null, null, null);
    }

    public List<OrderResponse> getAllOrderResponse() {

        return null;
    }
}
