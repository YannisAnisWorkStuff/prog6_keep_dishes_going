import {Box, Button, Card, CardContent, CircularProgress, Container, Stack, Typography,} from "@mui/material";
import CheckCircleIcon from "@mui/icons-material/CheckCircle";
import HourglassBottomIcon from "@mui/icons-material/HourglassBottom";
import ErrorOutlineIcon from "@mui/icons-material/ErrorOutline";
import PedalBikeIcon from "@mui/icons-material/PedalBike";
import DoneAllIcon from "@mui/icons-material/DoneAll";
import {motion} from "framer-motion";
import {OrderConfirmationStyles as S} from "./OrderConfirmationStyles";
import {useOrderConfirmation} from "../../hooks/UseOrderConfirmation";

export function OrderConfirmationPage() {
    const {order, loading, error, navigate} = useOrderConfirmation();

    const getMessage = () => {
        switch (order?.status) {
            case "PENDING":
                return "We received your order — waiting for the restaurant to confirm...";
            case "ACCEPTED":
                return "Your order has been accepted! The restaurant is preparing it.";
            case "READY_FOR_PICKUP":
                return "Your order is ready for pickup. The courier is on the way!";
            case "DELIVERED":
                return "Your order has been delivered — enjoy your meal!";
            case "REJECTED":
                return "Unfortunately, your order was rejected by the restaurant.";
            default:
                return "Processing your order...";
        }
    };

    const renderIcon = () => {
        switch (order?.status) {
            case "ACCEPTED":
                return (
                    <motion.div
                        initial={{scale: 0, opacity: 0}}
                        animate={{scale: 1, opacity: 1}}
                        transition={{duration: 0.6}}
                    >
                        <CheckCircleIcon sx={S.iconSuccess}/>
                    </motion.div>
                );

            case "READY_FOR_PICKUP":
                return (
                    <motion.div
                        animate={{y: [0, -5, 0]}}
                        transition={{repeat: Infinity, duration: 1.5}}
                    >
                        <PedalBikeIcon sx={S.iconPickup}/>
                    </motion.div>
                );

            case "DELIVERED":
                return (
                    <motion.div
                        initial={{scale: 0.7, opacity: 0}}
                        animate={{scale: 1, opacity: 1}}
                        transition={{duration: 0.7}}
                    >
                        <DoneAllIcon sx={S.iconDelivered}/>
                    </motion.div>
                );

            case "REJECTED":
                return (
                    <motion.div
                        initial={{scale: 0, opacity: 0}}
                        animate={{scale: 1, opacity: 1}}
                        transition={{duration: 0.6}}
                    >
                        <ErrorOutlineIcon sx={S.iconError}/>
                    </motion.div>
                );

            default:
                return (
                    <motion.div
                        animate={{rotate: 360}}
                        transition={{repeat: Infinity, duration: 2, ease: "linear"}}
                    >
                        <HourglassBottomIcon sx={S.iconPending}/>
                    </motion.div>
                );
        }
    };

    if (loading)
        return (
            <Box sx={S.loadingRoot}>
                <motion.div
                    initial={{scale: 0.8, opacity: 0}}
                    animate={{scale: 1, opacity: 1}}
                    transition={{duration: 0.8}}
                >
                    <CircularProgress size={80} sx={{color: "#2E7D32"}}/>
                    <Typography variant="h6" sx={{mt: 3, color: "#2E7D32"}}>
                        We’re confirming your order...
                    </Typography>
                </motion.div>
            </Box>
        );

    if (error)
        return (
            <Box sx={S.loadingRoot}>
                <ErrorOutlineIcon sx={S.iconError}/>
                <Typography color="error" sx={{mt: 2}}>
                    {error}
                </Typography>
                <Button variant="outlined" sx={{mt: 3}} onClick={() => navigate("/")}>
                    Go Home
                </Button>
            </Box>
        );

    return (
        <Box sx={S.root}>
            <motion.div
                initial={{opacity: 0, y: 20}}
                animate={{opacity: 1, y: 0}}
                transition={{duration: 0.6}}
            >
                <Container sx={S.container}>
                    <Card sx={S.card}>
                        <CardContent>
                            <Stack alignItems="center" spacing={2}>
                                {renderIcon()}

                                <Typography variant="h4" fontWeight={700} color="#1B5E20" textAlign="center">
                                    {getMessage()}
                                </Typography>

                                <Typography variant="body1" color="text.secondary">
                                    Order ID: <strong>{order?.id}</strong>
                                </Typography>

                                {order?.status === "ACCEPTED" && (
                                    <Typography sx={{mt: 2, color: "#43A047", fontWeight: 500}}>
                                        The restaurant is preparing your meal 👨‍🍳
                                    </Typography>
                                )}

                                {order?.status === "READY_FOR_PICKUP" && (
                                    <Typography sx={{mt: 2, color: "#0288D1", fontWeight: 500}}>
                                        Courier is on the way to pick up your order 🚴
                                    </Typography>
                                )}

                                {order?.status === "DELIVERED" && (
                                    <Typography sx={{mt: 2, color: "#2E7D32", fontWeight: 500}}>
                                        Your order has been delivered! Enjoy your meal 🍽️
                                    </Typography>
                                )}

                                {order?.status === "REJECTED" && (
                                    <>
                                        <Typography sx={{mt: 2, color: "#D32F2F", fontWeight: 600}}>
                                            Your order was rejected..
                                        </Typography>

                                        {order.rejectionReason && (
                                            <Typography
                                                sx={{
                                                    mt: 1,
                                                    color: "text.secondary",
                                                    fontStyle: "italic",
                                                    textAlign: "center",
                                                }}
                                            >
                                                Reason: {order.rejectionReason}
                                            </Typography>
                                        )}

                                        <Button
                                            variant="outlined"
                                            sx={S.rejectedButton}
                                            onClick={() => navigate("/restaurants")}
                                        >
                                            Browse Other Restaurants
                                        </Button>
                                    </>
                                )}
                            </Stack>
                        </CardContent>
                    </Card>
                </Container>
            </motion.div>
        </Box>
    );
}