import type {Address} from "./Address.ts";
import type {DaySchedule} from "./DaySchedule.ts";

export interface Restaurant {
    id?: string;
    ownerId: string;
    name: string;
    address: Address;
    cuisineType: string;
    email: string;
    pictures: string[];
    preparationTimeMinutes: number;
    schedules: DaySchedule[];
}