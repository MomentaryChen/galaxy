import React, { useRef, useState } from 'react';
import AudioPlayer from 'react-audio-player';
import {
    Button, Container, Grid, Typography, List, ListItem, ListItemIcon, ListItemText, Divider, ListItemButton,
    Card, CardActions, CardContent, Skeleton, Stack, Box, Slider, Input, ButtonGroup, FormControl, InputLabel, Fab, IconButton, Hidden
} from '@mui/material';
import axios from 'axios';
import PhoneIcon from '@mui/icons-material/Phone';
import VolumeDown from '@mui/icons-material/VolumeDown';
import VolumeUp from '@mui/icons-material/VolumeUp';
import { green, grey, red } from '@mui/material/colors';
import { makeStyles } from '@mui/styles';

const useStyles = makeStyles(theme => ({
    circleButton: {
        width: '50px',
        height: '50px',
        borderRadius: '25px 25px 25px 25px',
        backgroundColor: '#ea4b35',
    }
}));

const CallStatus = {
    INITAIL: 'initail',
    PROGRESSING: 'progressing'
}

export default function SoftPhone(props) {
    const classes = useStyles();
    const [volumnValue, setVolumnValue] = React.useState(30);

    const [phoneValue, setPhoneValue] = useState('');
    const [phoneValid, setPhoneValid] = useState(true);

    // call status
    const [callStatus, setCallStatus] = useState(CallStatus.INITAIL);

    const handleSliderChange = (event, newValue) => {
        setVolumnValue(newValue);
    };

    const handleInputChange = (event) => {
        setVolumnValue(event.target.value === '' ? 0 : Number(event.target.value));
    };

    const outBoundList = () => {
        const lists = [];

        for (let i = 0; i <= 9; i++) {
            //記得在JSX中使用JS變數要用花括號包著
            lists.push({
                phone: "090921039" + i
            })
        }

        return <>
            {
                lists.map((obj, idx) => {
                    return <div key={idx}>
                        <ListItemButton onClick={(e) => setPhoneValue(e.target.innerText)}>
                            <ListItem >
                                <ListItemIcon>
                                    <PhoneIcon />
                                </ListItemIcon>
                                <ListItemText
                                    primary={obj.phone}
                                />
                            </ListItem>
                        </ListItemButton>
                        <Divider light />
                    </div>
                })
            }

        </>
    }

    const handleBlur = () => {
        if (volumnValue < 0) {
            setVolumnValue(0);
        } else if (volumnValue > 100) {
            setVolumnValue(100);
        }
    };

    /**
     * Phone Button Click
     */
    const handlePhoneButtonClick = (event) => {
        const inputValue = event.target.innerText;
        const phoneNumberPattern = /^09\d{8}$/; // 這裡假設電話號碼是10位數
        const isValidPhoneNumber = phoneNumberPattern.test(inputValue);
        setPhoneValid(isValidPhoneNumber);

        if (phoneValue.length < 10) {
            setPhoneValue(phoneValue + "" + inputValue);
        }

    }

    const phoneButton = (value) => {
        return <Fab sx={{
            bgcolor: grey[50],
            '&:hover': {
                bgcolor: green[700],
            }
        }}
            aria-label={value}
            onClick={handlePhoneButtonClick}
        >
            {value}
        </Fab>
    }

    /**
     * Phone Input Change
     */
    const handlePhoneInputChange = (event) => {
        console.log(event);
        const inputValue = event.target.value;
        const phoneNumberPattern = /^09\d{8}$/; // 這裡假設電話號碼是10位數
        const isValidPhoneNumber = phoneNumberPattern.test(inputValue);
        setPhoneValid(isValidPhoneNumber);
        if (phoneValue.length < 10) {
            setPhoneValue(inputValue);
        }
    }

    const call = () => {
        setCallStatus(CallStatus.PROGRESSING);
    }

    const reject = () => {
        setCallStatus(CallStatus.INITAIL);
    }


    return <>
        <Container fixed>
            <audio id="remoteAudio"></audio>
            <Grid container spacing={3} sx={{ marginTop: 5 }} >
                <Grid item md={6}>
                    <Box sx={{
                        display: 'flex', alignItems: 'center',
                        justifyContent: 'space-between',
                        mt: -2,
                    }}>
                        <Box sx={{ alignItems: 'center' }}>
                            <Typography variant="h5" component="div">
                                輸入外撥號碼:
                            </Typography>
                        </Box>
                        {callStatus === CallStatus.PROGRESSING && <Skeleton sx={{ bgcolor: green[500] }} variant="circular" width={40} height={40} />}
                    </Box>
                    <Card sx={{ minWidth: 275, marginTop: 1 }}>
                        <CardContent>
                            <Box >
                                <Stack spacing={2} direction="row" sx={{ mb: 1 }} alignItems="center">
                                    <VolumeDown />
                                    <Slider aria-label="Volume" value={volumnValue} onChange={handleSliderChange} />
                                    <VolumeUp />
                                    <Input
                                        value={volumnValue}
                                        size="small"
                                        onChange={handleInputChange}
                                        onBlur={handleBlur}
                                        inputProps={{
                                            step: 10,
                                            min: 0,
                                            max: 100,
                                            type: 'number',
                                            'aria-labelledby': 'input-slider',
                                        }}
                                    />
                                </Stack>
                            </Box>
                            <FormControl fullWidth sx={{ m: 1 }} variant="standard">
                                <InputLabel htmlFor="phone">電話</InputLabel>
                                <Input
                                    id="phone"
                                    placeholder='09********'
                                    startAdornment={<PhoneIcon position="start"></PhoneIcon>}
                                    onChange={handlePhoneInputChange}
                                    error={!phoneValid}
                                    value={phoneValue}
                                />
                            </FormControl>
                            <Box sx={{ width: '100%', marginTop: 5 }} >
                                <Stack spacing={5} direction="row" sx={{
                                    mb: 5, display: 'flex',
                                    alignItems: 'center',
                                    justifyContent: 'center',

                                }}>
                                    {phoneButton(1)}
                                    {phoneButton(2)}
                                    {phoneButton(3)}
                                </Stack>
                                <Stack spacing={5} direction="row" sx={{
                                    mb: 5, display: 'flex',
                                    alignItems: 'center',
                                    justifyContent: 'center',
                                    mt: -1,
                                }}>
                                    {phoneButton(4)}
                                    {phoneButton(5)}
                                    {phoneButton(6)}
                                </Stack>
                                <Stack spacing={5} direction="row" sx={{
                                    mb: 5, display: 'flex',
                                    alignItems: 'center',
                                    justifyContent: 'center',
                                    mt: -1,
                                }}>
                                    {phoneButton(4)}
                                    {phoneButton(5)}
                                    {phoneButton(6)}
                                </Stack>
                            </Box>
                            <Stack spacing={5} direction="row" sx={{
                                mb: 5, display: 'flex',
                                alignItems: 'center',
                                justifyContent: 'center',
                                mt: -1,
                            }}>
                                <Button variant="contained" sx={{
                                    backgroundColor: green[500], '&:hover': {
                                        backgroundColor: green[700],
                                    },
                                }}
                                    onClick={call}><PhoneIcon ></PhoneIcon></Button>
                                <Button variant="contained" sx={{
                                    backgroundColor: red[500], '&:hover': {
                                        backgroundColor: red[700],
                                    },
                                }}
                                    onClick={reject}
                                ><PhoneIcon></PhoneIcon></Button>
                            </Stack>
                        </CardContent>
                    </Card>
                </Grid>
                <Grid item md={6}>
                    <Typography sx={{ mt: 4, mb: 2 }} variant="h6" component="div">
                        外撥名單
                    </Typography>
                    <List dense={true}>
                        {outBoundList()}
                    </List>
                </Grid>
            </Grid>

        </Container >

    </>
}

