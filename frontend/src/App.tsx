import {BrowserRouter, Route, Routes} from 'react-router-dom';
import {QueryClient, QueryClientProvider} from '@tanstack/react-query';
import {ThemeProvider} from '@mui/material/styles';
import CssBaseline from '@mui/material/CssBaseline';
import {MainPage} from './pages/MainPage';
import {OwnerAuthPage} from './pages/Owner/OwnerAuthPage.tsx';
import {OwnerDashboard} from './pages/Owner/OwnerDashboard.tsx';
import {CreateRestaurantPage} from './pages/CreateRestaurantPage';
import {ProtectedRoute} from './components/ProtectedRoute';
import {OwnerRegisterPage} from "./pages/Owner/OwnerRegisterPage.tsx";
import SecurityContextProvider from "./context/SecurityContextProvider.tsx";
import {ManageDishesPage} from "./pages/Restaurant/ManageDishesPage.tsx";
import {EditRestaurantPage} from "./pages/Restaurant/EditRestaurantPage.tsx";
import theme from "./theme.ts";
import {RestaurantDiscoveryPage} from "./pages/Restaurant/RestaurantDiscoveryPage.tsx";
import {RestaurantDetailsPage} from "./pages/Restaurant/RestaurantDetailsPage.tsx";
import {CustomerAuthPage} from "./pages/Customer/CustomerAuthPage.tsx";
import {CustomerRegisterPage} from "./pages/Customer/CustomerRegisterPage.tsx";
import {CheckoutPage} from "./pages/Customer/CheckoutPage.tsx";
import {OrderConfirmationPage} from "./pages/Customer/OrderConfirmationPage.tsx";
import {ManageOrdersPage} from "./pages/Owner/ManageOrdersPage.tsx";

const queryClient = new QueryClient({
    defaultOptions: {
        queries: {
            refetchOnWindowFocus: false,
            retry: 1,
        },
    },
});

export function App() {
    return (
        <QueryClientProvider client={queryClient}>
            <ThemeProvider theme={theme}>
                <CssBaseline/>
                <BrowserRouter>
                    <SecurityContextProvider>
                        <Routes>
                            <Route path="/" element={<MainPage/>}/>
                            <Route path="/owner/auth" element={<OwnerAuthPage/>}/>
                            <Route
                                path="/owner/dashboard"
                                element={
                                    <ProtectedRoute>
                                        <OwnerDashboard/>
                                    </ProtectedRoute>
                                }
                            />
                            <Route
                                path="/owner/create-restaurant"
                                element={
                                    <ProtectedRoute>
                                        <CreateRestaurantPage/>
                                    </ProtectedRoute>
                                }
                            />
                            <Route path="/owner/register" element={<OwnerRegisterPage/>}/>
                            <Route path="/owner/manage-dishes/:restaurantId" element={<ManageDishesPage/>}/>
                            <Route path="/owner/edit-restaurant/:restaurantId" element={<EditRestaurantPage/>}/>
                            <Route path="/restaurants" element={<RestaurantDiscoveryPage/>}/>
                            <Route path="/restaurants/:restaurantId" element={<RestaurantDetailsPage/>}/>
                            <Route path="/customer/auth" element={<CustomerAuthPage/>}/>
                            <Route path="/customer/register" element={<CustomerRegisterPage/>}/>
                            <Route path="/checkout/:customerId" element={<CheckoutPage/>}/>
                            <Route path="/confirmation/:orderId" element={<OrderConfirmationPage/>}/>
                            <Route path="/owner/manage-orders/:restaurantId" element={<ManageOrdersPage/>}/>
                        </Routes>
                    </SecurityContextProvider>
                </BrowserRouter>
            </ThemeProvider>
        </QueryClientProvider>
    );
}

export default App;