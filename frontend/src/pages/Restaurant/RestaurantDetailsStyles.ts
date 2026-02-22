import type {SxProps, Theme} from '@mui/material';

export const RestaurantDetailsStyles: Record<string, SxProps<Theme>> = {
    root: {
        minHeight: '100vh',
        display: 'flex',
        flexDirection: 'column',
        bgcolor: '#fafafa',
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
    backButton: {
        textTransform: 'none',
        fontWeight: 600,
    },
    heroSection: {
        position: 'relative',
        textAlign: 'center',
        bgcolor: '#F4F8F6',
        pb: 4,
    },
    heroImage: {
        width: '100%',
        objectFit: 'cover',
        borderBottom: '3px solid #E0E0E0',
    },
    heroContent: {
        mt: 3,
        color: '#1f3b2e',
    },
    card: {
        borderRadius: 2,
        boxShadow: 3,
        overflow: 'hidden',
        transition: 'transform 0.2s, box-shadow 0.2s',
        '&:hover': {
            transform: 'translateY(-4px)',
            boxShadow: 6,
        },
    },
    footer: {
        bgcolor: '#f6f6f6',
        py: 3,
        textAlign: 'center',
        borderTop: 1,
        borderColor: 'divider',
    },
};