import {
    Alert,
    AppBar,
    Box,
    Button,
    Card,
    CardContent,
    Chip,
    CircularProgress,
    Container,
    Dialog,
    DialogActions,
    DialogContent,
    DialogTitle,
    Snackbar,
    Stack,
    Tab,
    Tabs,
    TextField,
    Toolbar,
    Typography,
} from "@mui/material";
import StorefrontIcon from "@mui/icons-material/Storefront";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import {useState} from "react";
import {useManageOrders} from "../../hooks/UseManageOrders";
import {ManageOrdersStyles as S} from "./ManageOrdersStyles";

export function ManageOrdersPage() {
    const {
        pendingOrders,
        acceptedOrders,
        activeTab,
        setActiveTab,
        loading,
        error,
        success,
        setError,
        setSuccess,
        handleAccept,
        handleReject,
        handleMarkReady,
        navigate,
    } = useManageOrders();

    const [openRejectModal, setOpenRejectModal] = useState(false);
    const [rejectReason, setRejectReason] = useState("");
    const [selectedOrderId, setSelectedOrderId] = useState<string | null>(null);

    const openRejectDialog = (orderId: string) => {
        setSelectedOrderId(orderId);
        setRejectReason("");
        setOpenRejectModal(true);
    };

    const closeRejectDialog = () => {
        setOpenRejectModal(false);
        setSelectedOrderId(null);
    };

    const confirmReject = () => {
        if (!rejectReason.trim()) {
            setError("Please provide a reason for rejection.");
            return;
        }
        if (selectedOrderId) {
            handleReject(selectedOrderId, rejectReason);
        }
        closeRejectDialog();
    };

    const getStatusChip = (status: string) => {
        switch (status) {
            case "ACCEPTED":
                return <Chip label="Accepted" color="success" sx={S.chip}/>;
            case "READY_FOR_PICKUP":
                return <Chip label="Ready for Pickup" color="info" sx={S.chip}/>;
            case "REJECTED":
                return <Chip label="Rejected" color="error" sx={S.chip}/>;
            default:
                return <Chip label="Pending" color="warning" sx={S.chip}/>;
        }
    };

    const renderOrders = (orders: any[], isPendingTab: boolean) => {
        if (orders.length === 0) {
            return (
                <Typography color="text.secondary">
                    {isPendingTab
                        ? "No pending orders right now."
                        : "No accepted orders waiting to be picked up."}
                </Typography>
            );
        }

        return (
            <Stack spacing={3}>
                {orders.map((order) => (
                    <Card key={order.orderId} sx={S.orderCard}>
                        <CardContent>
                            <Stack
                                direction="row"
                                justifyContent="space-between"
                                alignItems="center"
                                sx={{mb: 1}}
                            >
                                <Typography variant="h6" fontWeight={600}>
                                    Order #{order.orderId}
                                </Typography>
                                {getStatusChip(order.status)}
                            </Stack>

                            <Typography color="text.secondary">
                                Customer ID: {order.customerId}
                            </Typography>
                            <Typography color="text.secondary">
                                Created: {new Date(order.createdAt).toLocaleString()}
                            </Typography>
                            <Typography variant="body1" sx={{mt: 1}}>
                                Total: €{order.total?.toFixed(2)}
                            </Typography>

                            <Stack direction="row" spacing={2} sx={{mt: 2}}>
                                {isPendingTab ? (
                                    <>
                                        <Button
                                            variant="contained"
                                            sx={S.buttonAccept}
                                            onClick={() => handleAccept(order.orderId)}
                                        >
                                            Accept
                                        </Button>
                                        <Button
                                            variant="outlined"
                                            sx={S.buttonReject}
                                            onClick={() => openRejectDialog(order.orderId)}
                                        >
                                            Reject
                                        </Button>
                                    </>
                                ) : (
                                    <Button
                                        variant="contained"
                                        sx={S.buttonReady}
                                        onClick={() => handleMarkReady(order.orderId)}
                                    >
                                        Mark as Ready
                                    </Button>
                                )}
                            </Stack>
                        </CardContent>
                    </Card>
                ))}
            </Stack>
        );
    };

    if (loading)
        return (
            <Container sx={{py: 10, textAlign: "center"}}>
                <CircularProgress sx={{color: "#2E7D32"}}/>
                <Typography variant="h6" sx={{mt: 2, color: "#2E7D32"}}>
                    Loading orders...
                </Typography>
            </Container>
        );

    return (
        <Box sx={S.root}>
            <AppBar position="static" sx={S.headerBar} elevation={2}>
                <Toolbar>
                    <StorefrontIcon sx={{mr: 2}}/>
                    <Typography variant="h6" sx={{flexGrow: 1, fontWeight: 600}}>
                        Manage Orders
                    </Typography>
                    <Button
                        color="inherit"
                        startIcon={<ArrowBackIcon/>}
                        sx={{textTransform: "none"}}
                        onClick={() => navigate(-1)}
                    >
                        Back
                    </Button>
                </Toolbar>
            </AppBar>

            <Container sx={S.container}>
                <Tabs
                    value={activeTab}
                    onChange={(_e, v) => setActiveTab(v)}
                    textColor="primary"
                    indicatorColor="primary"
                    sx={{mb: 3}}
                >
                    <Tab label="Pending Orders" value="pending"/>
                    <Tab label="Accepted Orders" value="accepted"/>
                </Tabs>

                {activeTab === "pending"
                    ? renderOrders(pendingOrders, true)
                    : renderOrders(acceptedOrders, false)}
            </Container>

            <Dialog open={openRejectModal} onClose={closeRejectDialog} maxWidth="xs" fullWidth>
                <DialogTitle>Reject Order</DialogTitle>
                <DialogContent>
                    <Typography variant="body2" sx={{mb: 1}}>
                        Please provide a reason for rejecting this order.
                    </Typography>
                    <TextField
                        fullWidth
                        multiline
                        minRows={2}
                        value={rejectReason}
                        onChange={(e) => setRejectReason(e.target.value)}
                        placeholder="Type reason here..."
                    />
                </DialogContent>
                <DialogActions>
                    <Button onClick={closeRejectDialog}>Cancel</Button>
                    <Button
                        variant="contained"
                        color="error"
                        onClick={confirmReject}
                        disabled={!rejectReason.trim()}
                    >
                        Confirm Reject
                    </Button>
                </DialogActions>
            </Dialog>

            <Snackbar
                open={!!error}
                autoHideDuration={3000}
                onClose={() => setError(null)}
                anchorOrigin={{vertical: "bottom", horizontal: "center"}}
            >
                <Alert severity="error">{error}</Alert>
            </Snackbar>

            <Snackbar
                open={!!success}
                autoHideDuration={2000}
                onClose={() => setSuccess(null)}
                anchorOrigin={{vertical: "bottom", horizontal: "center"}}
            >
                <Alert severity="success">{success}</Alert>
            </Snackbar>
        </Box>
    );
}