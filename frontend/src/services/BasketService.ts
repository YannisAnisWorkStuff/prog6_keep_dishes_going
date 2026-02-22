import axios from "axios";
import type {Basket} from "../model/Basket";

const BASE_URL = `${import.meta.env.VITE_BACKEND_URL}/api/customers`;

export async function getBasket(customerId: string): Promise<Basket> {
    const {data} = await axios.get(`${BASE_URL}/${customerId}/basket`);
    return data;
}

export async function addDishToBasket(params: {
    customerId: string;
    restaurantId: string;
    dishId: string;
    quantity: number;
}): Promise<void> {
    await axios.post(`${BASE_URL}/${params.customerId}/basket`, params);
}

export async function removeDishFromBasket(params: {
    customerId: string;
    dishId: string;
}): Promise<void> {
    await axios.delete(`${BASE_URL}/${params.customerId}/basket/${params.dishId}`);
}

export async function updateBasketQuantity(params: {
    customerId: string;
    dishId: string;
    quantity: number;
}): Promise<void> {
    await axios.patch(
        `${BASE_URL}/${params.customerId}/basket/${params.dishId}`,
        {quantity: params.quantity}
    );
}

export async function clearBasket(customerId: string) {
    await axios.delete(`${BASE_URL}/${customerId}/basket`);
}