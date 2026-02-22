import {useEffect, useState} from "react";
import {useNavigate, useParams} from "react-router-dom";
import {getOrder} from "../services/OrderService";

export function useOrderConfirmation() {
    const {orderId} = useParams<{ orderId: string }>();
    const [order, setOrder] = useState<any>(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);
    const navigate = useNavigate();

    useEffect(() => {
        if (!orderId) return;
        let interval: number;

        const fetchOrder = async () => {
            try {
                const res = await getOrder(orderId);
                setOrder(res);
                setLoading(false);
            } catch (err) {
                console.error(err);
                setError("Failed to fetch order status.");
                setLoading(false);
            }
        };

        fetchOrder();
        interval = window.setInterval(fetchOrder, 5000);

        return () => clearInterval(interval);
    }, [orderId]);

    const getStatusIconProps = () => {
        switch (order?.status) {
            case "ACCEPTED":
                return {color: "success", label: "Accepted"};
            case "REJECTED":
                return {color: "error", label: "Rejected"};
            default:
                return {color: "warning", label: "Pending"};
        }
    };

    const getStatusMessage = () => {
        switch (order?.status) {
            case "ACCEPTED":
                return "Your order has been accepted by the restaurant!";
            case "REJECTED":
                return "Unfortunately, your order was rejected by the restaurant.";
            default:
                return "We received your order — waiting for the restaurant to confirm...";
        }
    };

    return {
        order,
        loading,
        error,
        navigate,
        getStatusIconProps,
        getStatusMessage,
    };
}