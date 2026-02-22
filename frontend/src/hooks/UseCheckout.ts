import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import {useBasket} from "./UseBasket.ts";
import {useBasketActions} from "./useBasketActions.tsx";
import {useCurrentCustomer} from "./UseCustomer.ts";
import {createGuestOrder, createOrder} from "../services/OrderService.ts";

export function useCheckout(customerId?: string) {
    const navigate = useNavigate();
    const {customer, isLoading: customerLoading} = useCurrentCustomer();
    const {data: basket, isLoading: basketLoading} = useBasket(customerId);
    const {clearBasket} = useBasketActions(customerId);

    const [name, setName] = useState("");
    const [paymentMethod, setPaymentMethod] = useState("card");
    const [address, setAddress] = useState({
        street: "",
        number: "",
        postalCode: "",
        city: "",
        country: "",
    });
    const [loading, setLoading] = useState(false);
    const [success, setSuccess] = useState(false);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        if (customer) {
            setName(customer.name ?? "");
            if (customer.address) {
                setAddress({
                    street: customer.address.street ?? "",
                    number: customer.address.number ?? "",
                    postalCode: customer.address.postalCode ?? "",
                    city: customer.address.city ?? "",
                    country: customer.address.country ?? "",
                });
            }
        }
    }, [customer]);

    async function handleCheckout() {
        try {
            setLoading(true);
            setError(null);

            let finalBasket = basket;
            if (!customerId) {
                finalBasket = JSON.parse(localStorage.getItem("guestBasket") || '{"items": []}');
            }
            if (!finalBasket || !finalBasket.items?.length) {
                setError("Your basket is empty.");
                return;
            }

            if (customerId) {
                const orderRequest = {
                    customerId: customer?.id!,
                    customerName: name,
                    restaurantId: finalBasket.restaurantId,
                    deliveryAddress: {...address},
                };

                const order = await createOrder(orderRequest);
                await clearBasket();
                setSuccess(true);
                setTimeout(() => navigate(`/confirmation/${order.id}`), 1000);
            } else {
                const orderRequest = {
                    customerName: name,
                    restaurantId: finalBasket.restaurantId,
                    deliveryAddress: {...address},
                    items: finalBasket.items,
                };

                const order = await createGuestOrder(orderRequest);
                localStorage.removeItem("guestBasket");
                setSuccess(true);
                setTimeout(() => navigate(`/confirmation/${order.id}`), 1000);
            }
        } catch (err) {
            console.error(err);
            setError("Failed to place order. Please try again.");
        } finally {
            setLoading(false);
        }
    }

    const total =
        basket?.items?.reduce(
            (sum: number, item: any) => sum + item.price * item.quantity,
            0
        ) ?? 0;

    return {
        customer,
        basket,
        customerLoading,
        basketLoading,
        name,
        setName,
        paymentMethod,
        setPaymentMethod,
        address,
        setAddress,
        total,
        loading,
        success,
        error,
        setError,
        setSuccess,
        handleCheckout,
    };
}
