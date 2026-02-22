package com.yiannisftiti.prog6.keepdishesgoing.owner.adapter.in.controller;

import com.yiannisftiti.prog6.keepdishesgoing.owner.adapter.in.request.*;
import com.yiannisftiti.prog6.keepdishesgoing.owner.adapter.in.response.DishDTO;
import com.yiannisftiti.prog6.keepdishesgoing.owner.adapter.in.response.RestaurantDTO;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.*;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.exceptions.PublishedDishLimitExceededException;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.exceptions.RestaurantNotFoundException;
import com.yiannisftiti.prog6.keepdishesgoing.owner.port.in.dish.*;
import com.yiannisftiti.prog6.keepdishesgoing.owner.port.in.restaurant.*;
import com.yiannisftiti.prog6.keepdishesgoing.owner.port.out.owner.LoadOwnerPort;
import com.yiannisftiti.prog6.keepdishesgoing.owner.port.out.restaurant.LoadDishPort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    private final RegisterRestaurantUseCase registerRestaurantUseCase;
    private final GetRestaurantUseCase getRestaurantUseCase;
    private final AddDishUseCase addDishUseCase;
    private final EditDishAsDraftUseCase editDishAsDraftUseCase;
    private final DeleteDishUseCase deleteDishUseCase;
    private final PublishDishUseCase publishDishUseCase;
    private final UnpublishDishUseCase unpublishDishUseCase;
    private final ApplyPendingDishesUseCase applyPendingDishesUseCase;
    private final MarkDishBackInStockUseCase markDishBackInStockUseCase;
    private final MarkDishOutOfStockUseCase markDishOutOfStockUseCase;
    private final EditRestaurantUseCase editRestaurantUseCase;
    private final LoadDishPort loadDishPort;
    private final LoadOwnerPort loadOwnerPort;

    public RestaurantController(
            RegisterRestaurantUseCase registerRestaurantUseCase,
            GetRestaurantUseCase getRestaurantUseCase,
            AddDishUseCase addDishUseCase,
            EditDishAsDraftUseCase editDishAsDraftUseCase,
            PublishDishUseCase publishDishUseCase,
            UnpublishDishUseCase unpublishDishUseCase,
            DeleteDishUseCase deleteDishUseCase,
            LoadDishPort loadDishPort,
            ApplyPendingDishesUseCase applyPendingDishesUseCase,
            MarkDishOutOfStockUseCase markDishOutOfStockUseCase,
            MarkDishBackInStockUseCase markDishBackInStockUseCase,
            EditRestaurantUseCase editRestaurantUseCase,
            LoadOwnerPort loadOwnerPort) {

        this.registerRestaurantUseCase = registerRestaurantUseCase;
        this.getRestaurantUseCase = getRestaurantUseCase;
        this.addDishUseCase = addDishUseCase;
        this.editDishAsDraftUseCase = editDishAsDraftUseCase;
        this.publishDishUseCase = publishDishUseCase;
        this.unpublishDishUseCase = unpublishDishUseCase;
        this.deleteDishUseCase = deleteDishUseCase;
        this.loadDishPort = loadDishPort;
        this.applyPendingDishesUseCase = applyPendingDishesUseCase;
        this.markDishBackInStockUseCase = markDishBackInStockUseCase;
        this.markDishOutOfStockUseCase = markDishOutOfStockUseCase;
        this.editRestaurantUseCase = editRestaurantUseCase;
        this.loadOwnerPort = loadOwnerPort;
    }


    @GetMapping
    public ResponseEntity<List<RestaurantDTO>> getAllRestaurants() {
        List<Restaurant> restaurants = this.getRestaurantUseCase.getRestaurants();
        return ResponseEntity.ok(restaurants.stream().map(RestaurantDTO::fromDomain).toList());
    }

    @GetMapping("/{restaurantId}")
    public ResponseEntity<RestaurantDTO> getRestaurantById(@PathVariable String restaurantId) {
        try {
            Restaurant restaurant = getRestaurantUseCase.getRestaurantById(RestaurantId.of(UUID.fromString(restaurantId)));
            return ResponseEntity.ok(RestaurantDTO.fromDomain(restaurant));
        } catch (RestaurantNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<RestaurantDTO> getRestaurantByOwner(@PathVariable String ownerId) {
        try {
            Restaurant restaurant = getRestaurantUseCase.getRestaurantByOwnerId(new OwnerId(UUID.fromString(ownerId)));
            return ResponseEntity.ok(RestaurantDTO.fromDomain(restaurant));
        } catch (RestaurantNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{restaurantId}/dishes")
    public ResponseEntity<List<DishDTO>> getDishesByRestaurant(@PathVariable String restaurantId) {
        List<Dish> dishes = loadDishPort.loadDishesByRestaurant(RestaurantId.of(UUID.fromString(restaurantId)));
        return ResponseEntity.ok(dishes.stream().map(DishDTO::fromDomain).toList());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('owner')")
    public ResponseEntity<RestaurantDTO> registerRestaurant(
            @RequestBody RegisterRestaurantRequest request,
            @AuthenticationPrincipal Jwt jwt
    ) {
        String email = jwt.getClaimAsString("email");
        Owner owner = loadOwnerPort.loadByEmail(email)
                .orElseThrow(() -> new IllegalStateException("Owner not found"));

        RegisterRestaurantCommand command = new RegisterRestaurantCommand(
                owner.getId(),
                request.name(),
                request.address(),
                request.cuisineType(),
                request.email(),
                request.preparationTimeMinutes(),
                request.schedules(),
                request.pictures()
        );

        Restaurant restaurant = registerRestaurantUseCase.registerRestaurant(command);
        return ResponseEntity.ok(RestaurantDTO.fromDomain(restaurant));
    }

    @PutMapping("/{restaurantId}")
    @PreAuthorize("hasAuthority('owner')")
    public ResponseEntity<RestaurantDTO> editRestaurant(
            @PathVariable String restaurantId,
            @RequestBody EditRestaurantRequest request,
            @AuthenticationPrincipal Jwt jwt
    ) throws RestaurantNotFoundException {
        enforceOwnership(jwt, restaurantId);

        EditRestaurantCommand command = new EditRestaurantCommand(
                RestaurantId.of(UUID.fromString(restaurantId)),
                request.name(),
                request.address(),
                request.cuisineType(),
                request.email(),
                request.pictures(),
                request.preparationTimeMinutes(),
                request.schedules()
        );

        Restaurant updated = editRestaurantUseCase.editRestaurant(command);
        return ResponseEntity.ok(RestaurantDTO.fromDomain(updated));
    }

    @PostMapping("/{restaurantId}/dishes")
    @PreAuthorize("hasAuthority('owner')")
    public ResponseEntity<DishDTO> addDish(
            @PathVariable String restaurantId,
            @RequestBody RegisterDishRequest request,
            @AuthenticationPrincipal Jwt jwt
    ) throws RestaurantNotFoundException {
        enforceOwnership(jwt, restaurantId);

        AddDishCommand command = new AddDishCommand(
                RestaurantId.of(UUID.fromString(restaurantId)),
                request.name(),
                request.type(),
                request.description(),
                request.price(),
                request.pictureUrl(),
                request.foodTags()
        );

        Dish dish = addDishUseCase.addDish(RestaurantId.of(UUID.fromString(restaurantId)), command);
        return ResponseEntity.ok(DishDTO.fromDomain(dish));
    }

    @PutMapping("/{restaurantId}/dishes/{dishId}/draft")
    @PreAuthorize("hasAuthority('owner')")
    public ResponseEntity<Void> editDishAsDraft(
            @PathVariable String restaurantId,
            @PathVariable String dishId,
            @RequestBody EditDishAsDraftRequest request,
            @AuthenticationPrincipal Jwt jwt
    ) throws RestaurantNotFoundException {
        enforceOwnership(jwt, restaurantId);

        EditDishAsDraftCommand command = new EditDishAsDraftCommand(
                DishId.of(UUID.fromString(dishId)),
                RestaurantId.of(UUID.fromString(restaurantId)),
                request.name(),
                request.type(),
                request.description(),
                request.price(),
                request.pictureUrl(),
                request.foodTags()
        );

        editDishAsDraftUseCase.editDishAsDraft(command);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{restaurantId}/dishes/{dishId}/publish")
    @PreAuthorize("hasAuthority('owner')")
    public ResponseEntity<Void> publishDish(@PathVariable String restaurantId, @PathVariable String dishId, @AuthenticationPrincipal Jwt jwt) throws RestaurantNotFoundException {
        enforceOwnership(jwt, restaurantId);
        publishDishUseCase.publishDish(DishId.of(UUID.fromString(dishId)), RestaurantId.of(UUID.fromString(restaurantId)));
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{restaurantId}/dishes/{dishId}/unpublish")
    @PreAuthorize("hasAuthority('owner')")
    public ResponseEntity<Void> unpublishDish(@PathVariable String restaurantId, @PathVariable String dishId, @AuthenticationPrincipal Jwt jwt) throws RestaurantNotFoundException {
        enforceOwnership(jwt, restaurantId);
        unpublishDishUseCase.unpublishDish(DishId.of(UUID.fromString(dishId)), RestaurantId.of(UUID.fromString(restaurantId)));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{restaurantId}/dishes/{dishId}")
    @PreAuthorize("hasAuthority('owner')")
    public ResponseEntity<Void> deleteDish(@PathVariable String restaurantId, @PathVariable String dishId, @AuthenticationPrincipal Jwt jwt) throws RestaurantNotFoundException {
        enforceOwnership(jwt, restaurantId);
        deleteDishUseCase.deleteDish(DishId.of(UUID.fromString(dishId)), RestaurantId.of(UUID.fromString(restaurantId)));
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{restaurantId}/dishes/apply-pending")
    @PreAuthorize("hasAuthority('owner')")
    public ResponseEntity<Void> applyAllPendingDishes(
            @PathVariable String restaurantId,
            @AuthenticationPrincipal Jwt jwt
    ) {
        try {
            enforceOwnership(jwt, restaurantId);
            applyPendingDishesUseCase.applyAllPendingDishes(RestaurantId.of(UUID.fromString(restaurantId)));
            return ResponseEntity.ok().build();
        } catch (RestaurantNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (SecurityException e) {
            return ResponseEntity.status(403).build();
        }
    }

    @PutMapping("/{restaurantId}/dishes/{dishId}/out-of-stock")
    @PreAuthorize("hasAuthority('owner')")
    public ResponseEntity<Void> markOutOfStock(
            @PathVariable String restaurantId,
            @PathVariable String dishId,
            @AuthenticationPrincipal Jwt jwt
    ) {
        try {
            enforceOwnership(jwt, restaurantId);
            markDishOutOfStockUseCase.markOutOfStock(
                    DishId.of(UUID.fromString(dishId)),
                    RestaurantId.of(UUID.fromString(restaurantId))
            );
            return ResponseEntity.ok().build();
        } catch (RestaurantNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (SecurityException e) {
            return ResponseEntity.status(403).build();
        }
    }

    @PutMapping("/{restaurantId}/dishes/{dishId}/back-in-stock")
    @PreAuthorize("hasAuthority('owner')")
    public ResponseEntity<Void> markBackInStock(
            @PathVariable String restaurantId,
            @PathVariable String dishId,
            @AuthenticationPrincipal Jwt jwt
    ) {
        try {
            enforceOwnership(jwt, restaurantId);
            markDishBackInStockUseCase.markBackInStock(
                    DishId.of(UUID.fromString(dishId)),
                    RestaurantId.of(UUID.fromString(restaurantId))
            );
            return ResponseEntity.ok().build();
        } catch (RestaurantNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (SecurityException e) {
            return ResponseEntity.status(403).build();
        }
    }

    @GetMapping("{restaurantId}/estimate")
    public ResponseEntity<Double> getGuesstimate(@PathVariable String restaurantId) {
        return ResponseEntity.ok(this.getRestaurantUseCase.getGuesstimate(RestaurantId.of(UUID.fromString(restaurantId))));
    }

    private void enforceOwnership(Jwt jwt, String restaurantId) {
        String email = jwt.getClaimAsString("email");
        Owner owner = loadOwnerPort.loadByEmail(email)
                .orElseThrow(() -> new IllegalStateException("Owner not found"));

        System.out.println("DEBUG: Owner " + owner.getId().id() + " owns restaurant "
                + (owner.getRestaurant().id() != null ? owner.getRestaurant().id() : "null")
                + ", trying to edit restaurant " + restaurantId);

        if (owner.getRestaurant().id() == null ||
                !owner.getRestaurant().id().toString().equals(restaurantId)) {
            throw new SecurityException("You are not allowed to manage this restaurant.");
        }
    }

}
