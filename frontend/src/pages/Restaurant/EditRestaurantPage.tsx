import {useNavigate, useParams} from 'react-router-dom';
import {Alert, AppBar, Box, Button, CircularProgress, Container, Toolbar, Typography,} from '@mui/material';
import {RestaurantForm, type RestaurantFormData} from '../../components/restaurant/RestaurantForm.tsx';
import {useRestaurant} from '../../hooks/UseRestaurants.ts';
import {useRestaurantActions} from '../../hooks/UseRestaurantsAction.ts';
import {useCurrentOwner} from "../../hooks/UseOwner.ts";
import StorefrontIcon from "@mui/icons-material/Storefront";
import {RestaurantEditStyles} from "./RestaurantEditStyles.ts";


export function EditRestaurantPage() {
    const {restaurantId} = useParams<{ restaurantId: string }>();
    const navigate = useNavigate();

    const {owner, isLoading: ownerLoading, error: ownerError} = useCurrentOwner();
    const ownerId = owner?.id ?? null;

    const {data: restaurant, isLoading: restaurantLoading, error: restaurantError} = useRestaurant(ownerId ?? '');
    const {handleEditRestaurant} = useRestaurantActions(restaurantId);

    const handleSubmit = async (data: RestaurantFormData) => {
        if (!restaurantId) return;

        await handleEditRestaurant({
            name: data.name,
            address: {
                street: data.street,
                number: data.number,
                postalCode: data.postalCode,
                city: data.city,
                country: data.country,
            },
            cuisineType: data.cuisineType,
            email: data.email,
            pictures: data.pictures,
            preparationTimeMinutes: data.preparationTimeMinutes,
            schedules: data.schedules,
        });

        navigate('/owner/dashboard');
    };

    if (ownerLoading || restaurantLoading || !ownerId) {
        return (
            <Container sx={{py: 6, textAlign: 'center'}}>
                <CircularProgress/>
            </Container>
        );
    }

    if (ownerError || restaurantError || !restaurant) {
        return (
            <Container sx={{py: 6}}>
                <Alert severity="error">Failed to load restaurant details.</Alert>
            </Container>
        );
    }

    const defaultValues: RestaurantFormData = {
        name: restaurant.name,
        street: restaurant.address.street,
        number: restaurant.address.number,
        postalCode: restaurant.address.postalCode,
        city: restaurant.address.city,
        country: restaurant.address.country,
        cuisineType: restaurant.cuisineType,
        email: restaurant.email,
        pictures: restaurant.pictures ?? [],
        preparationTimeMinutes: restaurant.preparationTimeMinutes,
        schedules: restaurant.schedules,
    };

    return (
        <Box sx={RestaurantEditStyles.root}>
            <AppBar position="static" color="transparent" elevation={0} sx={RestaurantEditStyles.appBar}>
                <Toolbar>
                    <StorefrontIcon sx={RestaurantEditStyles.logoIcon}/>
                    <Typography variant="h6" sx={RestaurantEditStyles.logoText}>
                        Keep Dishes Going
                    </Typography>
                    <Button
                        variant="outlined"
                        onClick={() => navigate('/owner/dashboard')}
                        sx={RestaurantEditStyles.ownerButton}
                    >
                        Back to Dashboard
                    </Button>
                </Toolbar>
            </AppBar>
            <Box sx={RestaurantEditStyles.heroSection}>
                <Container maxWidth="md">
                    <Box sx={RestaurantEditStyles.heroContent}>
                        <Typography variant="h3" fontWeight={700} gutterBottom>
                            Edit Your Restaurant
                        </Typography>
                        <Typography variant="h6" sx={{opacity: 0.9, mb: 4}}>
                            Update your restaurant’s information and schedule below.
                        </Typography>

                        <RestaurantForm
                            submitting={false}
                            onSubmit={handleSubmit}
                            onCancel={() => navigate(-1)}
                            defaultValues={defaultValues}
                        />
                    </Box>
                </Container>
            </Box>

            <Box sx={RestaurantEditStyles.footer}>
                <Typography variant="body2" color="text.secondary">
                    © 2025 Keep Dishes Going - Yiannis Ftiti
                </Typography>
            </Box>
        </Box>
    );
}