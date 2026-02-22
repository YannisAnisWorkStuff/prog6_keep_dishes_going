import {useNavigate} from 'react-router-dom';
import {Alert, Box, Button, Container, MenuItem, Paper, Stack, TextField, Typography} from '@mui/material';
import {Controller, useForm} from 'react-hook-form';
import PersonAddAlt1Icon from '@mui/icons-material/PersonAddAlt1';
import {useRegisterCustomer} from '../../hooks/UseCustomer';
import {OwnerRegisterPageStyles as CustomerRegisterStyles} from '../Owner/OwnerRegisterPageStyles';
import {COUNTRIES} from "../../model/Countries.ts";

interface RegisterFormData {
    name: string;
    email: string;
    password: string;
    confirmPassword: string;
    address: {
        street: string;
        number: string;
        postalCode: string;
        city: string;
        country: string;
    };
}

export function CustomerRegisterPage() {
    const navigate = useNavigate();
    const {mutateAsync: registerCustomer, isPending, isSuccess, isError, error} = useRegisterCustomer();

    const {
        control,
        handleSubmit,
        watch,
        formState: {errors},
    } = useForm<RegisterFormData>({
        defaultValues: {
            name: '',
            email: '',
            password: '',
            confirmPassword: '',
            address: {
                street: '',
                number: '',
                postalCode: '',
                city: '',
                country: 'Belgium',
            },
        },
    });

    async function onSubmit(data: RegisterFormData) {
        if (data.password !== data.confirmPassword) {
            throw new Error('Passwords do not match.');
        }

        await registerCustomer({
            name: data.name,
            email: data.email,
            password: data.password,
            address: data.address,
        });

        setTimeout(() => navigate('/customer/auth'), 2000);
    }

    return (
        <Box sx={CustomerRegisterStyles.root}>
            <Container maxWidth="sm">
                <Paper elevation={3} sx={CustomerRegisterStyles.paper}>
                    <Box sx={{textAlign: 'center', mb: 3}}>
                        <PersonAddAlt1Icon sx={{fontSize: 60, color: 'success.main', mb: 2}}/>
                        <Typography variant="h4" fontWeight={700}>
                            Register as Customer
                        </Typography>
                        <Typography variant="body2" color="text.secondary">
                            Create your account to start ordering food.
                        </Typography>
                    </Box>

                    {isError && (
                        <Alert severity="error" sx={{mb: 2}}>
                            {error?.message || 'Failed to register. Please try again.'}
                        </Alert>
                    )}

                    {isSuccess && (
                        <Alert severity="success" sx={{mb: 2}}>
                            Registration successful! Redirecting to sign in...
                        </Alert>
                    )}

                    <form onSubmit={handleSubmit(onSubmit)}>
                        <Stack spacing={3}>
                            {/* Personal Info */}
                            <Controller
                                name="name"
                                control={control}
                                rules={{required: 'Full name is required'}}
                                render={({field}) => (
                                    <TextField
                                        {...field}
                                        label="Full Name"
                                        fullWidth
                                        error={!!errors.name}
                                        helperText={errors.name?.message}
                                    />
                                )}
                            />
                            <Controller
                                name="email"
                                control={control}
                                rules={{
                                    required: 'Email is required',
                                    pattern: {
                                        value: /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i,
                                        message: 'Invalid email address',
                                    },
                                }}
                                render={({field}) => (
                                    <TextField
                                        {...field}
                                        label="Email Address"
                                        type="email"
                                        fullWidth
                                        error={!!errors.email}
                                        helperText={errors.email?.message}
                                    />
                                )}
                            />

                            {/* Passwords */}
                            <Controller
                                name="password"
                                control={control}
                                rules={{
                                    required: 'Password is required',
                                    minLength: {value: 6, message: 'Minimum 6 characters'},
                                }}
                                render={({field}) => (
                                    <TextField
                                        {...field}
                                        label="Password"
                                        type="password"
                                        fullWidth
                                        error={!!errors.password}
                                        helperText={errors.password?.message}
                                    />
                                )}
                            />
                            <Controller
                                name="confirmPassword"
                                control={control}
                                rules={{
                                    required: 'Please confirm your password',
                                    validate: (value) =>
                                        value === watch('password') || 'Passwords do not match',
                                }}
                                render={({field}) => (
                                    <TextField
                                        {...field}
                                        label="Confirm Password"
                                        type="password"
                                        fullWidth
                                        error={!!errors.confirmPassword}
                                        helperText={errors.confirmPassword?.message}
                                    />
                                )}
                            />

                            {/* Address Fields */}
                            <Typography variant="h6" fontWeight={600} sx={{mt: 2}}>
                                Address
                            </Typography>

                            <Controller
                                name="address.street"
                                control={control}
                                rules={{required: 'Street is required'}}
                                render={({field}) => (
                                    <TextField
                                        {...field}
                                        label="Street"
                                        fullWidth
                                        error={!!errors.address?.street}
                                        helperText={errors.address?.street?.message}
                                    />
                                )}
                            />
                            <Controller
                                name="address.number"
                                control={control}
                                rules={{required: 'Number is required'}}
                                render={({field}) => (
                                    <TextField
                                        {...field}
                                        label="Number"
                                        fullWidth
                                        error={!!errors.address?.number}
                                        helperText={errors.address?.number?.message}
                                    />
                                )}
                            />
                            <Controller
                                name="address.postalCode"
                                control={control}
                                rules={{required: 'Postal code is required'}}
                                render={({field}) => (
                                    <TextField
                                        {...field}
                                        label="Postal Code"
                                        fullWidth
                                        error={!!errors.address?.postalCode}
                                        helperText={errors.address?.postalCode?.message}
                                    />
                                )}
                            />
                            <Controller
                                name="address.city"
                                control={control}
                                rules={{required: 'City is required'}}
                                render={({field}) => (
                                    <TextField
                                        {...field}
                                        label="City"
                                        fullWidth
                                        error={!!errors.address?.city}
                                        helperText={errors.address?.city?.message}
                                    />
                                )}
                            />

                            {/* Country Dropdown */}
                            <Controller
                                name="address.country"
                                control={control}
                                rules={{required: 'Country is required'}}
                                render={({field}) => (
                                    <TextField
                                        {...field}
                                        select
                                        label="Country"
                                        fullWidth
                                        error={!!errors.address?.country}
                                        helperText={errors.address?.country?.message}
                                    >
                                        {COUNTRIES.map((country) => (
                                            <MenuItem key={country} value={country}>
                                                {country}
                                            </MenuItem>
                                        ))}
                                    </TextField>
                                )}
                            />
                        </Stack>

                        <Box sx={CustomerRegisterStyles.actions}>
                            <Button
                                variant="outlined"
                                onClick={() => navigate('/customer/auth')}
                                disabled={isPending}
                                sx={{textTransform: 'none'}}
                            >
                                Cancel
                            </Button>
                            <Button
                                type="submit"
                                variant="contained"
                                disabled={isPending}
                                sx={{textTransform: 'none', minWidth: 150}}
                            >
                                {isPending ? 'Registering...' : 'Register'}
                            </Button>
                        </Box>
                    </form>
                </Paper>
            </Container>
        </Box>
    );
}