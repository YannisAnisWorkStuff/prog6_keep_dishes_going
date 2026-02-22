import {Controller, useForm} from 'react-hook-form';
import {Box, Button, CircularProgress, MenuItem, Paper, Stack, TextField, Typography,} from '@mui/material';
import {RestaurantFormStyles} from './RestaurantStyles';
import type {DaySchedule} from '../../model/DaySchedule';
import {ChipInput} from "../ChipInput.tsx";
import {COUNTRIES} from "../../model/Countries.ts";

export interface RestaurantFormData {
    name: string;
    street: string;
    number: string;
    postalCode: string;
    city: string;
    country: string;
    cuisineType: string;
    email: string;
    pictures: string[];
    preparationTimeMinutes: number;
    schedules: DaySchedule[];
}

interface RestaurantFormProps {
    submitting: boolean;
    onSubmit: (data: RestaurantFormData) => void;
    onCancel: () => void;
    defaultValues?: Partial<RestaurantFormData>;
}

export function RestaurantForm({
                                   submitting,
                                   onSubmit,
                                   onCancel,
                                   defaultValues,
                               }: RestaurantFormProps) {
    const {
        control,
        handleSubmit,
        formState: {errors},
    } = useForm<RestaurantFormData>({
        defaultValues: {
            name: '',
            street: '',
            number: '',
            postalCode: '',
            city: '',
            country: 'Belgium',
            cuisineType: '',
            email: '',
            pictures: [],
            preparationTimeMinutes: 30,
            schedules: [
                {day: 'MONDAY', opentime: '', closetime: ''},
                {day: 'TUESDAY', opentime: '', closetime: ''},
                {day: 'WEDNESDAY', opentime: '', closetime: ''},
                {day: 'THURSDAY', opentime: '', closetime: ''},
                {day: 'FRIDAY', opentime: '', closetime: ''},
                {day: 'SATURDAY', opentime: '', closetime: ''},
                {day: 'SUNDAY', opentime: '', closetime: ''},
            ],
            ...defaultValues,
        },
    });

    return (
        <Paper elevation={2} sx={RestaurantFormStyles.paper}>
            <Typography variant="h5" gutterBottom fontWeight={700}>
                Restaurant Details
            </Typography>
            <Typography variant="body2" color="text.secondary" sx={{mb: 4}}>
                Fill in the information about your restaurant
            </Typography>

            <form onSubmit={handleSubmit(onSubmit)}>
                <Stack spacing={3}>
                    <Controller
                        name="name"
                        control={control}
                        rules={{required: 'Restaurant name is required'}}
                        render={({field}) => (
                            <TextField
                                {...field}
                                label="Restaurant Name"
                                fullWidth
                                error={!!errors.name}
                                helperText={errors.name?.message}
                            />
                        )}
                    />

                    <Box sx={RestaurantFormStyles.flexRow}>
                        <Controller
                            name="street"
                            control={control}
                            rules={{required: 'Street is required'}}
                            render={({field}) => (
                                <TextField
                                    {...field}
                                    label="Street"
                                    fullWidth
                                    error={!!errors.street}
                                    helperText={errors.street?.message}
                                />
                            )}
                        />

                        <Controller
                            name="number"
                            control={control}
                            rules={{required: 'Number is required'}}
                            render={({field}) => (
                                <TextField
                                    {...field}
                                    label="Number"
                                    sx={{maxWidth: 150}}
                                    error={!!errors.number}
                                    helperText={errors.number?.message}
                                />
                            )}
                        />
                    </Box>

                    <Box sx={RestaurantFormStyles.flexRow}>
                        <Controller
                            name="postalCode"
                            control={control}
                            rules={{required: 'Postal code is required'}}
                            render={({field}) => (
                                <TextField
                                    {...field}
                                    label="Postal Code"
                                    sx={{flex: 1}}
                                    error={!!errors.postalCode}
                                    helperText={errors.postalCode?.message}
                                />
                            )}
                        />
                        <Controller
                            name="city"
                            control={control}
                            rules={{required: 'City is required'}}
                            render={({field}) => (
                                <TextField
                                    {...field}
                                    label="City"
                                    sx={{flex: 1}}
                                    error={!!errors.city}
                                    helperText={errors.city?.message}
                                />
                            )}
                        />
                        <Controller
                            name="country"
                            control={control}
                            rules={{required: 'Country is required'}}
                            render={({field}) => (
                                <TextField
                                    {...field}
                                    select
                                    label="Country"
                                    sx={{flex: 1}}
                                    error={!!errors.country}
                                    helperText={errors.country?.message}
                                >
                                    <MenuItem value="" disabled>
                                        Select a country
                                    </MenuItem>
                                    {COUNTRIES.map((country) => (
                                        <MenuItem key={country} value={country}>
                                            {country}
                                        </MenuItem>
                                    ))}
                                </TextField>
                            )}
                        />
                    </Box>

                    <Box sx={RestaurantFormStyles.flexRow}>
                        <Controller
                            name="cuisineType"
                            control={control}
                            rules={{required: 'Cuisine type is required'}}
                            render={({field}) => (
                                <TextField
                                    {...field}
                                    label="Cuisine Type"
                                    placeholder="e.g., Italian, French, Japanese"
                                    fullWidth
                                    error={!!errors.cuisineType}
                                    helperText={errors.cuisineType?.message}
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
                                    label="Contact Email"
                                    type="email"
                                    fullWidth
                                    error={!!errors.email}
                                    helperText={errors.email?.message}
                                />
                            )}
                        />
                    </Box>

                    <Controller
                        name="pictures"
                        control={control}
                        rules={{required: 'At least one picture URL is required'}}
                        render={({field}) => (
                            <ChipInput
                                label="Picture URLs"
                                placeholder="https://example.com/image.jpg"
                                values={field.value || []}
                                onChange={field.onChange}
                                sx={{mt: 1}}
                            />
                        )}
                    />


                    <Controller
                        name="preparationTimeMinutes"
                        control={control}
                        rules={{
                            required: 'Preparation time is required',
                            min: {value: 1, message: 'Must be at least 1 minute'},
                        }}
                        render={({field}) => (
                            <TextField
                                {...field}
                                label="Default Preparation Time (minutes)"
                                type="number"
                                sx={{maxWidth: 300}}
                                error={!!errors.preparationTimeMinutes}
                                helperText={errors.preparationTimeMinutes?.message}
                            />
                        )}
                    />
                </Stack>

                <Box sx={{mt: 4}}>
                    <Typography variant="h6" gutterBottom fontWeight={600}>
                        Opening Hours
                    </Typography>

                    {[
                        'MONDAY',
                        'TUESDAY',
                        'WEDNESDAY',
                        'THURSDAY',
                        'FRIDAY',
                        'SATURDAY',
                        'SUNDAY',
                    ].map((day, index) => (
                        <Box
                            key={day}
                            sx={{
                                display: 'flex',
                                alignItems: 'center',
                                gap: 2,
                                mb: 1.5,
                            }}
                        >
                            <Typography sx={{width: 100}}>
                                {day.charAt(0) + day.slice(1).toLowerCase()}
                            </Typography>

                            <Controller
                                name={`schedules.${index}.opentime`}
                                control={control}
                                render={({field}) => (
                                    <TextField
                                        {...field}
                                        type="time"
                                        label="Opens"
                                        InputLabelProps={{shrink: true}}
                                        sx={{width: 150}}
                                    />
                                )}
                            />

                            <Controller
                                name={`schedules.${index}.closetime`}
                                control={control}
                                render={({field}) => (
                                    <TextField
                                        {...field}
                                        type="time"
                                        label="Closes"
                                        InputLabelProps={{shrink: true}}
                                        sx={{width: 150}}
                                    />
                                )}
                            />
                        </Box>
                    ))}
                </Box>

                <Box sx={RestaurantFormStyles.actions}>
                    <Button
                        variant="outlined"
                        onClick={onCancel}
                        disabled={submitting}
                        sx={{textTransform: 'none'}}
                    >
                        Cancel
                    </Button>
                    <Button
                        type="submit"
                        variant="contained"
                        disabled={submitting}
                        sx={{textTransform: 'none', minWidth: 150}}
                    >
                        {submitting ? <CircularProgress size={24}/> : 'Create Restaurant'}
                    </Button>
                </Box>
            </form>
        </Paper>
    );
}
