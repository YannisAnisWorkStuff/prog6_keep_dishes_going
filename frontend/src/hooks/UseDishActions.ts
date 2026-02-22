import {useCallback} from 'react';
import {
    useAddDish,
    useApplyPendingDishes,
    useDeleteDish,
    useEditDish,
    useMarkBackInStock,
    useMarkOutOfStock,
    usePublishDish,
    useUnpublishDish
} from './UseDish';
import type {Dish} from "../model/Dish/Dish.ts";

export function useDishActions(restaurantId: string) {
    const publishMutation = usePublishDish(restaurantId);
    const unpublishMutation = useUnpublishDish(restaurantId);
    const outOfStockMutation = useMarkOutOfStock(restaurantId);
    const backInStockMutation = useMarkBackInStock(restaurantId);
    const addMutation = useAddDish(restaurantId);
    const editMutation = useEditDish(restaurantId);
    const applyPendingMutation = useApplyPendingDishes(restaurantId);
    const deleteMutation = useDeleteDish(restaurantId);


    const handleAddDish = useCallback(
        async (dish: Omit<Dish, 'id'>) => {
            try {
                await addMutation.mutateAsync(dish);
                alert('Dish added successfully!');
            } catch (err) {
                console.error('Error adding dish:', err);
                alert('Failed to add dish');
            }
        },
        [addMutation]
    );

    const handleEditDish = useCallback(
        async (dishId: string, dish: Omit<Dish, 'id'>) => {
            try {
                await editMutation.mutateAsync({dishId, dish});
                alert('Dish updated as draft!');
            } catch (err) {
                console.error('Error editing dish:', err);
                alert('Failed to edit dish');
            }
        },
        [editMutation]
    );

    const handlePublish = useCallback(
        async (dishId: string) => {
            try {
                await publishMutation.mutateAsync(dishId);
                alert('Dish published!');
            } catch (err) {
                console.error('Error publishing dish:', err);
                alert('Failed to publish dish');
            }
        },
        [publishMutation]
    );

    const handleUnpublish = useCallback(
        async (dishId: string) => {
            try {
                await unpublishMutation.mutateAsync(dishId);
                alert('Dish unpublished!');
            } catch (err) {
                console.error('Error unpublishing dish:', err);
                alert('Failed to unpublish dish');
            }
        },
        [unpublishMutation]
    );

    const handleOutOfStock = useCallback(
        async (dishId: string) => {
            try {
                await outOfStockMutation.mutateAsync(dishId);
                alert('Dish marked as out of stock!');
            } catch (err) {
                console.error('Error marking out of stock:', err);
                alert('Failed to mark dish as out of stock');
            }
        },
        [outOfStockMutation]
    );

    const handleBackInStock = useCallback(
        async (dishId: string) => {
            try {
                await backInStockMutation.mutateAsync(dishId);
                alert('Dish marked back in stock!');
            } catch (err) {
                console.error('Error marking back in stock:', err);
                alert('Failed to mark dish back in stock');
            }
        },
        [backInStockMutation]
    );

    const handleApplyPendingDishes = useCallback(async () => {
        try {
            await applyPendingMutation.mutateAsync();
            alert('All pending dish changes have been applied!');
        } catch (err) {
            console.error('Error applying pending dishes:', err);
            alert('Failed to apply pending dish changes.');
        }
    }, [applyPendingMutation]);

    const handleDeleteDish = useCallback(
        async (dishId: string) => {
            try {
                await deleteMutation.mutateAsync(dishId);
                alert('Dish deleted successfully!');
            } catch (err) {
                console.error('Error deleting dish:', err);
                alert('Failed to delete dish');
            }
        },
        [deleteMutation]
    );


    return {
        handlePublish,
        handleUnpublish,
        handleOutOfStock,
        handleBackInStock,
        handleAddDish,
        handleEditDish,
        handleApplyPendingDishes,
        handleDeleteDish
    };
}
