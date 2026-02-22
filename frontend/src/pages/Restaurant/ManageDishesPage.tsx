import {useState} from 'react';
import {useNavigate, useParams} from 'react-router-dom';
import {
    AppBar,
    Box,
    Button,
    Card,
    CardContent,
    CardMedia,
    CircularProgress,
    Container,
    Dialog,
    DialogActions,
    DialogContent,
    DialogTitle,
    MenuItem,
    Stack,
    TextField,
    Toolbar,
    Typography,
} from '@mui/material';
import RemoveShoppingCartOutlinedIcon from '@mui/icons-material/RemoveShoppingCartOutlined';
import CheckCircleOutlineIcon from '@mui/icons-material/CheckCircleOutline';
import AddIcon from '@mui/icons-material/Add';
import EditIcon from '@mui/icons-material/Edit';
import PublishIcon from '@mui/icons-material/Publish';
import UnpublishedIcon from '@mui/icons-material/Unpublished';
import StorefrontIcon from '@mui/icons-material/Storefront';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import DeleteOutlineIcon from '@mui/icons-material/DeleteOutline';
import {useDishes} from '../../hooks/UseDish.ts';
import {useDishActions} from '../../hooks/UseDishActions.ts';
import type {Dish} from '../../model/Dish/Dish.ts';
import type {DishType} from '../../model/Dish/DishType.ts';
import {ChipInput} from '../../components/ChipInput.tsx';
import {ManageDishesStyles} from './ManageDishesStyles.ts';

export function ManageDishesPage() {
    const {restaurantId} = useParams<{ restaurantId: string }>();
    const navigate = useNavigate();
    const {data: dishes = [], isLoading, refetch} = useDishes(restaurantId ?? '');
    const [openDialog, setOpenDialog] = useState(false);
    const [editingDish, setEditingDish] = useState<Dish | null>(null);
    const [form, setForm] = useState<Omit<Dish, 'id'>>({
        name: '',
        dishType: 'MAIN',
        description: '',
        price: 0,
        pictureUrl: '',
        foodTags: [],
    });
    const [deleteConfirmOpen, setDeleteConfirmOpen] = useState(false);
    const [dishToDelete, setDishToDelete] = useState<Dish | null>(null);

    const {
        handlePublish,
        handleUnpublish,
        handleOutOfStock,
        handleBackInStock,
        handleAddDish,
        handleEditDish,
        handleApplyPendingDishes,
        handleDeleteDish,
    } = useDishActions(restaurantId ?? '');

    const handleOpen = (dish?: Dish) => {
        if (dish) {
            setEditingDish(dish);
            setForm({
                name: dish.name,
                dishType: dish.dishType,
                description: dish.description,
                price: dish.price,
                pictureUrl: dish.pictureUrl,
                foodTags: dish.foodTags ?? [],
            });
        } else {
            setEditingDish(null);
            setForm({
                name: '',
                dishType: 'MAIN',
                description: '',
                price: 0,
                pictureUrl: '',
                foodTags: [],
            });
        }
        setOpenDialog(true);
    };

    const handleClose = () => setOpenDialog(false);

    const handleSubmit = async () => {
        if (!restaurantId) return;
        if (!form.dishType) {
            alert('Please select a dish type before submitting.');
            return;
        }
        if (editingDish) {
            await handleEditDish(editingDish.id!, form);
        } else {
            await handleAddDish(form);
        }
        handleClose();
        await refetch();
    };

    const handleOpenDeleteDialog = (dish: Dish) => {
        setDishToDelete(dish);
        setDeleteConfirmOpen(true);
    };

    const handleCloseDeleteDialog = () => {
        setDeleteConfirmOpen(false);
        setDishToDelete(null);
    };

    const handleConfirmDelete = async () => {
        if (!dishToDelete || !restaurantId) return;
        await handleDeleteDish(dishToDelete.id!);
        handleCloseDeleteDialog();
        await refetch();
    };

    if (isLoading) {
        return (
            <Container sx={{py: 4, textAlign: 'center'}}>
                <CircularProgress/>
            </Container>
        );
    }

    return (
        <Box sx={ManageDishesStyles.root}>
            <AppBar position="static" color="transparent" elevation={0} sx={ManageDishesStyles.appBar}>
                <Toolbar>
                    <StorefrontIcon sx={ManageDishesStyles.logoIcon}/>
                    <Typography variant="h6" sx={ManageDishesStyles.logoText}>
                        Keep Dishes Going
                    </Typography>
                    <Button
                        variant="outlined"
                        onClick={() => navigate('/owner/dashboard')}
                        sx={{textTransform: 'none', fontWeight: 600}}
                    >
                        Back to Dashboard
                    </Button>
                </Toolbar>
            </AppBar>

            <Box sx={ManageDishesStyles.heroSection}>
                <Container maxWidth="lg">
                    <Box sx={ManageDishesStyles.heroContent}>
                        <Typography variant="h3" fontWeight={700} gutterBottom>
                            Manage Dishes
                        </Typography>
                        <Typography variant="h6" sx={{opacity: 0.9}}>
                            Add, edit, or manage your restaurant’s dishes below.
                        </Typography>

                        <Stack direction="row" spacing={2} justifyContent="center" sx={{mt: 4}}>
                            <Button
                                variant="contained"
                                startIcon={<AddIcon/>}
                                onClick={() => handleOpen()}
                            >
                                Add New Dish
                            </Button>
                            <Button
                                variant="outlined"
                                color="secondary"
                                onClick={handleApplyPendingDishes}
                            >
                                Apply All Pending Dishes
                            </Button>
                            <Button
                                startIcon={<ArrowBackIcon/>}
                                onClick={() => navigate(-1)}
                            >
                                Back
                            </Button>
                        </Stack>
                    </Box>

                    {dishes.length === 0 ? (
                        <Typography align="center" color="white" sx={{mt: 4}}>
                            No dishes yet.
                        </Typography>
                    ) : (
                        <Box sx={ManageDishesStyles.gridContainer}>
                            {dishes.map((dish) => (
                                <Card key={dish.id} sx={{borderRadius: 2, overflow: 'hidden', boxShadow: 3}}>
                                    <CardMedia
                                        component="img"
                                        height="160"
                                        image={dish.pictureUrl?.trim() || '/assets/dish.png'}
                                        alt={dish.name}
                                        onError={(e) => {
                                            e.currentTarget.onerror = null;
                                            e.currentTarget.src = '/assets/dish.png';
                                        }}
                                        sx={{objectFit: 'cover'}}
                                    />

                                    <CardContent>
                                        <Typography variant="h6" fontWeight={600}>
                                            {dish.name}
                                        </Typography>
                                        <Typography color="text.secondary" noWrap>
                                            {dish.description}
                                        </Typography>
                                        <Typography>€{dish.price.toFixed(2)}</Typography>
                                        <Typography variant="body2" color="text.secondary">
                                            {dish.dishType} | {dish.state}
                                        </Typography>
                                        <Stack direction="row" spacing={1} sx={{mt: 1.5, flexWrap: 'wrap'}}>
                                            <Button size="small" startIcon={<EditIcon/>}
                                                    onClick={() => handleOpen(dish)}>
                                                Edit
                                            </Button>

                                            {dish.state === 'PUBLISHED' ? (
                                                <Button
                                                    size="small"
                                                    color="warning"
                                                    startIcon={<UnpublishedIcon/>}
                                                    onClick={() => handleUnpublish(dish.id!)}
                                                >
                                                    Unpublish
                                                </Button>
                                            ) : (
                                                <Button
                                                    size="small"
                                                    color="success"
                                                    startIcon={<PublishIcon/>}
                                                    onClick={() => handlePublish(dish.id!)}
                                                >
                                                    Publish
                                                </Button>
                                            )}

                                            {dish.state === 'OUT_OF_STOCK' ? (
                                                <Button
                                                    size="small"
                                                    color="success"
                                                    startIcon={<CheckCircleOutlineIcon/>}
                                                    onClick={() => handleBackInStock(dish.id!)}
                                                >
                                                    Back In Stock
                                                </Button>
                                            ) : (
                                                dish.state === 'PUBLISHED' && (
                                                    <Button
                                                        size="small"
                                                        color="error"
                                                        startIcon={<RemoveShoppingCartOutlinedIcon/>}
                                                        onClick={() => handleOutOfStock(dish.id!)}
                                                    >
                                                        Out of Stock
                                                    </Button>
                                                )
                                            )}

                                            <Button
                                                size="small"
                                                color="error"
                                                startIcon={<DeleteOutlineIcon/>}
                                                onClick={() => handleOpenDeleteDialog(dish)}
                                            >
                                                Delete
                                            </Button>
                                        </Stack>
                                    </CardContent>
                                </Card>
                            ))}
                        </Box>
                    )}
                </Container>
            </Box>

            <Dialog open={openDialog} onClose={handleClose} fullWidth maxWidth="sm">
                <DialogTitle>{editingDish ? 'Edit Dish' : 'Add New Dish'}</DialogTitle>
                <DialogContent>
                    <Stack spacing={2} sx={{mt: 1}}>
                        <TextField
                            label="Name"
                            value={form.name}
                            onChange={(e) => setForm({...form, name: e.target.value})}
                            fullWidth
                        />
                        <TextField
                            select
                            label="Type"
                            value={form.dishType}
                            onChange={(e) =>
                                setForm({...form, dishType: e.target.value as DishType})
                            }
                            fullWidth
                        >
                            <MenuItem value="STARTER">Starter</MenuItem>
                            <MenuItem value="MAIN">Main</MenuItem>
                            <MenuItem value="DESSERT">Dessert</MenuItem>
                        </TextField>
                        <TextField
                            label="Description"
                            value={form.description}
                            onChange={(e) => setForm({...form, description: e.target.value})}
                            fullWidth
                            multiline
                            rows={2}
                        />
                        <TextField
                            label="Price (€)"
                            type="number"
                            value={form.price}
                            onChange={(e) =>
                                setForm({...form, price: parseFloat(e.target.value)})
                            }
                            fullWidth
                        />
                        <TextField
                            label="Picture URL"
                            value={form.pictureUrl}
                            onChange={(e) => setForm({...form, pictureUrl: e.target.value})}
                            fullWidth
                        />
                        <ChipInput
                            label="Food Tags"
                            placeholder="e.g. spicy, vegan, gluten-free"
                            values={form.foodTags}
                            onChange={(newTags) => setForm({...form, foodTags: newTags})}
                        />
                    </Stack>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose}>Cancel</Button>
                    <Button onClick={handleSubmit} variant="contained">
                        {editingDish ? 'Save Draft' : 'Add Dish'}
                    </Button>
                </DialogActions>
            </Dialog>

            <Dialog open={deleteConfirmOpen} onClose={handleCloseDeleteDialog}>
                <DialogTitle>Confirm Delete</DialogTitle>
                <DialogContent>
                    <Typography>
                        Are you sure you want to delete <strong>{dishToDelete?.name}</strong>? This action cannot be
                        undone.
                    </Typography>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleCloseDeleteDialog}>Cancel</Button>
                    <Button onClick={handleConfirmDelete} color="error" variant="contained">
                        Delete
                    </Button>
                </DialogActions>
            </Dialog>

            <Box sx={ManageDishesStyles.footer}>
                <Typography variant="body2" color="text.secondary">
                    © 2025 Keep Dishes Going - Yiannis Ftiti
                </Typography>
            </Box>
        </Box>
    );
}