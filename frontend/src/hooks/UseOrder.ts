import {useMutation, useQuery, useQueryClient} from "@tanstack/react-query";
import {acceptOrder, getAcceptedOrders, getPendingOrders, markOrderReady, rejectOrder,} from "../services/OrderService";

export function usePendingOrders(restaurantId: string) {
    return useQuery({
        queryKey: ["orders", restaurantId, "pending"],
        queryFn: () => getPendingOrders(restaurantId),
        enabled: !!restaurantId,
        refetchOnMount: "always",
        refetchInterval: 8000,
    });
}

export function useAcceptedOrders(restaurantId: string) {
    return useQuery({
        queryKey: ["orders", restaurantId, "accepted"],
        queryFn: () => getAcceptedOrders(restaurantId),
        enabled: !!restaurantId,
        refetchOnMount: "always",
        refetchInterval: 8000,
    });
}

export function useAcceptOrder(restaurantId: string) {
    const queryClient = useQueryClient();
    return useMutation({
        mutationFn: (orderId: string) => acceptOrder(restaurantId, orderId),
        onSuccess: () =>
            queryClient.invalidateQueries({queryKey: ["orders", restaurantId]}),
    });
}

export function useRejectOrder(restaurantId: string) {
    const queryClient = useQueryClient();
    return useMutation({
        mutationFn: ({orderId, reason,}: {
            orderId: string;
            reason: string;
        }) => rejectOrder(restaurantId, orderId, reason),
        onSuccess: () =>
            queryClient.invalidateQueries({queryKey: ["orders", restaurantId]}),
    });
}

export function useMarkOrderReady(restaurantId: string) {
    const queryClient = useQueryClient();
    return useMutation({
        mutationFn: (orderId: string) => markOrderReady(restaurantId, orderId),
        onSuccess: () =>
            queryClient.invalidateQueries({queryKey: ["orders", restaurantId]}),
    });
}