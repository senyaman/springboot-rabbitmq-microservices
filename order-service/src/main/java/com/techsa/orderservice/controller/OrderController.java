package com.techsa.orderservice.controller;

import com.techsa.orderservice.dto.Order;
import com.techsa.orderservice.dto.OrderEvent;
import com.techsa.orderservice.publisher.OrderProducer;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class OrderController {

    private OrderProducer orderProducer;

    @PostMapping("/orders")
    public ResponseEntity<String> placeOrder(@RequestBody Order order) {

        order.setOrderId(UUID.randomUUID().toString());

        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setStatus("Pending");
        orderEvent.setMessage("Your order is in pending mode");
        orderEvent.setOrder(order);

        orderProducer.sendMessage(orderEvent);

        return ResponseEntity.ok("Order sent to the RabbitMQ...");
    }

}
