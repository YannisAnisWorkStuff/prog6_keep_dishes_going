package com.yiannisftiti.prog6.keepdishesgoing.customer.adapter.out.basket;

import com.yiannisftiti.prog6.keepdishesgoing.customer.domain.Basket;
import com.yiannisftiti.prog6.keepdishesgoing.customer.domain.BasketId;
import com.yiannisftiti.prog6.keepdishesgoing.customer.port.out.LoadBasketPort;
import com.yiannisftiti.prog6.keepdishesgoing.customer.port.out.SaveBasketPort;
import com.yiannisftiti.prog6.keepdishesgoing.customer.port.out.UpdateBasketPort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class BasketJPAAdapter implements LoadBasketPort, SaveBasketPort, UpdateBasketPort {

    private final BasketJPARepository basketRepository;

    public BasketJPAAdapter(BasketJPARepository basketRepository) {
        this.basketRepository = basketRepository;
    }

    @Override
    public Optional<Basket> loadBasket(UUID customerId) {
        return basketRepository.findByCustomerId(customerId)
                .map(this::toDomain);
    }

    @Override
    public void saveBasket(Basket basket) {
        BasketJPAEntity entity = basketRepository.findByCustomerId(basket.getCustomerId())
                .orElseGet(BasketJPAEntity::new);

        entity.setId(basket.getId().value());
        entity.setCustomerId(basket.getCustomerId());
        entity.setRestaurantId(basket.getRestaurantId());
        entity.clearItems();

        basket.getItems().forEach(item -> {
            BasketItemJPAEntity itemEntity = new BasketItemJPAEntity();
            itemEntity.setId(UUID.randomUUID());
            itemEntity.setDishId(item.getDishId());
            itemEntity.setName(item.getName());
            itemEntity.setPrice(item.getPrice());
            itemEntity.setQuantity(item.getQuantity());
            entity.addItem(itemEntity);
        });

        basketRepository.save(entity);
    }

    private Basket toDomain(BasketJPAEntity entity) {
        Basket basket = new Basket(
                new BasketId(entity.getId()),
                entity.getCustomerId(),
                entity.getRestaurantId()
        );

        entity.getItems().forEach(i -> basket.addDish(i.getDishId(), i.getName(), i.getPrice(), i.getQuantity())
        );

        return basket;
    }

    @Override
    public List<Basket> findAllByRestaurant(UUID restaurantId) {
        return basketRepository.findByRestaurantId(restaurantId).stream()
                .map(this::toDomain)
                .toList();
    }


    @Override
    public void clearBasket(UUID customerId) {
        BasketJPAEntity basket = basketRepository.findByCustomerId(customerId).orElse(null);
        if (basket != null) {
            basket.getItems().clear();
            basket.setRestaurantId(new UUID(0,0));
            basketRepository.save(basket);
        }
    }

}