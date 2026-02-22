import type {BasketItem} from "./BasketItem.ts";

export interface Basket {
    basketId: string;
    customerId: string;
    restaurantId: string;
    items: BasketItem[];
    totalPrice: number;
}