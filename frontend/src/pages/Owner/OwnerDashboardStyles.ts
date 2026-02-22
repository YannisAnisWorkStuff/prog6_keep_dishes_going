import type {SxProps, Theme} from '@mui/material';

export const OwnerDashboardStyles: Record<string, SxProps<Theme>> = {
    root: {
        minHeight: '100vh',
        bgcolor: 'background.default',
    },
    loaderBox: {
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        minHeight: '100vh',
    },
    restaurantSection: {
        mt: 4,
    },
    restaurantCard: {
        maxWidth: 400,
        mt: 2,
    },
    cardActions: {
        display: 'flex',
        justifyContent: 'flex-end',
        px: 2,
        pb: 2,
    },
    managementButtons: {
        mt: 4,
        display: 'flex',
        gap: 2,
        flexWrap: 'wrap',
    },
    noRestaurantBox: {
        mt: 4,
        p: 6,
        textAlign: 'center',
        bgcolor: 'white',
        borderRadius: 2,
        border: '2px dashed',
        borderColor: 'divider',
    },
};