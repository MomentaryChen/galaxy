import React, { useRef, useState } from 'react';
import AudioPlayer from 'react-audio-player';
import { Button, Box } from '@mui/material';
import axios from 'axios';

import TextField from '@mui/material/TextField';
import Stack from '@mui/material/Stack';
import Autocomplete from '@mui/material/Autocomplete';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemText from '@mui/material/ListItemText';
import ListSubheader from '@mui/material/ListSubheader';


const CosAutoComplete = (props) => {
    const [autoList, setAutoList] = useState([]);

    const handleClick = () => {
        setAutoList(top100Films);
    }

    const clearAutoList = () => {
        if (autoList.length !== 0)
            setAutoList([]);
    }

    return (
        <>
            <Box
                display='flex'
                alignItems='center'
                flexDirection='column'
                pt={3}
            >

                <Stack spacing={2} flexDirection='row' sx={{ width: 700 }}>
                    <Autocomplete
                        freeSolo
                        id="free-solo-2-demo"
                        disableClearable
                        options={autoList.map((option) => option.title)}
                        fullWidth
                        renderInput={(params) => (
                            <>
                                <TextField
                                    {...params}
                                    label="Search input"
                                    onChange={clearAutoList}
                                    InputProps={{
                                        ...params.InputProps,
                                        type: 'search',
                                        endAdornment: (
                                            <Button onClick={handleClick}>Search</Button>
                                        ),
                                    }}
                                />
                            </>
                        )}
                    />

                </Stack>
            </Box>

        </>
    )
}

const top100Films = [
    { title: 'Forrest Gump', year: 1994 },
    { title: 'Forrest Gump 2', year: 1998 },
    { title: 'Inception', year: 2010 },
    {
        title: 'The Lord of the Rings: The Two Towers',
        year: 2002,
    },
];

export default CosAutoComplete;