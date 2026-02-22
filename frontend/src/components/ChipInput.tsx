import {useState} from 'react';
import {Box, Chip, IconButton, TextField} from '@mui/material';
import AddIcon from '@mui/icons-material/Add';
import type {SxProps, Theme} from '@mui/material/styles';

interface ChipInputProps {
    label?: string;
    values: string[];
    onChange: (newValues: string[]) => void;
    placeholder?: string;
    sx?: SxProps<Theme>;
}

export function ChipInput({label, values, onChange, placeholder, sx}: ChipInputProps) {
    const [input, setInput] = useState('');

    const handleAdd = () => {
        const trimmed = input.trim();
        if (trimmed && !values.includes(trimmed)) {
            onChange([...values, trimmed]);
            setInput('');
        }
    };

    const handleDelete = (val: string) => {
        onChange(values.filter((v) => v !== val));
    };

    const handleKeyDown = (e: React.KeyboardEvent<HTMLInputElement>) => {
        if (e.key === 'Enter') {
            e.preventDefault();
            handleAdd();
        }
    };

    return (
        <Box sx={{display: 'flex', flexDirection: 'column', gap: 1, ...sx}}>
            <Box sx={{display: 'flex', alignItems: 'center', gap: 1}}>
                <TextField
                    label={label}
                    placeholder={placeholder}
                    variant="outlined"
                    value={input}
                    onChange={(e) => setInput(e.target.value)}
                    onKeyDown={handleKeyDown}
                    fullWidth
                />
                <IconButton color="primary" onClick={handleAdd} disabled={!input.trim()}>
                    <AddIcon/>
                </IconButton>
            </Box>

            <Box sx={{display: 'flex', flexWrap: 'wrap', gap: 1}}>
                {values.map((val) => (
                    <Chip
                        key={val}
                        label={val}
                        onDelete={() => handleDelete(val)}
                        sx={{bgcolor: '#f0f0f0', borderRadius: 1}}
                    />
                ))}
            </Box>
        </Box>
    );
}