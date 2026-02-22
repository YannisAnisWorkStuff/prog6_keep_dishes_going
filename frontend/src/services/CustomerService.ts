import axios from 'axios';
import type {Customer} from '../model/Customer';


const BASE_URL = `${import.meta.env.VITE_BACKEND_URL}/api/customers`;

export async function registerCustomer(customer: Customer): Promise<Customer> {
    const {data} = await axios.post(BASE_URL, customer);
    return data;
}

export async function getCurrentCustomer(): Promise<Customer> {
    const {data} = await axios.get(BASE_URL + "/me", {withCredentials: true});
    return data;
}
