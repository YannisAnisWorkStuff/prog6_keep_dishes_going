package com.yiannisftiti.prog6.keepdishesgoing.owner.adapter.in.controller;

import com.yiannisftiti.prog6.keepdishesgoing.owner.adapter.in.request.RejectOrderRequest;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.IncomingOrderProjection;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.Owner;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.OwnerId;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.RestaurantId;
import com.yiannisftiti.prog6.keepdishesgoing.owner.port.in.order.AcceptOrderUseCase;
import com.yiannisftiti.prog6.keepdishesgoing.owner.port.in.order.ReadyOrderUseCase;
import com.yiannisftiti.prog6.keepdishesgoing.owner.port.in.order.RejectOrderUseCase;
import com.yiannisftiti.prog6.keepdishesgoing.owner.port.out.order.FindAcceptedOrdersPort;
import com.yiannisftiti.prog6.keepdishesgoing.owner.port.out.order.FindPendingOrdersPort;
import com.yiannisftiti.prog6.keepdishesgoing.owner.port.out.owner.LoadOwnerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/owners/orders")
public class IncomingOrderController {

    private final FindPendingOrdersPort findPendingOrdersPort;
    private final FindAcceptedOrdersPort findAcceptedOrdersPort;
    private final AcceptOrderUseCase acceptOrderUseCase;
    private final RejectOrderUseCase rejectOrderUseCase;
    private final ReadyOrderUseCase readyOrderUseCase;
    private final LoadOwnerPort loadOwnerPort;

    public IncomingOrderController(
            FindPendingOrdersPort findPendingOrdersPort,
            FindAcceptedOrdersPort findAcceptedOrdersPort,
            AcceptOrderUseCase acceptOrderUseCase,
            RejectOrderUseCase rejectOrderUseCase,
            ReadyOrderUseCase readyOrderUseCase,
            LoadOwnerPort loadOwnerPort) {
        this.findPendingOrdersPort = findPendingOrdersPort;
        this.findAcceptedOrdersPort = findAcceptedOrdersPort;
        this.acceptOrderUseCase = acceptOrderUseCase;
        this.rejectOrderUseCase = rejectOrderUseCase;
        this.readyOrderUseCase = readyOrderUseCase;
        this.loadOwnerPort = loadOwnerPort;
    }

    @GetMapping("/{restaurantId}")
    @PreAuthorize("hasAuthority('owner')")
    public ResponseEntity<List<IncomingOrderProjection>> getPendingOrders(@PathVariable UUID restaurantId,
                                                                          @AuthenticationPrincipal Jwt jwt) {
        enforceRestaurantOwnership(jwt, restaurantId);
        return ResponseEntity.ok(findPendingOrdersPort.findAllPendingOrders(RestaurantId.of(restaurantId)));
    }

    @GetMapping("/{restaurantId}/accepted")
    @PreAuthorize("hasAuthority('owner')")
    public ResponseEntity<List<IncomingOrderProjection>> getAcceptedOrders(@PathVariable UUID restaurantId,
                                                                           @AuthenticationPrincipal Jwt jwt) {
        enforceRestaurantOwnership(jwt, restaurantId);
        return ResponseEntity.ok(findAcceptedOrdersPort.findAcceptedOrders(RestaurantId.of(restaurantId)));
    }

    @PostMapping("/{restaurantId}/{orderId}/accept")
    @PreAuthorize("hasAuthority('owner')")
    public ResponseEntity<Void> acceptOrder(@PathVariable UUID restaurantId,
                                            @PathVariable UUID orderId,
                                            @AuthenticationPrincipal Jwt jwt) {
        enforceRestaurantOwnership(jwt, restaurantId);
        acceptOrderUseCase.acceptOrder(restaurantId, orderId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{restaurantId}/{orderId}/reject")
    @PreAuthorize("hasAuthority('owner')")
    public ResponseEntity<Void> rejectOrder(@PathVariable UUID restaurantId, @PathVariable UUID orderId, @RequestBody RejectOrderRequest request,
                                            @AuthenticationPrincipal Jwt jwt) {
        enforceRestaurantOwnership(jwt, restaurantId);
        String reason = request.reason() != null ? request.reason() : "Owner rejected your order";
        rejectOrderUseCase.rejectOrder(restaurantId, orderId, reason);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{restaurantId}/{orderId}/ready")
    @PreAuthorize("hasAuthority('owner')")
    public ResponseEntity<Void> markAsReady(@PathVariable UUID restaurantId, @PathVariable UUID orderId, @AuthenticationPrincipal Jwt jwt) {
        enforceRestaurantOwnership(jwt, restaurantId);
        readyOrderUseCase.markReady(restaurantId, orderId);
        return ResponseEntity.ok().build();
    }

    private void enforceRestaurantOwnership(Jwt jwt, UUID restaurantId) {
        String email = jwt.getClaimAsString("email");
        Owner owner = loadOwnerPort.loadByEmail(email)
                .orElseThrow(() ->  OwnerId.ownerNotFoundException(email));

        if (!owner.hasRestaurant()) {
            throw new SecurityException("You are not allowed to manage orders for this restaurant.");
        }
    }
}
