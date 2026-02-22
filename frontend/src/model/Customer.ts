import type {Address} from './Address.ts';

export interface Customer {
    id?: string;
    name: string;
    email: string;
    password?: string;
    address: Address;
}