import {useEffect, useRef, useState} from "react";
import {Card, CardContent, CardMedia, Typography} from "@mui/material";
import type {Restaurant} from "../../model/Restaurant.ts";
import {RestaurantDiscoveryStyles} from "../../pages/Restaurant/RestaurantDiscoveryStyles.ts";

export default function RestaurantCard({restaurant, onClick}: { restaurant: Restaurant; onClick?: () => void }) {
    const [currentIndex, setCurrentIndex] = useState(0);
    const timerRef = useRef<number | null>(null);

    function handleMouseEnter() {
        if (restaurant.pictures.length > 1) {
            timerRef.current = window.setInterval(() => {
                setCurrentIndex((prev) => (prev + 1) % restaurant.pictures.length);
            }, 3000);
        }
    }

    function handleMouseLeave() {
        if (timerRef.current !== null) {
            window.clearInterval(timerRef.current);
            timerRef.current = null;
        }
        setCurrentIndex(0);
    }

    useEffect(() => {
        return () => {
            if (timerRef.current !== null) {
                window.clearInterval(timerRef.current);
            }
        };
    }, []);

    return (
        <Card
            sx={RestaurantDiscoveryStyles.card}
            onClick={onClick}
            onMouseEnter={handleMouseEnter}
            onMouseLeave={handleMouseLeave}
            style={{cursor: "pointer"}}
        >
            <CardMedia
                component="img"
                height="160"
                image={restaurant.pictures[currentIndex]?.trim() || "https://placehold.co/400x200?text=No+Image"}
                alt={restaurant.name}
                onError={(e) => {
                    e.currentTarget.onerror = null;
                    e.currentTarget.src = "https://placehold.co/400x200?text=No+Image";
                }}
                sx={{objectFit: "cover"}}
            />
            <CardContent>
                <Typography variant="h6" fontWeight={600}>
                    {restaurant.name}
                </Typography>
                <Typography color="text.secondary" noWrap>
                    {restaurant.cuisineType}
                </Typography>
                <Typography variant="body2" color="text.secondary">
                    {restaurant.address.city}, {restaurant.address.country}
                </Typography>
                <Typography variant="body2" color="text.secondary">
                    Prep: {restaurant.preparationTimeMinutes} min
                </Typography>
            </CardContent>
        </Card>
    );
}