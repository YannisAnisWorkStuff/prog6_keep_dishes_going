import {useContext, useEffect} from 'react';
import {useNavigate} from 'react-router-dom';
import {Box, Button, Container, Divider, Paper, Typography,} from '@mui/material';
import LoginIcon from '@mui/icons-material/Login';
import AppRegistrationIcon from '@mui/icons-material/AppRegistration';
import PersonIcon from '@mui/icons-material/Person';
import SecurityContext from '../../context/SecurityContext';

export function CustomerAuthPage() {
    const navigate = useNavigate();
    const {isInitialised, isAuthenticated, login, loggedInUser} = useContext(SecurityContext);

    useEffect(() => {
        if (isInitialised && isAuthenticated()) {
            if (loggedInUser?.roles?.includes('customer')) {
                navigate('/restaurants');
            }
        }
    }, [isInitialised, isAuthenticated, loggedInUser, navigate]);

    if (!isInitialised) {
        return (
            <Box sx={{display: 'flex', justifyContent: 'center', alignItems: 'center', minHeight: '100vh'}}>
                <Typography>Loading...</Typography>
            </Box>
        );
    }

    return (
        <Box sx={{minHeight: '100vh', bgcolor: 'background.default', display: 'flex', alignItems: 'center'}}>
            <Container maxWidth="sm">
                <Paper elevation={3} sx={{p: 4, borderRadius: 3}}>
                    <Box sx={{textAlign: 'center', mb: 4}}>
                        <PersonIcon sx={{fontSize: 60, color: 'success.main', mb: 2}}/>
                        <Typography variant="h4" component="h1" gutterBottom fontWeight={700}>
                            Welcome, Customer
                        </Typography>
                        <Typography variant="body1" color="text.secondary">
                            Sign in or create an account to start ordering your favorite dishes.
                        </Typography>
                    </Box>

                    <Divider sx={{my: 3}}/>

                    <Box sx={{display: 'flex', flexDirection: 'column', gap: 2}}>
                        <Button
                            variant="contained"
                            size="large"
                            fullWidth
                            startIcon={<LoginIcon/>}
                            onClick={() => login()}
                            sx={{
                                py: 1.5,
                                textTransform: 'none',
                                fontSize: '1rem',
                                fontWeight: 600,
                            }}
                        >
                            Sign in with Keycloak
                        </Button>

                        <Button
                            variant="outlined"
                            size="large"
                            fullWidth
                            startIcon={<AppRegistrationIcon/>}
                            onClick={() => navigate('/customer/register')}
                            sx={{
                                py: 1.5,
                                textTransform: 'none',
                                fontSize: '1rem',
                                fontWeight: 600,
                            }}
                        >
                            Register as New Customer
                        </Button>

                        <Typography variant="body2" color="text.secondary" sx={{textAlign: 'center', mt: 2}}>
                            New here? Create a customer account and start ordering.
                        </Typography>
                    </Box>

                    <Divider sx={{my: 3}}/>

                    <Box sx={{textAlign: 'center'}}>
                        <Button variant="text" onClick={() => navigate('/')} sx={{textTransform: 'none'}}>
                            Back to home
                        </Button>
                    </Box>
                </Paper>
            </Container>
        </Box>
    );
}