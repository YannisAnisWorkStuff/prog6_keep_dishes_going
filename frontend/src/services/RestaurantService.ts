import axios from 'axios';
import type {Restaurant} from '../model/Restaurant';

const BASE_URL = `${import.meta.env.VITE_BACKEND_URL}/restaurants`;

export async function fetchRestaurantByOwner(ownerId: string): Promise<Restaurant | null> {
    try {
        const {data} = await axios.get(`${BASE_URL}/owner/${ownerId}`);
        return data;
    } catch (error: any) {
        if (axios.isAxiosError(error) && error.response?.status === 404) {
            return null;
        }
        throw error;
    }
}

export async function createRestaurant(restaurant: Omit<Restaurant, 'id'>): Promise<Restaurant> {
    const {data} = await axios.post(BASE_URL, restaurant);
    return data;
}

export async function getAllRestaurants(): Promise<Restaurant[]> {
    const {data} = await axios.get(BASE_URL);
    return data;
}

export async function editRestaurant(restaurantId: string, data: Partial<Restaurant>) {
    const response = await axios.put(`${BASE_URL}/${restaurantId}`, data);
    return response.data;
}

export async function fetchRestaurantById(restaurantId: string): Promise<Restaurant> {
    const {data} = await axios.get(`${BASE_URL}/${restaurantId}`);
    return data;
}

export async function getRestaurantGuesstimate(restaurantId: string): Promise<number> {
    const {data} = await axios.get<number>(`${BASE_URL}/${restaurantId}/estimate`);
    return data;
}