import type {Restaurant} from '../model/Restaurant';

export interface RestaurantFilterOptions {
    searchTerm?: string;
    cuisineFilter?: string[];
    openNow?: boolean;
    sortBy?: 'default' | 'fastest';
    priceRange?: [number, number];
    maxDistance?: number | null;
    maxDeliveryTime?: number | null;
}


export function extractCuisines(restaurants: Restaurant[]): string[] {
    return Array.from(
        new Set(
            restaurants
                .flatMap((r) =>
                    r.cuisineType
                        ? r.cuisineType
                            .split(',')
                            .map((c) => c.trim())
                            .filter(Boolean)
                        : []
                )
        )
    );
}

export function filterRestaurants(
    restaurants: Restaurant[],
    {
        searchTerm = '',
        cuisineFilter = [],
        openNow = false,
        sortBy = 'default',
        priceRange = [0, 100],
        maxDistance = null,
        maxDeliveryTime = null,
    }: RestaurantFilterOptions
): Restaurant[] {
    let filtered = [...restaurants];

    if (searchTerm.trim()) {
        const term = searchTerm.toLowerCase();
        filtered = filtered.filter(
            (r) =>
                r.name.toLowerCase().includes(term) ||
                r.address.city.toLowerCase().includes(term)
        );
    }

    if (cuisineFilter.length > 0) {
        filtered = filtered.filter((r) =>
            cuisineFilter.some((c) =>
                r.cuisineType.toLowerCase().includes(c.toLowerCase())
            )
        );
    }

    if (openNow) {
        const now = new Date();
        const currentDay = now.getDay(); // Sunday = 0
        const currentTime = now.toTimeString().slice(0, 5); // "HH:MM"
        filtered = filtered.filter((r) => {
            const schedule = r.schedules?.[currentDay];
            if (!schedule?.opentime || !schedule?.closetime) return false;
            return (
                schedule.opentime <= currentTime &&
                currentTime <= schedule.closetime
            );
        });
    }

    filtered = filtered.filter((r: any) => {
        const avgPrice = r.averagePrice ?? 20;
        return avgPrice >= priceRange[0] && avgPrice <= priceRange[1];
    });

    filtered = filtered.filter((r: any) => {
        if (maxDistance == null) return true;
        return r.distanceKm ? r.distanceKm <= maxDistance : true;
    });

    filtered = filtered.filter((r: any) => {
        if (maxDeliveryTime == null) return true;
        return r.guesstimatedDeliveryTime
            ? r.guesstimatedDeliveryTime <= maxDeliveryTime
            : true;
    });

    if (sortBy === 'fastest') {
        filtered.sort(
            (a, b) =>
                (a.preparationTimeMinutes ?? 999) - (b.preparationTimeMinutes ?? 999)
        );
    }


    return filtered;
}
