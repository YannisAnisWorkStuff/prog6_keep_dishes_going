import {useNavigate, useParams} from "react-router-dom";
import {useState} from "react";
import {useAcceptedOrders, useAcceptOrder, useMarkOrderReady, usePendingOrders, useRejectOrder,} from "./UseOrder";

export function useManageOrders() {
    const {restaurantId} = useParams<{ restaurantId: string }>();
    const navigate = useNavigate();

    const [activeTab, setActiveTab] = useState<"pending" | "accepted">("pending");
    const [error, setError] = useState<string | null>(null);
    const [success, setSuccess] = useState<string | null>(null);

    const {
        data: pendingOrders = [],
        isLoading: pendingLoading,
        isError: pendingError,
    } = usePendingOrders(restaurantId!);

    const {
        data: acceptedOrders = [],
        isLoading: acceptedLoading,
        isError: acceptedError,
    } = useAcceptedOrders(restaurantId!);

    const loading = pendingLoading || acceptedLoading;

    const acceptMutation = useAcceptOrder(restaurantId!);
    const rejectMutation = useRejectOrder(restaurantId!);
    const markReadyMutation = useMarkOrderReady(restaurantId!);

    const handleAccept = async (orderId: string) => {
        try {
            await acceptMutation.mutateAsync(orderId);
            setSuccess("Order accepted!");
        } catch {
            setError("Failed to accept order.");
        }
    };

    const handleReject = async (orderId: string, reason: string) => {
        try {
            await rejectMutation.mutateAsync({orderId, reason});
            setSuccess("Order rejected.");
        } catch {
            setError("Failed to reject order.");
        }
    };

    const handleMarkReady = async (orderId: string) => {
        try {
            await markReadyMutation.mutateAsync(orderId);
            setSuccess("Order marked as ready!");
        } catch {
            setError("Failed to mark order as ready.");
        }
    };

    return {
        pendingOrders,
        acceptedOrders,
        activeTab,
        setActiveTab,
        loading,
        error,
        success,
        setError,
        setSuccess,
        handleAccept,
        handleReject,
        handleMarkReady,
        navigate,
    };
}