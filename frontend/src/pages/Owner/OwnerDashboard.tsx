import {useContext} from 'react';
import {useNavigate} from 'react-router-dom';
import {
    Alert,
    AppBar,
    Box,
    Button,
    Card,
    CardActions,
    CardContent,
    CardMedia,
    CircularProgress,
    Container,
    Toolbar,
    Typography,
} from '@mui/material';
import AddIcon from '@mui/icons-material/Add';
import EditIcon from '@mui/icons-material/Edit';
import LogoutIcon from '@mui/icons-material/Logout';
import StorefrontIcon from '@mui/icons-material/Storefront';
import SecurityContext from '../../context/SecurityContext';
import {OwnerDashboardStyles} from './OwnerDashboardStyles';
import {useRestaurant} from '../../hooks/UseRestaurants.ts';
import {useCurrentOwner} from "../../hooks/UseOwner.ts";

export function OwnerDashboard() {
    const navigate = useNavigate();
    const {logout, loggedInUser} = useContext(SecurityContext);

    const {owner, isLoading: ownerLoading, error: ownerError} = useCurrentOwner();
    const ownerId = owner?.id ?? null;

    const {
        data: restaurant,
        isLoading: restaurantLoading,
        error: restaurantError,
    } = useRestaurant(ownerId ?? '');

    if (ownerLoading || restaurantLoading || !ownerId) {
        return (
            <Box sx={OwnerDashboardStyles.loaderBox}>
                <CircularProgress/>
            </Box>
        );
    }

    return (
        <Box sx={OwnerDashboardStyles.root}>
            <AppBar position="static" color="primary" elevation={1}>
                <Toolbar>
                    <StorefrontIcon sx={{mr: 1, fontSize: 28}}/>
                    <Typography variant="h6" component="div" sx={{flexGrow: 1, fontWeight: 600}}>
                        Owner Dashboard
                    </Typography>
                    <Typography variant="body2" sx={{mr: 2}}>
                        {loggedInUser?.name}
                    </Typography>
                    <Button
                        color="inherit"
                        onClick={logout}
                        startIcon={<LogoutIcon/>}
                        sx={{textTransform: 'none'}}
                    >
                        Logout
                    </Button>
                </Toolbar>
            </AppBar>

            <Container maxWidth="lg" sx={{py: 4}}>
                {(ownerError || restaurantError) && (
                    <Alert severity="error" sx={{mb: 3}}>
                        {(ownerError ?? restaurantError)?.message || 'Failed to load restaurant.'}
                    </Alert>
                )}

                <Typography variant="h4" gutterBottom fontWeight={700}>
                    Welcome back, {owner?.name || 'Owner'}!
                </Typography>

                {restaurant ? (
                    <Box sx={OwnerDashboardStyles.restaurantSection}>
                        <Typography variant="h5" gutterBottom fontWeight={600}>
                            Your Restaurant
                        </Typography>

                        <Card sx={OwnerDashboardStyles.restaurantCard}>
                            <CardMedia
                                component="img"
                                height="200"
                                image={restaurant.pictures?.[0] ?? '/assets/dish.png'}
                                alt={restaurant.name}
                                onError={(e) => {
                                    (e.currentTarget as HTMLImageElement).src = 'https://via.placeholder.com/400x200';
                                }}
                                sx={{objectFit: 'cover'}}
                            />
                            <CardContent>
                                <Typography variant="h6" fontWeight={600}>
                                    {restaurant.name}
                                </Typography>
                                <Typography variant="body2" color="text.secondary">
                                    {restaurant.cuisineType ?? '—'}
                                </Typography>
                                <Typography variant="body2" color="text.secondary" sx={{mt: 1}}>
                                    {restaurant.address
                                        ? `${restaurant.address.street} ${restaurant.address.number}, ${restaurant.address.postalCode} ${restaurant.address.city}, ${restaurant.address.country}`
                                        : '—'}
                                </Typography>
                                <Typography variant="body2" color="text.secondary">
                                    Prep time: {restaurant.preparationTimeMinutes ?? '—'} min
                                </Typography>
                            </CardContent>

                            <CardActions sx={OwnerDashboardStyles.cardActions}>
                                <Button
                                    variant="contained"
                                    color="primary"
                                    startIcon={<EditIcon/>}
                                    sx={{textTransform: 'none'}}
                                    onClick={() => navigate(`/owner/edit-restaurant/${restaurant.id}`)}
                                >
                                    Edit Details
                                </Button>
                            </CardActions>
                        </Card>

                        <Box sx={OwnerDashboardStyles.managementButtons}>
                            <Button
                                variant="contained"
                                color="secondary"
                                onClick={() => navigate(`/owner/manage-dishes/${restaurant.id}`)}
                                sx={{textTransform: 'none'}}
                            >
                                Manage Dishes
                            </Button>
                            <Button
                                variant="outlined"
                                onClick={() => navigate(`/owner/manage-orders/${restaurant.id}`)}
                                sx={{textTransform: 'none'}}
                            >
                                View Orders
                            </Button>
                        </Box>
                    </Box>
                ) : (
                    <Box sx={OwnerDashboardStyles.noRestaurantBox}>
                        <StorefrontIcon sx={{fontSize: 80, color: 'text.secondary', mb: 2}}/>
                        <Typography variant="h5" gutterBottom fontWeight={600}>
                            No restaurant yet
                        </Typography>
                        <Typography variant="body1" color="text.secondary" sx={{mb: 3}}>
                            Get started by creating your restaurant profile
                        </Typography>
                        <Button
                            variant="contained"
                            size="large"
                            startIcon={<AddIcon/>}
                            onClick={() => navigate('/owner/create-restaurant')}
                            sx={{textTransform: 'none', fontWeight: 600}}
                        >
                            Create Restaurant Now
                        </Button>
                    </Box>
                )}
            </Container>
        </Box>
    );
}