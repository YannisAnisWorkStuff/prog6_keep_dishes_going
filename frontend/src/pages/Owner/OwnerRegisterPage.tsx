import {useNavigate} from 'react-router-dom';
import {Alert, Box, Button, Container, Paper, Stack, TextField, Typography,} from '@mui/material';
import {Controller, useForm} from 'react-hook-form';
import StorefrontIcon from '@mui/icons-material/Storefront';
import {useRegisterOwner} from '../../hooks/UseOwner';
import {OwnerRegisterPageStyles} from './OwnerRegisterPageStyles';

interface RegisterFormData {
    name: string;
    email: string;
    password: string;
    confirmPassword: string;
}

export function OwnerRegisterPage() {
    const navigate = useNavigate();
    const {mutateAsync: registerOwner, isPending, isSuccess, isError, error} = useRegisterOwner();

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
        },
    });

    async function onSubmit(data: RegisterFormData) {
        if (data.password !== data.confirmPassword) {
            throw new Error('Passwords do not match.');
        }

        await registerOwner({
            name: data.name,
            email: data.email,
            password: data.password,
        });

        setTimeout(() => navigate('/owner/auth'), 2000);
    }

    return (
        <Box sx={OwnerRegisterPageStyles.root}>
            <Container maxWidth="sm">
                <Paper elevation={3} sx={OwnerRegisterPageStyles.paper}>
                    <Box sx={{textAlign: 'center', mb: 3}}>
                        <StorefrontIcon sx={{fontSize: 60, color: 'primary.main', mb: 2}}/>
                        <Typography variant="h4" fontWeight={700}>
                            Register as Restaurant Owner
                        </Typography>
                        <Typography variant="body2" color="text.secondary">
                            Create your owner account to start managing your restaurant.
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
                        </Stack>

                        <Box sx={OwnerRegisterPageStyles.actions}>
                            <Button
                                variant="outlined"
                                onClick={() => navigate('/owner/auth')}
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
