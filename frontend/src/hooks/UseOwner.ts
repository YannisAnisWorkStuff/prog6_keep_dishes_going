import {useMutation} from '@tanstack/react-query';
import type {Owner} from '../model/Owner';
import {getCurrentOwner, RegisterOwner} from "../services/OwnerService.ts";
import {useEffect, useState} from "react";

export function useRegisterOwner() {
    return useMutation<Owner, Error, Owner>({
        mutationFn: RegisterOwner,
    });
}

export function useCurrentOwner() {
    const [owner, setOwner] = useState<Owner | null>(null);
    const [error, setError] = useState<Error | null>(null);
    const [isLoading, setIsLoading] = useState<boolean>(true);

    useEffect(() => {
        (async () => {
            try {
                const ownerData = await getCurrentOwner();
                setOwner(ownerData);
            } catch (err) {
                setError(err instanceof Error ? err : new Error('Unknown error'));
                console.error('Error loading owner:', err);
            } finally {
                setIsLoading(false);
            }
        })();
    }, []);

    return {owner, isLoading, error};
}
