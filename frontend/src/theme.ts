// theme.ts
import {createTheme} from '@mui/material/styles';

const theme = createTheme({
    palette: {
        primary: {
            main: '#06C167',
        },
        secondary: {
            main: '#000000',
        },
        background: {
            default: '#f6f6f6',
        },
    },
    typography: {
        fontFamily: '"Inter", "Roboto", "Helvetica", "Arial", sans-serif',
        h1: {
            fontWeight: 700,
        },
        h2: {
            fontWeight: 600,
        },
    },
    shape: {
        borderRadius: 8,
    },
});

export default theme;
