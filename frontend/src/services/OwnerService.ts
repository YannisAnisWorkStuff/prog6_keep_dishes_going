import axios from 'axios';
import type {Owner} from '../model/Owner';

const BASE_URL = `${import.meta.env.VITE_BACKEND_URL}/api/owners`;

export async function RegisterOwner(owner: Owner): Promise<Owner> {
    const {data} = await axios.post(BASE_URL, owner);
    return data;
}

export async function getCurrentOwner(): Promise<Owner> {
    const {data} = await axios.get(BASE_URL + "/me");
    return data;
}
