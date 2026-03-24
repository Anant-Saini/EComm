package org.arth.ecomm.controller;

import lombok.RequiredArgsConstructor;
import org.arth.ecomm.model.dto.OrderRequest;
import org.arth.ecomm.model.dto.OrderResponse;
import org.arth.ecomm.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/orders/place")
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody OrderRequest orderRequest) {
        OrderResponse orderResponse = orderService.placeOrder(orderRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderResponse);
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderResponse>> getAllOrderResponse() {
        List<OrderResponse> orderResponses = orderService.getAllOrderResponse();
        return ResponseEntity.ok(orderResponses);

    }
}
