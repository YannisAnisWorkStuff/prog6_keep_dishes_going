import {useNavigate} from 'react-router-dom';
import {AppBar, Box, CircularProgress, Container, Toolbar, Typography} from '@mui/material';
import StorefrontIcon from '@mui/icons-material/Storefront';
import {RestaurantForm, type RestaurantFormData} from '../components/restaurant/RestaurantForm';
import {useCreateRestaurant} from '../hooks/UseRestaurants';
import {useCurrentOwner} from '../hooks/UseOwner';
import type {DaySchedule} from "../model/DaySchedule.ts";


export function CreateRestaurantPage() {
    const navigate = useNavigate();
    const {owner, isLoading: ownerLoading, error: ownerError} = useCurrentOwner();

    const {mutateAsync: createRestaurant, isPending, isError, error} = useCreateRestaurant();

    async function handleSubmit(data: RestaurantFormData) {
        if (!owner?.id) return;

        const schedules: DaySchedule[] = data.schedules
            .filter(s => s.opentime && s.closetime)
            .map(s => ({
                day: s.day.toUpperCase() as DaySchedule['day'],
                opentime: s.opentime,
                closetime: s.closetime,
            }));

        const restaurantData = {
            ownerId: owner.id,
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
            schedules,
        };

        try {
            await createRestaurant(restaurantData);
            alert('Restaurant created successfully!');
            navigate('/owner/dashboard');
        } catch (err) {
            console.error('Error creating restaurant:', err);
            alert('Failed to create restaurant. Please try again.');
        }
    }

    if (ownerLoading) {
        return (
            <Box sx={{display: 'flex', justifyContent: 'center', alignItems: 'center', minHeight: '100vh'}}>
                <CircularProgress/>
            </Box>
        );
    }

    if (ownerError) {
        return (
            <Container sx={{py: 6}}>
                <Typography color="error" sx={{textAlign: 'center'}}>
                    {ownerError.message || 'Failed to load owner information.'}
                </Typography>
            </Container>
        );
    }

    return (
        <Box sx={{minHeight: '100vh', bgcolor: 'background.default'}}>
            <AppBar position="static" color="primary" elevation={1}>
                <Toolbar>
                    <StorefrontIcon sx={{mr: 1, fontSize: 28}}/>
                    <Typography variant="h6" component="div" sx={{fontWeight: 600}}>
                        Create Your Restaurant
                    </Typography>
                </Toolbar>
            </AppBar>

            <Container maxWidth="md" sx={{py: 4}}>
                <RestaurantForm
                    submitting={isPending}
                    onSubmit={handleSubmit}
                    onCancel={() => navigate('/owner/dashboard')}
                />

                {isError && (
                    <Typography color="error" sx={{mt: 2, textAlign: 'center'}}>
                        {error?.message || 'Failed to create restaurant.'}
                    </Typography>
                )}
            </Container>
        </Box>
    );
}
