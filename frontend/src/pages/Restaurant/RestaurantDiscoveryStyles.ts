import type {SxProps, Theme} from '@mui/material';

export const RestaurantDiscoveryStyles: Record<string, SxProps<Theme>> = {
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
        flex: 1,
        background: 'linear-gradient(135deg, #EAF4EE 0%, #C6E2D2 100%)',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        py: 6,
    },
    heroContent: {
        textAlign: 'center',
        color: '#1f3b2e',
        mb: 4,
    },
    gridContainer: {
        display: 'grid',
        gridTemplateColumns: 'repeat(auto-fill, minmax(260px, 1fr))',
        gap: 2,
        mt: 3,
    },
    card: {
        borderRadius: 2,
        boxShadow: 3,
        overflow: 'hidden',
        cursor: 'pointer',
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
