import {
    FormControl, Grid, TextField, List, ListItem, ListItemButton, Paper, Button,
    Box, Select, MenuItem, ButtonGroup, TableContainer, Table, TableHead, TableRow, TableCell, TableBody,
    Alert, Divider, Fade, Typography, useMediaQuery
} from '@mui/material';
// import { DataGrid } from '@mui/x-data-grid';
import React, { useEffect, useState, useMemo, useCallback } from 'react';
import { useCookies } from "react-cookie";
import GalaxyDialog from '../../compoment/GalaxyDialog';
import { orange, blue, pink, yellow, grey } from '@mui/material/colors';
import { makeStyles, useTheme } from '@mui/styles';
import CheckIcon from '@mui/icons-material/Check';
import DeleteIcon from '@mui/icons-material/Delete';

const useStyles = makeStyles(theme => ({
    lable: {
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        background: orange[100],

        // css製作建立長方形，邊角圓弧
        border: '3px solid #f8f9fa',
        borderRadius: '50%',
    },

    highLevelText: {
        color: blue[600],
    },

    mediumLevelText: {
        color: yellow[600],
    },

    lowLevelText: {
        color: pink[600],
    }
}));

export default function BadmintonRegister() {
    const [cookies, setCookie, removeCookie] = useCookies(['badminton_id']);
    const classes = useStyles();
    const [textPlayerName, setTextPlayerName] = useState("");

    const theme = useTheme();
    const isSmallScreen = useMediaQuery(theme.breakpoints.up("sm"));

    const [playerHelperText, setPlayerHelperText] = useState({
        success: "",
        error: ""
    });


    useEffect(() => {
        if (!cookies.badminton_id) {
            setShowDialog(true);
        }
    }, []);

    const [form, setForm] = useState({
        badmintonName: "",
        courtCnt: 4,
        level: "High",
        players: [
            { id: 1, name: 'Snow', level: 'High', age: 14 },
            { id: 2, name: 'Lannister', level: 'Low', age: 31 },
            { id: 3, name: 'Lannister', level: 'High', age: 31 },
            { id: 4, name: 'Stark', level: 'High', age: 11 },
            { id: 5, name: 'Targaryen', level: 'Medium', age: null },
        ]
    })

    const renderPlayers = () => {

        return (<>
            <Box sx={{ height: 500, width: '100%' }}>
                <TableContainer component={Paper} >
                    <Table sx={{ minWidth: 200 }} size="small" aria-label="the players table">
                        <TableHead>
                            <TableRow>
                                <TableCell><Typography variant="h6" component={'span'} >Name ({form.players.length})</Typography></TableCell>
                                <TableCell ><Typography variant="h6" component={'span'}>Level</Typography></TableCell>
                                <TableCell align="right"><Typography variant="h6" component={'span'}>Action</Typography></TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {form.players.map((row) => (
                                <TableRow
                                    key={row.id}
                                    sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                                >
                                    <TableCell component="th" scope="row">
                                        {row.name}
                                    </TableCell>
                                    <TableCell ><div className={classes[`${row.level.toLowerCase()}LevelText`]}>{row.level}</div></TableCell>
                                    {
                                        isSmallScreen ? <TableCell align="right"><Button value={row.id} onClick={removePlayers} variant="outlined"><DeleteIcon /> Remove</Button></TableCell>
                                            : <TableCell align="right"><Button value={row.id} onClick={removePlayers} variant="outlined"><DeleteIcon /></Button></TableCell>
                                    }

                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
                </TableContainer>
            </Box>


        </>)

    }

    const disappearHelperText = () => {
        setTimeout(() => {
            setPlayerHelperText({
                success: "",
                error: ""
            });
        }, 3000);
    };

    const addPlayers = () => {
        if (textPlayerName === "") {
            setPlayerHelperText(pre => ({ ...pre, error: `請輸入名稱` }));
            return;
        };
        const players = form.players;
        function isNameExists(name) {
            return players.find(obj => obj.name === name) !== undefined;
        }

        if (!isNameExists(textPlayerName)) {
            let player = {
                id: players.length === 0 ? 1 : players[players.length - 1].id + 1,
                name: textPlayerName, level: form.level, age: 14
            };
            setForm(pre => ({ ...pre, players: [...pre.players, player] }))
            setPlayerHelperText(pre => ({ ...pre, success: `新增成員成功` }));
            setTextPlayerName("");
            disappearHelperText();
        } else {
            setPlayerHelperText(pre => ({ ...pre, error: `新增成員失敗` }));
        }
    }

    const removePlayers = (e, newValue) => {
        const rmId = e.target.value;
        console.log(rmId)
        let indexToRemove = form.players.findIndex((p) => p.id === parseInt(rmId));

        if (indexToRemove >= 0 && indexToRemove < form.players.length) {
            const tmp = [...form.players];
            tmp.splice(indexToRemove, 1);
            setForm(pre => ({ ...pre, players: tmp }));
        }
    }


    const updateFormByName = (event) => {
        setForm(pre => ({ ...pre, [event.target.name]: event.target.value }))
    }

    const renderRegisterForm = useCallback(() => {
        return <>
            <Grid container item>
                {/* Baminton Name */}
                <Grid item
                    sm={2}
                    xs={4}
                    sx={{ marginTop: 1 }}
                    className={classes.lable}>

                    羽球隊名稱
                </Grid>
                <Grid
                    item
                    sx={{ marginTop: 1 }}
                    sm={4}
                    xs={8}
                >
                    <FormControl fullWidth>
                        <TextField value={form.badmintonName} name='badmintonName' onChange={updateFormByName} />
                    </FormControl>
                </Grid>
                {/* Court Count */}
                <Grid item
                    sm={2}
                    xs={4}
                    sx={{ marginTop: 1 }}
                    className={classes.lable}>
                    場地數量
                </Grid>
                <Grid
                    item
                    sx={{ marginTop: 1 }}
                    sm={4}
                    xs={8}
                >
                    <FormControl fullWidth>
                        <Select
                            id="court-cnt-select"
                            value={form.courtCnt}
                            name='courtCnt'
                            onChange={updateFormByName}
                        >
                            {
                                Array.from({ length: 10 }, (_, index) => index + 1).map((value) =>
                                    <MenuItem key={value} value={value}>{value}</MenuItem>
                                )
                            }
                        </Select>
                    </FormControl>

                </Grid>

                <Grid item
                    sm={2}
                    xs={4}
                    sx={{ marginTop: 1 }}
                    className={classes.lable}>
                    強度
                </Grid>
                <Grid
                    item
                    sx={{ marginTop: 1 }}
                    sm={2}
                    xs={8}
                >
                    <FormControl fullWidth>
                        <Select
                            id="court-cnt-select"
                            value={form.level}
                            name='level'
                            onChange={updateFormByName}
                        >
                            {
                                ['High', 'Medium', 'Low'].map((value) =>
                                    <MenuItem key={value} value={value}>{value}</MenuItem>
                                )
                            }
                        </Select>
                    </FormControl>
                </Grid>
                {/* Player Name */}
                <Grid item
                    sm={1}
                    xs={4}
                    sx={{ marginTop: 1 }}
                    className={classes.lable}>
                    成員
                </Grid>
                <Grid
                    item
                    sm={7}
                    xs={8}
                    sx={{ marginTop: 1 }}
                >
                    <FormControl fullWidth>
                        <TextField
                            value={textPlayerName}
                            onChange={(e) => {
                                setTextPlayerName(e.target.value);
                            }}
                        />
                    </FormControl>
                </Grid>
                <Grid
                    container
                    direction="row"
                    justifyContent="flex-end"
                    // alignItems="center"
                    sx={{ marginTop: 1 }}
                >
                    {playerHelperText.success && <Alert icon={<CheckIcon fontSize="inherit" />} severity="success">
                        {playerHelperText.success}
                    </Alert>}
                    {playerHelperText.error && <Alert icon={<CheckIcon fontSize="inherit" />} severity="error">
                        {playerHelperText.error}
                    </Alert>}
                    {/* <Button variant='outlined' sx={{ m: 1 }} onClick={() => { }}>Clear</Button> */}

                    <Button variant='outlined' sx={{ m: 1 }} onClick={addPlayers}>Add</Button>

                </Grid>

                <Grid
                    item
                    xs={12}
                    sx={{ marginTop: 1 }}
                >
                    {renderPlayers()}

                </Grid>
            </Grid>

        </>
    }, [form, textPlayerName, playerHelperText, isSmallScreen])



    const [showDialog, setShowDialog] = useState(false);
    const [dialogAttr, setDialogAttr] = useState({
        title: "請輸入以下資訊",
        message: <>{renderRegisterForm()}</>,
        actionLabel: "確認",
        actionHandler: () => {
            setShowDialog(false)
        },
        secondaryActionLabel: "清除",
        secondaryActionHandler: () => { },
        closeHandler: () => { },
    });

    useEffect(() => {
        setDialogAttr(pre => ({ ...pre, message: <>{renderRegisterForm()}</> }));
    }, [form, renderRegisterForm])

    return (<>
        {cookies.badminton_id}
        <GalaxyDialog showDialog={showDialog}
            setShowDialog={setShowDialog}
            title={dialogAttr.title}
            message={dialogAttr.message}
            actionLabel={dialogAttr.actionLabel}
            actionHandler={() => dialogAttr.actionHandler()}
            closeHandler={() => dialogAttr.closeHandler()}
            secondaryActionLabel={dialogAttr.secondaryActionLabel}
            secondaryActionHandler={() => dialogAttr.secondaryActionHandler()}></GalaxyDialog>
    </>)

}