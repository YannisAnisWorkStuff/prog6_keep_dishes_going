import type {SxProps, Theme} from '@mui/material';

export const OwnerRegisterPageStyles: Record<string, SxProps<Theme>> = {
    root: {
        minHeight: '100vh',
        bgcolor: 'background.default',
        display: 'flex',
        alignItems: 'center',
    },
    paper: {
        p: 4,
        borderRadius: 3,
    },
    actions: {
        mt: 4,
        display: 'flex',
        justifyContent: 'flex-end',
        gap: 2,
    },
};