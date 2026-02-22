import type {SxProps, Theme} from '@mui/material';

export const RestaurantEditStyles: Record<string, SxProps<Theme>> = {
    root: {
        minHeight: '100vh',
        display: 'flex',
        flexDirection: 'column',
    },
    appBar: {
        borderBottom: 1,
        borderColor: 'divider',
        bgcolor: 'white',
    },
    logoIcon: {
        mr: 1,
        color: 'primary.main',
        fontSize: 32,
    },
    logoText: {
        flexGrow: 1,
        fontWeight: 700,
    },
    ownerButton: {
        textTransform: 'none',
        fontWeight: 600,
    },
    heroSection: {
        flex: 1,
        background: 'linear-gradient(135deg, #06C167 0%, #048A4A 100%)',
        display: 'flex',
        alignItems: 'flex-start',
        justifyContent: 'center',
        py: 8,
    },
    heroContent: {
        textAlign: 'center',
        color: 'white',
        backgroundColor: 'rgba(255,255,255,0.1)',
        borderRadius: 4,
        p: 4,
        boxShadow: 3,
    },
    footer: {
        bgcolor: '#f6f6f6',
        py: 3,
        textAlign: 'center',
        borderTop: 1,
        borderColor: 'divider',
    },
};
