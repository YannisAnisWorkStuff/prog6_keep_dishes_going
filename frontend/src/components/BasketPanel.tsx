import {Box, Button, Divider, IconButton, Paper, Stack, TextField, Typography,} from "@mui/material";
import DeleteIcon from "@mui/icons-material/Delete";
import EditIcon from "@mui/icons-material/Edit";
import {useState} from "react";

export function BasketPanel({
                                basket,
                                customerId,
                                onRemoveDish,
                                onUpdateQuantity,
                                total,
                                navigate,
                            }: {
    basket: any;
    customerId?: string;
    onRemoveDish: (dishId: string) => Promise<void>;
    onUpdateQuantity: (dishId: string, quantity: number) => Promise<void>;
    total: number;
    navigate: (url: string) => void;
}) {
    const [editingItemId, setEditingItemId] = useState<string | null>(null);
    const [newQuantity, setNewQuantity] = useState<number>(1);

    if (!basket?.items?.length)
        return (
            <Typography color="text.secondary">Your basket is empty.</Typography>
        );

    return (
        <Paper
            elevation={3}
            sx={{
                flex: 1,
                p: 3,
                borderLeft: {lg: "1px solid #eee"},
                minWidth: {lg: "320px"},
                position: {xs: "relative", lg: "sticky"},
                top: {lg: 0},
                height: {lg: "100vh"},
                display: "flex",
                flexDirection: "column",
            }}
        >
            <Typography variant="h5" fontWeight={700} gutterBottom>
                Your Basket
            </Typography>
            <Divider sx={{mb: 2}}/>

            <Box sx={{flex: 1, overflowY: "auto"}}>
                {basket.items.map((item: any) => (
                    <Box key={item.dishId} sx={{mb: 2}}>
                        <Stack
                            direction="row"
                            justifyContent="space-between"
                            alignItems="center"
                        >
                            <Typography fontWeight={600}>{item.name}</Typography>
                            <Stack direction="row" spacing={0.5}>
                                <IconButton
                                    size="small"
                                    color="error"
                                    onClick={() => onRemoveDish(item.dishId)}
                                >
                                    <DeleteIcon fontSize="small"/>
                                </IconButton>
                                {editingItemId !== item.dishId && (
                                    <IconButton
                                        size="small"
                                        color="primary"
                                        onClick={() => {
                                            setEditingItemId(item.dishId);
                                            setNewQuantity(item.quantity);
                                        }}
                                    >
                                        <EditIcon fontSize="small"/>
                                    </IconButton>
                                )}
                            </Stack>
                        </Stack>

                        {editingItemId === item.dishId ? (
                            <Stack direction="row" spacing={1} alignItems="center" sx={{mt: 1}}>
                                <TextField
                                    type="number"
                                    size="small"
                                    value={newQuantity}
                                    onChange={(e) => {
                                        const val = parseInt(e.target.value, 10);
                                        if (!isNaN(val) && val > 0) setNewQuantity(val);
                                    }}
                                    sx={{width: "80px"}}
                                    inputProps={{min: 1}}
                                />
                                <Button
                                    size="small"
                                    variant="contained"
                                    color="success"
                                    onClick={() => {
                                        if (newQuantity > 0) {
                                            onUpdateQuantity(item.dishId, newQuantity);
                                            setEditingItemId(null);
                                        }
                                    }}
                                >
                                    Save
                                </Button>
                                <Button
                                    size="small"
                                    variant="outlined"
                                    onClick={() => setEditingItemId(null)}
                                >
                                    Cancel
                                </Button>
                            </Stack>
                        ) : (
                            <Typography variant="body2" color="text.secondary">
                                Qty: {item.quantity} × €{item.price ? item.price.toFixed(2) : "-"}
                            </Typography>
                        )}

                        <Typography variant="body1" fontWeight={600}>
                            €{item.price ? (item.price * item.quantity).toFixed(2) : "—"}
                        </Typography>
                        <Divider sx={{my: 1}}/>
                    </Box>
                ))}
            </Box>

            <Box sx={{mt: "auto"}}>
                <Typography variant="h6" fontWeight={700} sx={{mb: 2}}>
                    Total: €{total.toFixed(2)}
                </Typography>
                <Button
                    variant="contained"
                    color="success"
                    fullWidth
                    size="large"
                    // In BasketPanel.tsx
                    onClick={() => {
                        if (customerId && customerId !== 'undefined' && customerId !== 'null') {
                            navigate(`/checkout/${customerId}`);
                        } else {
                            navigate(`/checkout/guest`);
                        }
                    }}
                >
                    Proceed to Checkout
                </Button>

            </Box>
        </Paper>
    );
}