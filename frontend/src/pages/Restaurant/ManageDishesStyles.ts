import type {SxProps, Theme} from '@mui/material';

export const ManageDishesStyles: Record<string, SxProps<Theme>> = {
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
    heroSection: {
        flex: 1,
        background: 'linear-gradient(135deg, #06C167 0%, #048A4A 100%)',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        py: 6,
    },
    heroContent: {
        textAlign: 'center',
        color: 'white',
        mb: 4,
    },
    footer: {
        bgcolor: '#f6f6f6',
        py: 3,
        textAlign: 'center',
        borderTop: 1,
        borderColor: 'divider',
    },
    gridContainer: {
        display: 'grid',
        gridTemplateColumns: 'repeat(auto-fill, minmax(260px, 1fr))',
        gap: 2,
        mt: 3,
    },
};