import type {SxProps, Theme} from '@mui/material';

export const RestaurantFormStyles: Record<string, SxProps<Theme>> = {
    paper: {
        p: 4,
        borderRadius: 2,
    },
    flexRow: {
        display: 'flex',
        gap: 2,
    },
    actions: {
        mt: 4,
        display: 'flex',
        gap: 2,
        justifyContent: 'flex-end',
    },
};