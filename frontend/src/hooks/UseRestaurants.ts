import {useMutation, useQuery, useQueryClient} from '@tanstack/react-query';
import type {Restaurant} from '../model/Restaurant';
import {
    createRestaurant,
    editRestaurant,
    fetchRestaurantById,
    fetchRestaurantByOwner,
    getAllRestaurants,
    getRestaurantGuesstimate
} from "../services/RestaurantService.ts";
import {getDishesByRestaurant} from "../services/DishService.ts";
import type {Dish} from "../model/Dish/Dish.ts";

export function useRestaurant(ownerId: string) {
    return useQuery<Restaurant | null, Error>({
        queryKey: ['restaurant', ownerId],
        queryFn: () => fetchRestaurantByOwner(ownerId),
        enabled: !!ownerId,
        refetchOnMount: 'always',
    });
}

export function useRestaurantDiscovery() {
    return useQuery<Restaurant[], Error>({
        queryKey: ['restaurants', 'discovery'],
        queryFn: getAllRestaurants,
        refetchOnMount: 'always',
    });
}

export function useCreateRestaurant() {
    const queryClient = useQueryClient();

    return useMutation<Restaurant, Error, Omit<Restaurant, 'id'>>({
        mutationFn: createRestaurant,
        onSuccess: (newRestaurant) => {
            queryClient.invalidateQueries({queryKey: ['restaurant', newRestaurant.ownerId]});
        },
    });
}

export function useEditRestaurant(restaurantId: string) {
    const queryClient = useQueryClient();

    return useMutation<Restaurant, Error, Partial<Restaurant>>({
        mutationFn: (data) => editRestaurant(restaurantId, data),
        onSuccess: (updatedRestaurant) => {
            queryClient.invalidateQueries({queryKey: ['restaurant', updatedRestaurant.ownerId]});
        },
    });
}

export function useRestaurantDetails(restaurantId: string) {
    const restaurantQuery = useQuery<Restaurant>({
        queryKey: ['restaurantDetails', restaurantId],
        queryFn: () => fetchRestaurantById(restaurantId),
        enabled: !!restaurantId
    });

    const dishesQuery = useQuery<Dish[]>({
        queryKey: ['restaurantDishes', restaurantId],
        queryFn: () => getDishesByRestaurant(restaurantId),
        enabled: !!restaurantId,
        refetchInterval: 5000
    });

    return {
        restaurant: restaurantQuery.data,
        dishes: dishesQuery.data ?? [],
        isLoading: restaurantQuery.isLoading || dishesQuery.isLoading,
        error: restaurantQuery.error || dishesQuery.error,
    };
}

export function useRestaurantGuesstimate(restaurantId: string) {
    return useQuery({
        queryKey: ['restaurantGuesstimate', restaurantId],
        queryFn: () => getRestaurantGuesstimate(restaurantId),
        enabled: !!restaurantId,
    });
}