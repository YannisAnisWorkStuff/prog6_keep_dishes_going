export const ManageOrdersStyles = {
    root: {
        backgroundColor: "#fafafa",
        minHeight: "100vh",
    },
    headerBar: {
        backgroundColor: "#2E7D32",
        color: "white",
    },
    container: {
        py: 4,
    },
    orderCard: {
        p: 1,
        borderRadius: 2,
        boxShadow: 3,
    },
    chip: {
        fontWeight: 600,
    },
    buttonAccept: {
        backgroundColor: "#2E7D32",
        "&:hover": {backgroundColor: "#1B5E20"},
    },
    buttonReject: {
        color: "#C62828",
        borderColor: "#C62828",
        "&:hover": {backgroundColor: "#FFEBEE"},
    },
    buttonReady: {
        backgroundColor: "#0288D1",
        "&:hover": {backgroundColor: "#0277BD"},
    },
};
