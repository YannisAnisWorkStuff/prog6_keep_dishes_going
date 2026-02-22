import {useContext} from 'react';
import {Navigate} from 'react-router-dom';
import {Box, CircularProgress} from '@mui/material';
import SecurityContext from '../context/SecurityContext';

interface ProtectedRouteProps {
    children: React.ReactNode;
}

export function ProtectedRoute({children}: ProtectedRouteProps) {
    const {isInitialised, isAuthenticated, loggedInUser} = useContext(SecurityContext);

    if (!isInitialised) {
        return (
            <Box sx={{display: 'flex', justifyContent: 'center', alignItems: 'center', minHeight: '100vh'}}>
                <CircularProgress/>
            </Box>
        );
    }

    if (!isAuthenticated()) {
        return <Navigate to="/owner/auth" replace/>;
    }

    if (!loggedInUser?.roles?.includes('owner')) {
        return <Navigate to="/" replace/>;
    }

    return <>{children}</>;
}