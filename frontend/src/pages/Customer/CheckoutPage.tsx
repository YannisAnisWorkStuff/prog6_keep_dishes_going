import {
    Alert,
    AppBar,
    Box,
    Button,
    Card,
    CardContent,
    CircularProgress,
    Container,
    Divider,
    FormControl,
    FormControlLabel,
    InputLabel,
    MenuItem,
    Paper,
    Radio,
    RadioGroup,
    Select,
    Snackbar,
    Stack,
    TextField,
    Toolbar,
    Typography,
} from "@mui/material";
import StorefrontIcon from "@mui/icons-material/Storefront";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import {useNavigate, useParams} from "react-router-dom";
import {COUNTRIES} from "../../model/Countries";
import {CheckoutStyles as S} from "./CheckoutStyles";
import {useCheckout} from "../../hooks/UseCheckout.ts";

export function CheckoutPage() {
    const {customerId} = useParams<{ customerId: string }>();
    const navigate = useNavigate();
    const checkout = useCheckout(customerId);

    const {
        customer,
        basket,
        customerLoading,
        basketLoading,
        name,
        setName,
        paymentMethod,
        setPaymentMethod,
        address,
        setAddress,
        total,
        loading,
        success,
        error,
        setError,
        setSuccess,
        handleCheckout,
    } = checkout;

    if (basketLoading || customerLoading)
        return (
            <Box sx={{display: "flex", justifyContent: "center", alignItems: "center", height: "100vh"}}>
                <CircularProgress color="primary"/>
            </Box>
        );

    if (!basket || !basket.items?.length)
        return (
            <Container sx={{py: 8, textAlign: "center"}}>
                <Typography variant="h6" color="text.secondary">
                    Your basket is empty.
                </Typography>
            </Container>
        );

    return (
        <Box sx={S.root}>
            <AppBar position="static" color="primary" elevation={1}>
                <Toolbar>
                    <StorefrontIcon sx={S.headerBar}/>
                    <Typography variant="h6" sx={{flexGrow: 1, fontWeight: 600}}>
                        Checkout
                    </Typography>
                    <Button
                        color="inherit"
                        startIcon={<ArrowBackIcon/>}
                        onClick={() => navigate(-1)}
                        sx={{textTransform: "none"}}
                    >
                        Back
                    </Button>
                </Toolbar>
            </AppBar>

            <Container maxWidth="md" sx={S.container}>
                <Typography variant="h4" gutterBottom fontWeight={700}>
                    Confirm Your Order
                </Typography>

                <Stack spacing={3}>
                    <Card sx={S.card}>
                        <CardContent>
                            <Typography variant="h6" fontWeight={600} gutterBottom>
                                Order Summary
                            </Typography>
                            {basket.items.map((item: any) => (
                                <Box key={item.dishId} sx={S.summaryRow}>
                                    <Typography>{item.name}</Typography>
                                    <Typography color="text.secondary">
                                        {item.quantity} × €{item.price.toFixed(2)}
                                    </Typography>
                                </Box>
                            ))}
                            <Divider sx={{my: 2}}/>
                            <Typography variant="h6" fontWeight={700}>
                                Total: €{total.toFixed(2)}
                            </Typography>
                        </CardContent>
                    </Card>

                    <Card sx={S.card}>
                        <CardContent>
                            <Typography variant="h6" fontWeight={600} gutterBottom>
                                Delivery Information
                            </Typography>

                            {customer ? (
                                <>
                                    <Typography variant="body1" sx={{mb: 1}}>
                                        <strong>{name}</strong>
                                    </Typography>
                                    <Typography variant="body2" color="text.secondary" sx={{mb: 3}}>
                                        {address.street} {address.number}, {address.postalCode} {address.city},{" "}
                                        {address.country}
                                    </Typography>
                                    <Typography variant="body2" color="text.secondary" sx={{mb: 1}}>
                                        Payment Method:
                                    </Typography>
                                    <RadioGroup
                                        row
                                        value={paymentMethod}
                                        onChange={(e) => setPaymentMethod(e.target.value)}
                                    >
                                        <FormControlLabel value="card" control={<Radio/>} label="Card"/>
                                        <FormControlLabel value="paypal" control={<Radio/>} label="PayPal"/>
                                        <FormControlLabel value="cash" control={<Radio/>} label="Cash on Delivery"/>
                                    </RadioGroup>
                                </>
                            ) : (
                                <>
                                    <TextField
                                        fullWidth
                                        label="Full Name"
                                        value={name}
                                        onChange={(e) => setName(e.target.value)}
                                        sx={{mb: 2}}
                                    />
                                    <Stack direction={{xs: "column", sm: "row"}} spacing={2}>
                                        <TextField
                                            fullWidth
                                            label="Street"
                                            value={address.street}
                                            onChange={(e) => setAddress({...address, street: e.target.value})}
                                        />
                                        <TextField
                                            fullWidth
                                            label="Number"
                                            value={address.number}
                                            onChange={(e) => setAddress({...address, number: e.target.value})}
                                        />
                                    </Stack>
                                    <Stack direction={{xs: "column", sm: "row"}} spacing={2} sx={{mt: 2}}>
                                        <TextField
                                            fullWidth
                                            label="Postal Code"
                                            value={address.postalCode}
                                            onChange={(e) => setAddress({...address, postalCode: e.target.value})}
                                        />
                                        <TextField
                                            fullWidth
                                            label="City"
                                            value={address.city}
                                            onChange={(e) => setAddress({...address, city: e.target.value})}
                                        />
                                        <FormControl fullWidth>
                                            <InputLabel>Country</InputLabel>
                                            <Select
                                                value={address.country}
                                                label="Country"
                                                onChange={(e) => setAddress({...address, country: e.target.value})}
                                            >
                                                {COUNTRIES.map((c) => (
                                                    <MenuItem key={c} value={c}>
                                                        {c}
                                                    </MenuItem>
                                                ))}
                                            </Select>
                                        </FormControl>
                                    </Stack>
                                </>
                            )}
                        </CardContent>
                    </Card>

                    <Paper sx={S.placeOrderBox}>
                        <Typography variant="h6" fontWeight={600}>
                            Total: €{total.toFixed(2)}
                        </Typography>
                        <Button
                            variant="contained"
                            color="primary"
                            size="large"
                            onClick={handleCheckout}
                            disabled={
                                loading ||
                                !name ||
                                (!customer &&
                                    (!address.street || !address.number || !address.postalCode || !address.city || !address.country))
                            }
                            sx={S.placeOrderButton}
                        >
                            {loading ? "Processing..." : "Place Order"}
                        </Button>
                    </Paper>
                </Stack>
            </Container>
            <Snackbar
                open={!!error}
                autoHideDuration={3000}
                onClose={() => setError(null)}
                anchorOrigin={{vertical: "bottom", horizontal: "center"}}
            >
                <Alert severity="error" onClose={() => setError(null)}>
                    {error}
                </Alert>
            </Snackbar>

            <Snackbar
                open={success}
                autoHideDuration={1500}
                onClose={() => setSuccess(false)}
                anchorOrigin={{vertical: "bottom", horizontal: "center"}}>
                <Alert severity="success">Order placed successfully!</Alert>
            </Snackbar>
        </Box>
    );
}