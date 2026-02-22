import {
    Alert,
    AppBar,
    Box,
    Button,
    Card,
    CardContent,
    CardMedia,
    Chip,
    CircularProgress,
    Container,
    FormControl,
    MenuItem,
    Select,
    Snackbar,
    Stack,
    Toolbar,
    Typography,
} from "@mui/material";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import StorefrontIcon from "@mui/icons-material/Storefront";
import AccessTimeIcon from "@mui/icons-material/AccessTime";
import RoomIcon from "@mui/icons-material/Room";
import DeleteIcon from "@mui/icons-material/Delete";
import {useNavigate, useParams} from "react-router-dom";
import {useState} from "react";
import {RestaurantDetailsStyles} from "./RestaurantDetailsStyles";
import {useRestaurantDetails, useRestaurantGuesstimate} from "../../hooks/UseRestaurants";
import {useCurrentCustomer} from "../../hooks/UseCustomer";
import {DishModal} from "../../components/DishModal";
import {useBasket, useClearBasket} from "../../hooks/UseBasket"; // ✅ import this
import {useBasketActions} from "../../hooks/useBasketActions";
import {BasketPanel} from "../../components/BasketPanel";

export function RestaurantDetailsPage() {
    const {restaurantId} = useParams<{ restaurantId: string }>();
    const navigate = useNavigate();
    const {restaurant, dishes, isLoading, error} = useRestaurantDetails(restaurantId ?? "");
    const {customer, isLoading: customerLoading, error: customerError} = useCurrentCustomer();
    const [selectedDish, setSelectedDish] = useState<any>(null);
    const [modalOpen, setModalOpen] = useState(false);
    const {data: guesstimate, isLoading: guesstimateLoading} = useRestaurantGuesstimate(restaurantId ?? "");

    const [sortBy, setSortBy] = useState<'default' | 'price-asc' | 'price-desc' | 'name-asc' | 'name-desc'>('default');


    const customerId = customer?.id;
    const {
        data: basket,
        isLoading: basketLoading,
        hasChanged,
        setHasChanged,
        changeType,
        markUserChange,
    } = useBasket(customerId);

    const {handleAddToBasket, handleRemoveDish, handleUpdateQuantity} =
        useBasketActions(customerId, markUserChange);

    const {mutateAsync: clearBasket} = useClearBasket();

    const total =
        basket?.items?.reduce(
            (sum: number, item: any) => sum + item.price * item.quantity,
            0
        ) ?? 0;

    if (isLoading || customerLoading || basketLoading) {
        return (
            <Container sx={{py: 6, textAlign: "center"}}>
                <CircularProgress/>
            </Container>
        );
    }

    if (error || customerError || !restaurant) {
        return (
            <Container sx={{py: 6}}>
                <Typography color="error">
                    {error
                        ? "Failed to load restaurant details."
                        : customerError
                            ? "Failed to load customer info."
                            : "Something went wrong."}
                </Typography>
            </Container>
        );
    }

    return (
        <Box
            sx={{
                ...RestaurantDetailsStyles.root,
                display: "flex",
                flexDirection: {xs: "column", lg: "row"},
            }}
        >
            <Box sx={{flex: 3}}>
                <AppBar position="static" color="transparent" elevation={0} sx={RestaurantDetailsStyles.appBar}>
                    <Toolbar>
                        <StorefrontIcon sx={RestaurantDetailsStyles.logoIcon}/>
                        <Typography variant="h6" sx={RestaurantDetailsStyles.logoText}>
                            Keep Dishes Going
                        </Typography>
                        <Button
                            startIcon={<ArrowBackIcon/>}
                            onClick={() => navigate(-1)}
                            sx={RestaurantDetailsStyles.backButton}
                        >
                            Back
                        </Button>
                    </Toolbar>
                </AppBar>

                <Box sx={RestaurantDetailsStyles.heroSection}>
                    <CardMedia
                        component="img"
                        height="260"
                        image={restaurant.pictures?.[0] || "https://placehold.co/800x260?text=No+Image"}
                        alt={restaurant.name}
                        sx={RestaurantDetailsStyles.heroImage}
                    />
                    <Container maxWidth="md">
                        <Box sx={RestaurantDetailsStyles.heroContent}>
                            <Typography variant="h3" fontWeight={700}>
                                {restaurant.name}
                            </Typography>
                            <Typography variant="h6" color="text.secondary" gutterBottom>
                                {restaurant.cuisineType}
                            </Typography>
                            <Stack direction="row" spacing={1} justifyContent="center" sx={{mb: 1}}>
                                <Chip
                                    icon={<RoomIcon/>}
                                    label={`${restaurant.address.city}, ${restaurant.address.country}`}
                                />
                                <Chip
                                    icon={<AccessTimeIcon/>}
                                    label={`Prep: ${restaurant.preparationTimeMinutes} min`}
                                />
                            </Stack>
                            {!guesstimateLoading && (
                                <Chip
                                    color="success"
                                    icon={<AccessTimeIcon/>}
                                    label={`Guesstimated Delivery: ${guesstimate?.toFixed(0)} min`}
                                />
                            )}

                        </Box>
                    </Container>
                </Box>

                <Container sx={{py: 6}}>
                    <Stack
                        direction={{xs: "column", sm: "row"}}
                        justifyContent="space-between"
                        alignItems="center"
                        spacing={2}
                        sx={{mb: 2}}
                    >
                        <Typography variant="h4" fontWeight={700}>
                            Menu
                        </Typography>

                        <FormControl size="small" sx={{minWidth: 180}}>
                            <Select
                                value={sortBy}
                                onChange={(e) => setSortBy(e.target.value as typeof sortBy)}
                                displayEmpty
                            >
                                <MenuItem value="default">Default</MenuItem>
                                <MenuItem value="price-asc">Price: Low to High</MenuItem>
                                <MenuItem value="price-desc">Price: High to Low</MenuItem>
                                <MenuItem value="name-asc">Name: A–Z</MenuItem>
                                <MenuItem value="name-desc">Name: Z–A</MenuItem>
                            </Select>
                        </FormControl>

                        {basket?.items?.length > 0 && (
                            <Button
                                variant="outlined"
                                color="error"
                                size="small"
                                startIcon={<DeleteIcon/>}
                                onClick={() => clearBasket(customerId)}
                            >
                                Clear Basket
                            </Button>
                        )}
                    </Stack>

                    {dishes.length === 0 ? (
                        <Typography color="text.secondary">No dishes available.</Typography>
                    ) : (
                        <Box
                            sx={{
                                display: "grid",
                                gridTemplateColumns: {
                                    xs: "1fr",
                                    sm: "repeat(2, 1fr)",
                                    md: "repeat(3, 1fr)",
                                },
                                gap: 3,
                                mt: 2,
                            }}
                        >
                            {dishes
                                .filter((dish) => dish.state === "PUBLISHED")
                                .sort((a, b) => {
                                    switch (sortBy) {
                                        case "price-asc":
                                            return a.price - b.price;
                                        case "price-desc":
                                            return b.price - a.price;
                                        case "name-asc":
                                            return a.name.localeCompare(b.name);
                                        case "name-desc":
                                            return b.name.localeCompare(a.name);
                                        default:
                                            return 0;
                                    }
                                })
                                .map((dish) => (
                                    <Card
                                        key={dish.id}
                                        sx={{
                                            ...RestaurantDetailsStyles.card,
                                            cursor: "pointer",
                                        }}
                                        onClick={() => {
                                            setSelectedDish(dish);
                                            setModalOpen(true);
                                        }}
                                    >
                                        <CardMedia
                                            component="img"
                                            height="160"
                                            image={
                                                dish.pictureUrl?.trim() ||
                                                "https://placehold.co/300x160?text=No+Image"
                                            }
                                            alt={dish.name}
                                        />
                                        <CardContent>
                                            <Typography variant="h6" fontWeight={600} noWrap>
                                                {dish.name}
                                            </Typography>
                                            <Typography
                                                variant="body2"
                                                color="text.secondary"
                                                noWrap
                                            >
                                                {dish.description}
                                            </Typography>
                                            <Typography variant="body1" fontWeight={600}>
                                                €{dish.price.toFixed(2)}
                                            </Typography>
                                        </CardContent>
                                    </Card>
                                ))}
                        </Box>
                    )}
                </Container>
            </Box>

            {!customerLoading && (
                <BasketPanel
                    basket={basket}
                    customerId={customer?.id}  // This will be properly defined or undefined
                    onRemoveDish={handleRemoveDish}
                    onUpdateQuantity={handleUpdateQuantity}
                    total={total}
                    navigate={navigate}
                />
            )}

            <DishModal
                open={modalOpen}
                onClose={() => setModalOpen(false)}
                dish={selectedDish}
                onAddToBasket={(qty) =>
                    handleAddToBasket(restaurant.id, selectedDish, qty)
                }

            />

            <Snackbar
                open={hasChanged}
                autoHideDuration={3000}
                onClose={() => setHasChanged(false)}
                anchorOrigin={{vertical: "bottom", horizontal: "center"}}
            >
                <Alert
                    onClose={() => setHasChanged(false)}
                    severity={changeType === "external" ? "warning" : "info"}
                    sx={{width: "100%"}}
                >
                    {changeType === "external"
                        ? "Your basket has changed (some dishes were updated or removed)."
                        : "Your basket has been updated!"}
                </Alert>
            </Snackbar>
        </Box>
    );
}
