import {useMutation, useQuery, useQueryClient} from "@tanstack/react-query";
import {
    addDishToBasket,
    clearBasket,
    getBasket,
    removeDishFromBasket,
    updateBasketQuantity
} from "../services/BasketService";
import type {Basket} from "../model/Basket";
import {useEffect, useRef, useState} from "react";

export function useBasket(customerId?: string) {
    const [hasChanged, setHasChanged] = useState(false);
    const [changeType, setChangeType] = useState<"self" | "external" | null>(null);
    const previousBasket = useRef<Basket | null>(null);
    const lastUserChange = useRef<number | null>(null);

    const query = useQuery<Basket>({
        queryKey: ["basket", customerId || "guest"],
        queryFn: async () => {
            if (!customerId) {
                const local = localStorage.getItem("guest_basket");
                return local ? JSON.parse(local) : {items: []};
            }
            return getBasket(customerId);
        },
        enabled: true,
        refetchInterval: customerId ? 5000 : false, // 🔁 poll only for logged-in
    });

    useEffect(() => {
        if (!query.data) return;
        if (!previousBasket.current) {
            previousBasket.current = query.data;
            return;
        }

        const prev = previousBasket.current;
        const curr = query.data;

        const changed =
            prev.items.length !== curr.items.length ||
            prev.items.some(
                (p, i) =>
                    !curr.items[i] ||
                    p.dishId !== curr.items[i].dishId ||
                    p.quantity !== curr.items[i].quantity ||
                    p.price !== curr.items[i].price
            );

        if (changed) {
            const now = Date.now();
            const diff = lastUserChange.current ? now - lastUserChange.current : Infinity;
            setChangeType(diff < 3000 ? "self" : "external");
            setHasChanged(true);
        }

        previousBasket.current = curr;
    }, [query.data]);

    function markUserChange() {
        lastUserChange.current = Date.now();
    }

    useEffect(() => {
        if (!customerId) {
            const sync = () => query.refetch();
            window.addEventListener("storage", sync);
            return () => window.removeEventListener("storage", sync);
        }
    }, [customerId, query]);

    return {
        ...query,
        hasChanged,
        setHasChanged,
        changeType,
        markUserChange,
    };
}


export function useAddToBasket() {
    const queryClient = useQueryClient();

    return useMutation({
        mutationFn: addDishToBasket,
        onSuccess: (_, variables) => {
            queryClient.invalidateQueries({queryKey: ["basket", variables.customerId]});
        },
    });
}

export function useRemoveFromBasket() {
    const queryClient = useQueryClient();
    return useMutation({
        mutationFn: removeDishFromBasket,
        onSuccess: (_, variables) => {
            queryClient.invalidateQueries({queryKey: ["basket", variables.customerId]});
        },
    });
}

export function useUpdateBasketQuantity() {
    const queryClient = useQueryClient();
    return useMutation({
        mutationFn: updateBasketQuantity,
        onSuccess: (_, variables) => {
            queryClient.invalidateQueries({queryKey: ["basket", variables.customerId]});
        },
    });
}

export function useClearBasket() {
    const queryClient = useQueryClient();

    return useMutation({
        mutationFn: async (customerId?: string) => {
            if (customerId) {
                await clearBasket(customerId);
            } else {
                localStorage.removeItem("guest_basket");
            }
        },
        onSuccess: (_, customerId) => {
            queryClient.setQueryData(["basket", customerId || "guest"], {items: []});
        },
    });
}