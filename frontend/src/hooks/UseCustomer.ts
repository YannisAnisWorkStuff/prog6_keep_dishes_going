import {useMutation, useQueryClient} from '@tanstack/react-query';
import {getCurrentCustomer, registerCustomer} from '../services/CustomerService';
import type {Customer} from '../model/Customer';
import {useEffect, useState} from "react";

export function useRegisterCustomer() {
    const queryClient = useQueryClient();

    return useMutation<Customer, Error, Customer>({
        mutationFn: registerCustomer,
        onSuccess: () => {
            queryClient.invalidateQueries({queryKey: ['customers']});
        },
    });
}

export function useCurrentCustomer() {
    const [customer, setCustomer] = useState<Customer | null>(null);
    const [error, setError] = useState<Error | null>(null);
    const [isLoading, setIsLoading] = useState<boolean>(true);

    useEffect(() => {
        (async () => {
            try {
                const customerData = await getCurrentCustomer();
                setCustomer(customerData);
            } catch (err) {
                setCustomer(null);
                setError(null);
            } finally {
                setIsLoading(false);
            }
        })();
    }, []);

    return {customer, isLoading, error};
}
