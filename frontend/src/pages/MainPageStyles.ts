// src/pages/MainPage.styles.ts
import type {SxProps, Theme} from '@mui/material';

export const MainPageStyles: Record<string, SxProps<Theme>> = {
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
        alignItems: 'center',
        justifyContent: 'center',
        py: 8,
    },
    heroContent: {
        textAlign: 'center',
        color: 'white',
    },
    heroTitle: {
        fontWeight: 700,
        mb: 2,
    },
    heroSubtitle: {
        mb: 4,
        opacity: 0.95,
    },
    searchBox: {
        display: 'flex',
        gap: 2,
        maxWidth: 600,
        mx: 'auto',
        bgcolor: 'white',
        p: 1,
        borderRadius: 2,
        boxShadow: 3,
    },
    searchButton: {
        minWidth: 120,
        textTransform: 'none',
        fontWeight: 600,
        boxShadow: 'none',
    },
    footer: {
        bgcolor: '#f6f6f6',
        py: 3,
        textAlign: 'center',
        borderTop: 1,
        borderColor: 'divider',
    },
};