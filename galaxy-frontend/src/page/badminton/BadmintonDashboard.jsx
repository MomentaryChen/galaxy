import React, { useRef, useState, useEffect, useContext } from "react";
import CssBaseline from "@mui/material/CssBaseline";
import {
    Box,
    Container,
    Grid,
    Paper,
    Select,
    MenuItem,
    Divider,
    Button,
    Typography,
} from "@mui/material";
import { green, grey } from "@mui/material/colors";

import Court from "./Court";
import { styled } from "@mui/material/styles";
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";
import { Draggable, DragDropContext, Droppable } from "react-beautiful-dnd";
import BadmintonRegister from "./BadmintonRegister";
import WatingArea from "./WatingArea";

import UserContext from "../../UserContext";

function renderCourtSection(name) {
    return (
        <>
            <Paper
                variant="outlined"
                square
                sx={{
                    backgroundColor: green[500],
                    border: "3px solid white",
                    padding: "10px",
                    margin: "5px",
                    textAlign: "center",
                    width: 120,
                    height: 120,
                }}
            >
                {name}
            </Paper>
        </>
    );
}

const Item = styled(Paper)(({ theme }) => ({
    backgroundColor: green[500],
    ...theme.typography.body2,
    padding: theme.spacing(1),
    textAlign: "center",
    color: theme.palette.text.secondary,
}));

export default function BadmintonDashboard() {
    const initRef = useRef(false);
    const stomp = useRef(null);
    const [connected, setConnected] = useState(false);

    const [teams, setTeams] = useState([]);
    const [selectedTeams, setSelectedTeams] = useState(null);

    const [levels, setLevels] = useState([]);

    const userContext = useContext(UserContext);

    const connectSockJS = () => {
        const socket = new SockJS("http://localhost:8080/ws");
        stomp.current = Stomp.over(socket);
        stomp.current.connect(
            {
                GALAXY_API_TOKEN: userContext.userToken,
                Authorization: `Bearer ${userContext.userToken}`,
            },
            connectSockJSCallBack,
            connectSockJSErrorCallback
        );
        window.stomp = stomp.current;
        stomp.current.debug = (log) => {
            // if (JSON.stringify(log).includes("SUBSCRIBE")) {
            //     return;
            // }
            // window.console.log(log);
        };
    };

    const connectSockJSCallBack = (frame) => {
        console.log("CallBack");
        setConnected(true);

        stomp.current.subscribe("/topic/greetings", (message) => {
            console.log(message);
        });

        stomp.current.subscribe("/topic/levels", (message) => {
            const res = JSON.parse(message.body);
            setLevels(res.body.data);
        });

        stomp.current.subscribe("/topic/teams", (message) => {
            const res = JSON.parse(message.body);
            console.log(res.body.data);
            setTeams(res.body.data);
        });

        stomp.current.subscribe("/user/topic/waitingPlayers", (message) => {
            console.error(message);
            const res = JSON.parse(message.body);
            setWaitingPalyers(res.body.data);
            console.log(res)
        });
    };

    const connectSockJSErrorCallback = (error) => {
        console.log(error);
        connectSockJS();
    };

    useEffect(() => {
        if (initRef.current === true) return;
        if (stomp.current === null || !stomp.current.connected) {
            connectSockJS();
            initRef.current = true;
        }
        return () => {
            if (stomp.current !== null) {
                stomp.current.disconnect();
            }
        };
    }, []);

    useEffect(() => {
        if (stomp.current !== null && stomp.current.connected) {
            console.log("Geting Levels");
            // get all levels
            stomp.current.send("/app/levels", JSON.stringify({}), {});

            stomp.current.send("/app/teams", JSON.stringify({}), {});
        }
    }, [connected]);

    const refresh = () => {
        stomp.current.send("/app/teams", JSON.stringify({}), {});
    };

    const [testref, settestref] = useState(0);

    const [court, setCourt] = useState([null, null, null, null]);
    const [waitingPalyers, setWaitingPalyers] = useState([]);

    const handleDragStart = (e, player) => {
        e.dataTransfer.setData("player", JSON.stringify(player));
    };

    const handleDragOver = (e, index) => {
        e.preventDefault();
    };

    const handleDrop = (e, index) => {
        e.preventDefault();
        const player = JSON.parse(e.dataTransfer.getData("player"));
        const newCourt = [...court];
        newCourt[index] = player;
        setCourt(newCourt);
    };

    const renderCourt = () => {
        if (!selectedTeams) {
            return;
        }
        return selectedTeams?.courts.map((court) => {
            return (
                <Grid item key={court.id} xs={12} sm={6} md={4} lg={3}>
                    <Box
                        display="flex"
                        flexDirection="row"
                        alignItems="center"
                        justifyContent={"center"}
                        sx={{ m: 3 }}
                    >
                        <Court
                            players={court.players ? court.players : []}
                            courtName={court.name}
                            sx={{ margin: 0 }}
                        ></Court>
                    </Box>
                </Grid>
            );
        });
    };

    return (
        <React.Fragment>
            {teams && (
                <>
                    <>
                        <Select
                            id="court-cnt-select"
                            value={selectedTeams}
                            name="courtCnt"
                            onChange={(e) => {
                                setSelectedTeams(e.target.value);
                                console.log(e.target.value);
                                console.log(userContext.userToken);
                                stomp.current.send(
                                    "/app/selectedTeam",
                                    JSON.stringify({
                                        teamId: e.target.value.id,
                                    }),
                                    {
                                        GALAXY_API_TOKEN: userContext.userToken,
                                    }
                                );
                            }}
                        >
                            {teams.map((value) => (
                                <MenuItem key={value.id} value={value}>
                                    {value.name}
                                </MenuItem>
                            ))}
                        </Select>
                        <Button onClick={refresh}>Refresh</Button>
                    </>
                </>
            )}
            {selectedTeams && (
                <Grid container>
                    <Grid item xs={12} sx={{ textAlign: "center" }}>
                        <Typography variant="h2">
                            {selectedTeams.name}
                        </Typography>
                    </Grid>
                    <Grid item xs={12}>
                        <CssBaseline />
                        <Container sx={{ width: "95%", marginTop: 2 }}>
                            <DragDropContext
                                onDragEnd={(result) => {
                                    console.log(result);
                                    const { source, destination, draggableId } =
                                        result;
                                    if (!destination) {
                                        return;
                                    }

                                    let arr = Array.from(waitingPalyers);
                                    const [remove] = arr.splice(
                                        source.index,
                                        1
                                    );
                                    arr.splice(destination.index, 0, remove);
                                    setWaitingPalyers(arr);
                                }}
                            >
                                {/* <Typography variant="h3" noWrap>
                                    Badminon
                                </Typography> */}
                                <Grid
                                    container
                                    spacing={2}
                                    sx={{ minHeight: "80vh" }}
                                >
                                    {renderCourt()}
                                </Grid>
                            </DragDropContext>

                            <WatingArea players={waitingPalyers}></WatingArea>
                        </Container>
                    </Grid>
                    <BadmintonRegister levels={levels}></BadmintonRegister>
                </Grid>
            )}
        </React.Fragment>
    );
}
