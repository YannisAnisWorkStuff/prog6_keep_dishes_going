import axios from 'axios';
import type {Dish} from '../model/Dish/Dish';

const BASE_URL = `${import.meta.env.VITE_BACKEND_URL}/restaurants`;

function mapDishToBackendRequest(dish: Omit<Dish, 'id'>) {
    return {
        name: dish.name,
        type: dish.dishType,
        description: dish.description,
        price: dish.price,
        pictureUrl: dish.pictureUrl,
        foodTags: dish.foodTags,
    };
}

export async function addDish(restaurantId: string, dish: Omit<Dish, 'id'>): Promise<Dish> {
    const payload = mapDishToBackendRequest(dish);
    const {data} = await axios.post(`${BASE_URL}/${restaurantId}/dishes`, payload);
    return data;
}

export async function editDishAsDraft(restaurantId: string, dishId: string, dish: Omit<Dish, 'id'>): Promise<void> {
    const payload = mapDishToBackendRequest(dish);
    await axios.put(`${BASE_URL}/${restaurantId}/dishes/${dishId}/draft`, payload);
}

export async function publishDish(restaurantId: string, dishId: string): Promise<void> {
    await axios.put(`${BASE_URL}/${restaurantId}/dishes/${dishId}/publish`);
}

export async function unpublishDish(restaurantId: string, dishId: string): Promise<void> {
    await axios.put(`${BASE_URL}/${restaurantId}/dishes/${dishId}/unpublish`);
}

export async function getDishesByRestaurant(restaurantId: string): Promise<Dish[]> {
    const {data} = await axios.get(`${BASE_URL}/${restaurantId}/dishes`);
    return data;
}

export async function markDishOutOfStock(restaurantId: string, dishId: string) {
    await axios.put(`${BASE_URL}/${restaurantId}/dishes/${dishId}/out-of-stock`);
}

export async function markDishBackInStock(restaurantId: string, dishId: string) {
    await axios.put(`${BASE_URL}/${restaurantId}/dishes/${dishId}/back-in-stock`);
}

export async function applyPendingDishes(restaurantId: string): Promise<void> {
    await axios.post(`${BASE_URL}/${restaurantId}/dishes/apply-pending`);
}

export async function deleteDish(restaurantId: string, dishId: string): Promise<void> {
    await axios.delete(`${BASE_URL}/${restaurantId}/dishes/${dishId}`);
}