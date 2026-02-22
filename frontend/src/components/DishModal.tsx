import {
    Box,
    Button,
    Dialog,
    DialogActions,
    DialogContent,
    DialogTitle,
    IconButton,
    TextField,
    Typography,
} from "@mui/material";
import CloseIcon from "@mui/icons-material/Close";
import {useState} from "react";

interface DishModalProps {
    open: boolean;
    onClose: () => void;
    dish: {
        id: string;
        name: string;
        description: string;
        price: number;
        pictureUrl?: string;
    } | null;
    onAddToBasket: (quantity: number) => void;
}

export function DishModal({open, onClose, dish, onAddToBasket}: DishModalProps) {
    const [quantity, setQuantity] = useState(1);

    if (!dish) return null;

    return (
        <Dialog open={open} onClose={onClose} maxWidth="sm" fullWidth>
            <DialogTitle>
                {dish.name}
                <IconButton
                    aria-label="close"
                    onClick={onClose}
                    sx={{position: "absolute", right: 8, top: 8}}
                >
                    <CloseIcon/>
                </IconButton>
            </DialogTitle>

            <DialogContent dividers>
                <Box sx={{textAlign: "center", mb: 2}}>
                    <img
                        src={dish.pictureUrl || "https://placehold.co/400x200?text=No+Image"}
                        alt={dish.name}
                        style={{maxWidth: "100%", borderRadius: "12px"}}
                    />
                </Box>
                <Typography variant="body1" sx={{mb: 2}}>
                    {dish.description}
                </Typography>
                <Typography variant="h6" sx={{mb: 2}}>
                    Price: €{dish.price.toFixed(2)}
                </Typography>

                <TextField
                    label="Quantity"
                    type="number"
                    value={quantity}
                    onChange={(e) => setQuantity(Math.max(1, parseInt(e.target.value) || 1))}
                    inputProps={{min: 1}}
                    sx={{width: 120}}
                />
            </DialogContent>

            <DialogActions>
                <Button onClick={onClose} color="inherit">
                    Cancel
                </Button>
                <Button
                    onClick={() => {
                        onAddToBasket(quantity);
                        onClose();
                    }}
                    variant="contained"
                >
                    Add to Basket
                </Button>
            </DialogActions>
        </Dialog>
    );
}