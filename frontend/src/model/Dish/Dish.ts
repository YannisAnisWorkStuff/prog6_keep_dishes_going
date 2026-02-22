import type {DishType} from "./DishType.ts";
import type {DishState} from "./DishState.ts";

export interface Dish {
    id?: string;
    restaurantId?: string;
    name: string;
    dishType: DishType;
    description: string;
    price: number;
    pictureUrl: string;
    foodTags: string[];
    state?: DishState;
}