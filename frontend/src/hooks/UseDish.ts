import {useMutation, useQuery, useQueryClient} from '@tanstack/react-query';
import type {Dish} from '../model/Dish/Dish';
import {
    addDish,
    applyPendingDishes,
    deleteDish,
    editDishAsDraft,
    getDishesByRestaurant,
    markDishBackInStock,
    markDishOutOfStock,
    publishDish,
    unpublishDish
} from '../services/DishService';

export function useDishes(restaurantId: string) {
    return useQuery<Dish[], Error>({
        queryKey: ['dishes', restaurantId],
        queryFn: () => getDishesByRestaurant(restaurantId),
        enabled: !!restaurantId,
        refetchOnMount: 'always',
        refetchInterval: 5000
    });
}

export function useAddDish(restaurantId: string) {
    const queryClient = useQueryClient();
    return useMutation({
        mutationFn: (dish: Omit<Dish, 'id'>) => addDish(restaurantId, dish),
        onSuccess: () => queryClient.invalidateQueries({queryKey: ['dishes', restaurantId]}),
    });
}

export function useEditDish(restaurantId: string) {
    const queryClient = useQueryClient();
    return useMutation({
        mutationFn: ({dishId, dish}: { dishId: string, dish: Omit<Dish, 'id'> }) =>
            editDishAsDraft(restaurantId, dishId, dish),
        onSuccess: () => queryClient.invalidateQueries({queryKey: ['dishes', restaurantId]}),
    });
}

export function usePublishDish(restaurantId: string) {
    const queryClient = useQueryClient();
    return useMutation({
        mutationFn: (dishId: string) => publishDish(restaurantId, dishId),
        onSuccess: () => queryClient.invalidateQueries({queryKey: ['dishes', restaurantId]}),
    });
}

export function useUnpublishDish(restaurantId: string) {
    const queryClient = useQueryClient();
    return useMutation({
        mutationFn: (dishId: string) => unpublishDish(restaurantId, dishId),
        onSuccess: () => queryClient.invalidateQueries({queryKey: ['dishes', restaurantId]}),
    });
}

export function useMarkOutOfStock(restaurantId: string) {
    const queryClient = useQueryClient();
    return useMutation({
        mutationFn: (dishId: string) => markDishOutOfStock(restaurantId, dishId),
        onSuccess: () => queryClient.invalidateQueries({queryKey: ['dishes', restaurantId]}),
    });
}

export function useMarkBackInStock(restaurantId: string) {
    const queryClient = useQueryClient();
    return useMutation({
        mutationFn: (dishId: string) => markDishBackInStock(restaurantId, dishId),
        onSuccess: () => queryClient.invalidateQueries({queryKey: ['dishes', restaurantId]}),
    });
}

export function useApplyPendingDishes(restaurantId: string) {
    const queryClient = useQueryClient();
    return useMutation({
        mutationFn: () => applyPendingDishes(restaurantId),
        onSuccess: () => queryClient.invalidateQueries({queryKey: ['dishes', restaurantId]}),
    });
}

export function useDeleteDish(restaurantId: string) {
    const queryClient = useQueryClient();
    return useMutation({
        mutationFn: (dishId: string) => deleteDish(restaurantId, dishId),
        onSuccess: () => queryClient.invalidateQueries({queryKey: ['dishes', restaurantId]}),
    });
}