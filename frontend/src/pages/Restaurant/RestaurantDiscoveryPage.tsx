import {useContext, useMemo, useState} from 'react';
import {useNavigate, useSearchParams} from 'react-router-dom';
import {
    AppBar,
    Box,
    Button,
    Checkbox,
    CircularProgress,
    Container,
    FormControl,
    InputLabel,
    ListItemText,
    MenuItem,
    OutlinedInput,
    Paper,
    Select,
    Stack,
    TextField,
    Toolbar,
    Typography,
} from '@mui/material';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import AccessTimeIcon from '@mui/icons-material/AccessTime';
import FilterAltIcon from '@mui/icons-material/FilterAlt';
import {RestaurantDiscoveryStyles} from './RestaurantDiscoveryStyles';
import {useRestaurantDiscovery} from '../../hooks/UseRestaurants.ts';
import {extractCuisines, filterRestaurants,} from '../../utils/DiscoveryRestaurantMethods';
import SecurityContext from "../../context/SecurityContext.ts";
import RestaurantCard from "../../components/restaurant/RestaurantCard.tsx";
import StorefrontIcon from "@mui/icons-material/Storefront";


export function RestaurantDiscoveryPage() {
    const [params] = useSearchParams();
    const address = params.get('address') || '';
    const navigate = useNavigate();
    const {data: restaurants = [], isLoading, error} = useRestaurantDiscovery();

    const [searchTerm, setSearchTerm] = useState('');
    const [cuisineFilter, setCuisineFilter] = useState<string[]>([]);
    const [sortBy, setSortBy] = useState<'default' | 'fastest'>('default');
    const [openNow, setOpenNow] = useState(false);
    const cuisines = useMemo(() => extractCuisines(restaurants), [restaurants]);

    const {isAuthenticated, logout, loggedInUser} = useContext(SecurityContext);

    const [priceRange, setPriceRange] = useState<[number, number]>([0, 100]);
    const [maxDeliveryTime, setMaxDeliveryTime] = useState<number | null>(null);
    const [maxDistance, setMaxDistance] = useState<number | null>(null);


    const filteredRestaurants = useMemo(
        () =>
            filterRestaurants(restaurants, {
                searchTerm,
                cuisineFilter,
                openNow,
                sortBy,
                priceRange,
                maxDistance,
                maxDeliveryTime,
            }),
        [restaurants, searchTerm, cuisineFilter, sortBy, openNow, priceRange, maxDistance, maxDeliveryTime]
    );


    if (isLoading)
        return (
            <Container sx={{py: 6, textAlign: 'center'}}>
                <CircularProgress/>
            </Container>
        );

    if (error)
        return (
            <Container sx={{py: 6}}>
                <Typography color="error">Failed to load restaurants.</Typography>
            </Container>
        );

    return (
        <Box sx={RestaurantDiscoveryStyles.root}>
            <AppBar
                position="static"
                color="transparent"
                elevation={0}
                sx={RestaurantDiscoveryStyles.appBar}
            >
                <Toolbar>
                    <StorefrontIcon sx={{mr: 2}}/>
                    <Typography
                        variant="h6"
                        sx={{
                            ...RestaurantDiscoveryStyles.logoText,
                            cursor: loggedInUser?.roles?.includes('owner') ? 'pointer' : 'default',
                            userSelect: 'none',
                        }}
                        onClick={() => {
                            if (loggedInUser?.roles?.includes('owner')) {
                                navigate('/owner/dashboard');
                            }
                        }}
                    >
                        Keep Dishes Going
                    </Typography>

                    {isAuthenticated() ? (
                        <Button
                            color="error"
                            variant="outlined"
                            onClick={() => logout()}
                            sx={RestaurantDiscoveryStyles.backButton}
                        >
                            Logout
                        </Button>
                    ) : (
                        <Button
                            startIcon={<ArrowBackIcon/>}
                            onClick={() => navigate('/')}
                            sx={RestaurantDiscoveryStyles.backButton}
                        >
                            Back
                        </Button>
                    )}
                </Toolbar>
            </AppBar>

            <Box sx={{
                ...RestaurantDiscoveryStyles.heroSection,
                backgroundImage: 'url(/assets/discovery_background.jpg)',
                backgroundSize: 'cover',
                backgroundPosition: 'center',
                backgroundRepeat: 'no-repeat',
                position: 'relative',
                '&::before': {
                    content: '""',
                    position: 'absolute',
                    top: 0,
                    left: 0,
                    right: 0,
                    bottom: 0,
                    backgroundColor: 'rgba(0, 0, 0, 0.5)', // Dark overlay for better text readability
                    zIndex: 1,
                },
            }}>
                <Container maxWidth="lg" sx={{position: 'relative', zIndex: 2}}>
                    <Box sx={RestaurantDiscoveryStyles.heroContent}>
                        <Typography variant="h3" fontWeight={700} gutterBottom sx={{color: 'white'}}>
                            Discover Restaurants
                        </Typography>
                        <Typography variant="h6" sx={{opacity: 0.9, mb: 3, color: 'white'}}>
                            Showing restaurants near {address || 'you'}
                        </Typography>

                        <Box sx={{display: 'flex', justifyContent: 'center', mb: 4}}>
                            <TextField
                                placeholder="Search by name or city..."
                                value={searchTerm}
                                onChange={(e) => setSearchTerm(e.target.value)}
                                fullWidth
                                variant="outlined"
                                size="medium"
                                sx={{
                                    maxWidth: 600,
                                    bgcolor: 'white',
                                    borderRadius: 2,
                                    boxShadow: 3,
                                }}
                            />
                        </Box>

                        <Paper
                            elevation={2}
                            sx={{
                                p: 2.5,
                                borderRadius: 3,
                                backgroundColor: 'rgba(255,255,255,0.95)',
                                maxWidth: 900,
                                mx: 'auto',
                            }}
                        >
                            <Stack
                                direction={{xs: 'column', md: 'row'}}
                                spacing={2}
                                alignItems="center"
                                justifyContent="space-between"
                                flexWrap="wrap"
                            >
                                <Box display="flex" alignItems="center" gap={1}>
                                    <FilterAltIcon color="primary"/>
                                    <Typography fontWeight={600}>Filters</Typography>
                                </Box>

                                <FormControl size="small" sx={{minWidth: 180}}>
                                    <InputLabel>Cuisine Type</InputLabel>
                                    <Select
                                        multiple
                                        value={cuisineFilter}
                                        onChange={(e) =>
                                            setCuisineFilter(
                                                typeof e.target.value === 'string'
                                                    ? e.target.value.split(',')
                                                    : e.target.value
                                            )
                                        }
                                        input={<OutlinedInput label="Cuisine Type"/>}
                                        renderValue={(selected) => selected.join(', ')}
                                    >
                                        {cuisines.map((cuisine) => (
                                            <MenuItem key={cuisine} value={cuisine}>
                                                <Checkbox checked={cuisineFilter.indexOf(cuisine) > -1}/>
                                                <ListItemText primary={cuisine}/>
                                            </MenuItem>
                                        ))}
                                    </Select>
                                </FormControl>

                                <Box sx={{display: 'flex', alignItems: 'center', gap: 1}}>
                                    <Typography variant="body2" fontWeight={600}>Price (€)</Typography>
                                    <TextField
                                        type="number"
                                        label="Min"
                                        size="small"
                                        value={priceRange[0]}
                                        onChange={(e) => setPriceRange([+e.target.value, priceRange[1]])}
                                        sx={{width: 80}}
                                    />
                                    <TextField
                                        type="number"
                                        label="Max"
                                        size="small"
                                        value={priceRange[1]}
                                        onChange={(e) => setPriceRange([priceRange[0], +e.target.value])}
                                        sx={{width: 80}}
                                    />
                                </Box>

                                {/* Max Distance Filter */}
                                <Box sx={{display: 'flex', alignItems: 'center', gap: 1}}>
                                    <Typography variant="body2" fontWeight={600}>Distance (km)</Typography>
                                    <TextField
                                        type="number"
                                        size="small"
                                        placeholder="e.g. 5"
                                        value={maxDistance ?? ''}
                                        onChange={(e) =>
                                            setMaxDistance(e.target.value ? +e.target.value : null)
                                        }
                                        sx={{width: 100}}
                                    />
                                </Box>

                                {/* Delivery Time Filter */}
                                <Box sx={{display: 'flex', alignItems: 'center', gap: 1}}>
                                    <Typography variant="body2" fontWeight={600}>Max Time (min)</Typography>
                                    <TextField
                                        type="number"
                                        size="small"
                                        placeholder="e.g. 30"
                                        value={maxDeliveryTime ?? ''}
                                        onChange={(e) =>
                                            setMaxDeliveryTime(e.target.value ? +e.target.value : null)
                                        }
                                        sx={{width: 100}}
                                    />
                                </Box>

                                <FormControl size="small" sx={{minWidth: 180}}>
                                    <InputLabel>Sort By</InputLabel>
                                    <Select
                                        value={sortBy}
                                        label="Sort By"
                                        onChange={(e) =>
                                            setSortBy(e.target.value as 'default' | 'fastest')
                                        }
                                    >
                                        <MenuItem value="default">Default</MenuItem>
                                        <MenuItem value="fastest">
                                            <AccessTimeIcon fontSize="small" sx={{mr: 1}}/> Fastest
                                        </MenuItem>
                                    </Select>
                                </FormControl>

                                <Button
                                    variant={openNow ? 'contained' : 'outlined'}
                                    color="success"
                                    onClick={() => setOpenNow(!openNow)}
                                    sx={{textTransform: 'none', fontWeight: 600}}
                                >
                                    {openNow ? 'Open Now ✓' : 'Open Now'}
                                </Button>
                            </Stack>

                        </Paper>
                    </Box>
                </Container>
            </Box>

            <Container sx={{py: 6}}>
                {filteredRestaurants.length === 0 ? (
                    <Typography align="center" color="text.secondary" sx={{mt: 4}}>
                        No restaurants match your filters.
                    </Typography>
                ) : (
                    <Box sx={RestaurantDiscoveryStyles.gridContainer}>
                        {filteredRestaurants.map((r) => (
                            <RestaurantCard
                                key={r.id}
                                restaurant={r}
                                onClick={() => navigate(`/restaurants/${r.id}`)}
                            />
                        ))}
                    </Box>
                )}
            </Container>

            <Box sx={RestaurantDiscoveryStyles.footer}>
                <Typography variant="body2" color="text.secondary">
                    © 2025 Keep Dishes Going - Yiannis Ftiti
                </Typography>
            </Box>
        </Box>
    );
}