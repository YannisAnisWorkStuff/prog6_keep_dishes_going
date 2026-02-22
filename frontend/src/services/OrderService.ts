import axios from "axios";

const BASE_URL = `${import.meta.env.VITE_BACKEND_URL}/api/orders`;

const OWNER_ORDERS_URL = `${import.meta.env.VITE_BACKEND_URL}/api/owners/orders`;

export async function createOrder(request: {
    customerId: string;
    restaurantId: string;
    customerName: string;
    deliveryAddress: {
        street: string;
        number: string;
        postalCode: string;
        city: string;
        country: string;
    };
}) {
    const response = await axios.post(BASE_URL, request);
    return response.data;
}

export async function getOrder(orderId: string) {
    const response = await axios.get(`${BASE_URL}/${orderId}`);
    return response.data;
}


export async function getPendingOrders(restaurantId: string) {
    const response = await axios.get(`${OWNER_ORDERS_URL}/${restaurantId}`);
    return response.data;
}

export async function getAcceptedOrders(restaurantId: string) {
    const response = await axios.get(`${OWNER_ORDERS_URL}/${restaurantId}/accepted`);
    return response.data;
}

export async function acceptOrder(restaurantId: string, orderId: string) {
    await axios.post(`${OWNER_ORDERS_URL}/${restaurantId}/${orderId}/accept`);
}

export async function rejectOrder(restaurantId: string, orderId: string, reason = "Owner rejected your order"
) {
    await axios.post(`${OWNER_ORDERS_URL}/${restaurantId}/${orderId}/reject`, {reason});
}


export async function markOrderReady(restaurantId: string, orderId: string) {
    await axios.post(`${OWNER_ORDERS_URL}/${restaurantId}/${orderId}/ready`);
}

export async function createGuestOrder(orderRequest: any) {
    const {data} = await axios.post(`${BASE_URL}/guest`, orderRequest);
    return data;
}