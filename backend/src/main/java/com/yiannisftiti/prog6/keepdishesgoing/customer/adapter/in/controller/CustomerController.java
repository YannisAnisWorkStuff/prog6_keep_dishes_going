package com.yiannisftiti.prog6.keepdishesgoing.customer.adapter.in.controller;

import com.yiannisftiti.prog6.keepdishesgoing.customer.adapter.in.request.AddDishToBasketRequest;
import com.yiannisftiti.prog6.keepdishesgoing.customer.adapter.in.request.RegisterCustomerRequest;
import com.yiannisftiti.prog6.keepdishesgoing.customer.adapter.in.request.UpdateBasketQuantityRequest;
import com.yiannisftiti.prog6.keepdishesgoing.customer.adapter.in.response.BasketDTO;
import com.yiannisftiti.prog6.keepdishesgoing.customer.adapter.in.response.CustomerDTO;
import com.yiannisftiti.prog6.keepdishesgoing.customer.domain.Customer;
import com.yiannisftiti.prog6.keepdishesgoing.customer.domain.CustomerId;
import com.yiannisftiti.prog6.keepdishesgoing.customer.domain.exception.CustomerNotFoundException;
import com.yiannisftiti.prog6.keepdishesgoing.customer.port.in.*;
import com.yiannisftiti.prog6.keepdishesgoing.customer.port.out.LoadCustomerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final RegisterCustomerUseCase registerCustomerUseCase;
    private final GetBasketUseCase getBasketUseCase;
    private final LoadCustomerPort loadCustomerPort;
    private final AddDishToBasketUseCase addDishToBasketUseCase;
    private final RemoveDishFromBasketUseCase removeDishFromBasketUseCase;
    private final UpdateBasketQuantityUseCase updateBasketQuantityUseCase;
    private final ClearBasketUseCase clearBasketUseCase;

    public CustomerController(RegisterCustomerUseCase registerCustomerUseCase,
                              GetBasketUseCase getBasketUseCase,
                              LoadCustomerPort loadCustomerPort,
                              AddDishToBasketUseCase addDishToBasketUseCase,
                              RemoveDishFromBasketUseCase removeDishFromBasketUseCase,
                              UpdateBasketQuantityUseCase updateBasketQuantityUseCase,
                              ClearBasketUseCase clearBasketUseCase) {
        this.registerCustomerUseCase = registerCustomerUseCase;
        this.getBasketUseCase = getBasketUseCase;
        this.loadCustomerPort = loadCustomerPort;
        this.addDishToBasketUseCase = addDishToBasketUseCase;
        this.removeDishFromBasketUseCase = removeDishFromBasketUseCase;
        this.updateBasketQuantityUseCase = updateBasketQuantityUseCase;
        this.clearBasketUseCase = clearBasketUseCase;
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> register(@RequestBody RegisterCustomerRequest request) {
        Customer customer = registerCustomerUseCase.registerCustomer(
                new RegisterCustomerCommand(request.name(), request.email(), request.password(), request.address())
        );
        return ResponseEntity.ok(CustomerDTO.fromDomain(customer));
    }

    @GetMapping("/{customerId}/basket")
    public ResponseEntity<BasketDTO> getBasket(@PathVariable UUID customerId) {
        return getBasketUseCase.getBasket(CustomerId.of(customerId))
                .map(BasketDTO::fromDomain)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/me")
    @PreAuthorize("hasAuthority('customer')")
    public ResponseEntity<CustomerDTO> getCurrentCustomer(@AuthenticationPrincipal Jwt jwt) {
        String email = jwt.getClaimAsString("email");
        Customer customer = loadCustomerPort.loadCustomerByEmail(email)
                .orElseThrow(() -> new CustomerNotFoundException(email));
        return ResponseEntity.ok(CustomerDTO.fromDomain(customer));
    }

    @PostMapping("/{customerId}/basket")
    @PreAuthorize("hasAuthority('customer')")
    public ResponseEntity<Void> addDishToBasket(@PathVariable UUID customerId,
                                                @RequestBody AddDishToBasketRequest request,
                                                @AuthenticationPrincipal Jwt jwt) {
        enforceCustomerIdentity(jwt, customerId);
        addDishToBasketUseCase.addDishToBasket(
                new AddDishToBasketCommand(customerId, request.restaurantId(), request.dishId(), request.quantity())
        );
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{customerId}/basket/{dishId}")
    @PreAuthorize("hasAuthority('customer')")
    public ResponseEntity<Void> updateQuantity(@PathVariable UUID customerId,
                                               @PathVariable UUID dishId,
                                               @RequestBody UpdateBasketQuantityRequest request,
                                               @AuthenticationPrincipal Jwt jwt) {
        enforceCustomerIdentity(jwt, customerId);
        updateBasketQuantityUseCase.updateBasketQuantity(
                new UpdateBasketQuantityCommand(customerId, dishId, request.quantity())
        );
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{customerId}/basket/{dishId}")
    @PreAuthorize("hasAuthority('customer')")
    public ResponseEntity<Void> removeDishFromBasket(@PathVariable UUID customerId,
                                                     @PathVariable UUID dishId,
                                                     @AuthenticationPrincipal Jwt jwt) {
        enforceCustomerIdentity(jwt, customerId);
        removeDishFromBasketUseCase.removeDishFromBasket(
                new RemoveDishFromBasketCommand(customerId, dishId)
        );
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{customerId}/basket")
    @PreAuthorize("hasAuthority('customer')")
    public ResponseEntity<Void> deleteBasket(@PathVariable UUID customerId,
                                            @AuthenticationPrincipal Jwt jwt) {
        enforceCustomerIdentity(jwt, customerId);
        clearBasketUseCase.clearBasket(customerId);
        return ResponseEntity.noContent().build();
    }


    private void enforceCustomerIdentity(Jwt jwt, UUID pathCustomerId) {
        String email = jwt.getClaimAsString("email");
        Customer customer = loadCustomerPort.loadCustomerByEmail(email)
                .orElseThrow(() -> new CustomerNotFoundException(email));

        if (!customer.getId().value().equals(pathCustomerId)) {
            throw new SecurityException("You are not allowed to modify another customer's basket.");
        }
    }
}