import {useCallback} from 'react';
import {useCreateRestaurant, useEditRestaurant} from './UseRestaurants';
import type {Restaurant} from '../model/Restaurant';

export function useRestaurantActions(restaurantId?: string) {
    const createMutation = useCreateRestaurant();
    const editMutation = useEditRestaurant(restaurantId ?? '');

    const handleCreateRestaurant = useCallback(
        async (restaurant: Omit<Restaurant, 'id'>) => {
            try {
                const result = await createMutation.mutateAsync(restaurant);
                alert('Restaurant created successfully!');
                return result;
            } catch (err) {
                console.error('Error creating restaurant:', err);
                alert('Failed to create restaurant');
                throw err;
            }
        },
        [createMutation]
    );

    const handleEditRestaurant = useCallback(
        async (data: Partial<Restaurant>) => {
            try {
                await editMutation.mutateAsync(data);
                alert('Restaurant updated successfully!');
            } catch (err) {
                console.error('Error updating restaurant:', err);
                alert('Failed to update restaurant');
                throw err;
            }
        },
        [editMutation]
    );

    return {
        handleCreateRestaurant,
        handleEditRestaurant,
        isCreating: createMutation.isPending,
        isEditing: editMutation.isPending,
    };
}