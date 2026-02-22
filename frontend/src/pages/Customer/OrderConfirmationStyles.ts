export const OrderConfirmationStyles = {
    root: {
        minHeight: "100vh",
        backgroundColor: "#F1F8E9",
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        px: 2,
    },
    container: {
        textAlign: "center",
    },
    card: {
        maxWidth: 600,
        mx: "auto",
        p: 5,
        borderRadius: 3,
        boxShadow: "0 6px 20px rgba(0,0,0,0.1)",
        backgroundColor: "white",
    },
    loadingRoot: {
        height: "100vh",
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
        justifyContent: "center",
        backgroundColor: "#E8F5E9",
    },
    iconSuccess: {
        fontSize: 90,
        color: "#2E7D32",
    },
    iconPending: {
        fontSize: 80,
        color: "#66BB6A",
    },
    iconPickup: {
        fontSize: 85,
        color: "#0288D1",
    },
    iconDelivered: {
        fontSize: 90,
        color: "#43A047",
    },
    iconError: {
        fontSize: 90,
        color: "#D32F2F",
    },
    rejectedButton: {
        mt: 3,
        color: "#D32F2F",
        borderColor: "#D32F2F",
        textTransform: "none",
        fontWeight: 600,
        "&:hover": {
            borderColor: "#B71C1C",
            backgroundColor: "#FFEBEE",
        },
    },
};