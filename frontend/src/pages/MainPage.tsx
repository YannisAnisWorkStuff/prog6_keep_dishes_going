import {useState} from 'react';
import {useNavigate} from 'react-router-dom';
import {AppBar, Box, Button, Container, TextField, Toolbar, Typography,} from '@mui/material';
import SearchIcon from '@mui/icons-material/Search';
import StorefrontIcon from '@mui/icons-material/Storefront';
import PersonAddAltIcon from '@mui/icons-material/PersonAddAlt';
import {MainPageStyles} from './MainPageStyles.ts';

export function MainPage() {
    const navigate = useNavigate();
    const [address, setAddress] = useState('');

    const handleSearch = () => {
        if (address.trim()) {
            navigate(`/restaurants?address=${encodeURIComponent(address)}`);
        }
    };

    return (
        <Box sx={MainPageStyles.root}>
            <AppBar position="static" color="transparent" elevation={0} sx={MainPageStyles.appBar}>
                <Toolbar>
                    <StorefrontIcon sx={MainPageStyles.logoIcon}/>
                    <Typography variant="h6" component="div" sx={MainPageStyles.logoText}>
                        Keep Dishes Going
                    </Typography>

                    <Box sx={{display: 'flex', gap: 2}}>
                        <Button
                            variant="outlined"
                            onClick={() => navigate('/owner/auth')}
                            sx={MainPageStyles.ownerButton}
                        >
                            You're an owner?
                        </Button>

                        <Button
                            variant="contained"
                            color="success"
                            startIcon={<PersonAddAltIcon/>}
                            onClick={() => navigate('/customer/auth')}
                            sx={{
                                textTransform: 'none',
                                fontWeight: 600,
                            }}
                        >
                            Register as Customer
                        </Button>
                    </Box>
                </Toolbar>
            </AppBar>

            <Box sx={MainPageStyles.heroSection}>
                <Container maxWidth="md">
                    <Box sx={MainPageStyles.heroContent}>
                        <Typography variant="h2" component="h1" gutterBottom sx={MainPageStyles.heroTitle}>
                            Food delivery and takeout
                        </Typography>
                        <Typography variant="h5" sx={MainPageStyles.heroSubtitle}>
                            Enter your address to discover restaurants near you
                        </Typography>

                        <Box sx={MainPageStyles.searchBox}>
                            <TextField
                                fullWidth
                                placeholder="Enter delivery address"
                                variant="standard"
                                value={address}
                                onChange={(e) => setAddress(e.target.value)}
                                onKeyPress={(e) => e.key === 'Enter' && handleSearch()}
                                InputProps={{
                                    disableUnderline: true,
                                    sx: {px: 2, py: 1},
                                }}
                            />
                            <Button
                                variant="contained"
                                onClick={handleSearch}
                                disabled={!address.trim()}
                                sx={MainPageStyles.searchButton}
                                startIcon={<SearchIcon/>}
                            >
                                Find Food
                            </Button>
                        </Box>
                    </Box>
                </Container>
            </Box>

            <Box sx={MainPageStyles.footer}>
                <Typography variant="body2" color="text.secondary">
                    © 2025 Keep Dishes Going - Yiannis Ftiti
                </Typography>
            </Box>
        </Box>
    );
}
