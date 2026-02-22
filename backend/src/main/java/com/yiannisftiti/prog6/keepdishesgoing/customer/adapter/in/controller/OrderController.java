package com.yiannisftiti.prog6.keepdishesgoing.customer.adapter.in.controller;

import com.yiannisftiti.prog6.keepdishesgoing.customer.adapter.in.request.GuestOrderRequest;
import com.yiannisftiti.prog6.keepdishesgoing.customer.adapter.in.response.OrderDTO;
import com.yiannisftiti.prog6.keepdishesgoing.customer.domain.Customer;
import com.yiannisftiti.prog6.keepdishesgoing.customer.domain.Order;
import com.yiannisftiti.prog6.keepdishesgoing.customer.domain.exception.CustomerNotFoundException;
import com.yiannisftiti.prog6.keepdishesgoing.customer.port.in.CreateGuestOrderCommand;
import com.yiannisftiti.prog6.keepdishesgoing.customer.port.in.CreateOrderCommand;
import com.yiannisftiti.prog6.keepdishesgoing.customer.port.in.CreateOrderUseCase;
import com.yiannisftiti.prog6.keepdishesgoing.customer.adapter.in.request.CheckoutRequest;
import com.yiannisftiti.prog6.keepdishesgoing.customer.port.in.GetOrderStatusUseCase;
import com.yiannisftiti.prog6.keepdishesgoing.customer.port.out.LoadCustomerPort;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.exceptions.RestaurantNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final CreateOrderUseCase createOrderUseCase;
    private final GetOrderStatusUseCase getOrderStatusUseCase;
    private final LoadCustomerPort loadCustomerPort;

    public OrderController(CreateOrderUseCase createOrderUseCase, GetOrderStatusUseCase getOrderStatusUseCase, LoadCustomerPort loadCustomerPort) {
        this.createOrderUseCase = createOrderUseCase;
        this.getOrderStatusUseCase = getOrderStatusUseCase;
        this.loadCustomerPort = loadCustomerPort;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('customer')")
    public ResponseEntity<OrderDTO> createOrder(@RequestBody CheckoutRequest request,
                                                @AuthenticationPrincipal Jwt jwt) {
        String email = jwt.getClaimAsString("email");
        Customer customer = loadCustomerPort.loadCustomerByEmail(email)
                .orElseThrow(() -> new CustomerNotFoundException(email));

        if (!customer.getId().value().equals(request.customerId())) {
            throw new SecurityException("You are not allowed to create an order for another customer.");
        }

        Order order = createOrderUseCase.createOrder(
                new CreateOrderCommand(
                        customer.getId().value(),
                        request.restaurantId(),
                        request.customerName(),
                        request.deliveryAddress()
                )
        );

        return ResponseEntity.ok(OrderDTO.toDTO(order));
    }


    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDTO> getOrderStatus(@PathVariable UUID orderId) {
        return getOrderStatusUseCase.getOrder(orderId)
                .map(order -> ResponseEntity.ok(OrderDTO.toDTO(order)))
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping("/guest")
    public ResponseEntity<OrderDTO> createGuestOrder(@RequestBody GuestOrderRequest request) {
        Order order = createOrderUseCase.createGuestOrder(new CreateGuestOrderCommand(request.customerName(), request.restaurantId(),request.deliveryAddress(),request.items()));
        return ResponseEntity.ok(OrderDTO.toDTO(order));
    }


}