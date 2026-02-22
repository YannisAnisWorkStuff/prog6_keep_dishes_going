import {useQueryClient} from "@tanstack/react-query";
import {useAddToBasket, useClearBasket, useRemoveFromBasket, useUpdateBasketQuantity,} from "./UseBasket";

export function useBasketActions(customerId?: string, markUserChange?: () => void) {
    const queryClient = useQueryClient();
    const {mutateAsync: addToBasket} = useAddToBasket();
    const {mutateAsync: removeDish} = useRemoveFromBasket();
    const {mutateAsync: updateQuantity} = useUpdateBasketQuantity();
    const {mutateAsync: clear} = useClearBasket();

    function getLocalBasket() {
        const saved = localStorage.getItem("guest_basket");
        return saved ? JSON.parse(saved) : {items: []};
    }

    function saveLocalBasket(basket: any) {
        localStorage.setItem("guest_basket", JSON.stringify(basket));
        queryClient.setQueryData(["basket", "guest"], basket);
    }

    async function handleAddToBasket(restaurantId: string, dish: any, quantity: number) {
        if (customerId) {
            await addToBasket({customerId, restaurantId, dishId: dish.id, quantity});
            markUserChange?.();
            return;
        }

        const basket = getLocalBasket();
        const existing = basket.items.find((i: any) => i.dishId === dish.id);
        if (existing) {
            existing.quantity += quantity;
        } else {
            basket.items.push({
                dishId: dish.id,
                restaurantId,
                name: dish.name,
                price: dish.price,
                quantity,
            });
        }
        saveLocalBasket(basket);
        markUserChange?.();
    }

    async function handleRemoveDish(dishId: string) {
        if (customerId) {
            await removeDish({customerId, dishId});
            markUserChange?.();
            return;
        }

        const basket = getLocalBasket();
        basket.items = basket.items.filter((i: any) => i.dishId !== dishId);
        saveLocalBasket(basket);
        markUserChange?.();
    }

    async function handleUpdateQuantity(dishId: string, quantity: number) {
        if (customerId) {
            await updateQuantity({customerId, dishId, quantity});
            markUserChange?.();
            return;
        }

        const basket = getLocalBasket();
        const item = basket.items.find((i: any) => i.dishId === dishId);
        if (item) item.quantity = quantity;
        saveLocalBasket(basket);
        markUserChange?.();
    }

    async function clearBasket() {
        if (customerId) {
            await clear(customerId);
            markUserChange?.();
            return;
        }

        localStorage.removeItem("guest_basket");
        queryClient.setQueryData(["basket", "guest"], {items: []});
        markUserChange?.();
    }

    return {handleAddToBasket, handleRemoveDish, handleUpdateQuantity, clearBasket};
}
