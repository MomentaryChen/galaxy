import React, { useRef, useState, useEffect } from 'react';
import CssBaseline from '@mui/material/CssBaseline';
import { Box, Container, Grid, Paper, Stack, Divider, Button, Typography } from '@mui/material';
import { green, grey } from '@mui/material/colors';

import Court from './Court';
import { styled } from '@mui/material/styles';
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";
import { Draggable, DragDropContext, Droppable } from "react-beautiful-dnd";
import BadmintonRegister from './BadmintonRegister';

function renderCourtSection(name) {
  return (<>
    <Paper variant="outlined" square sx={{
      backgroundColor: green[500],
      border: "3px solid white",
      padding: "10px",
      margin: "5px",
      textAlign: "center",
      width: 120,
      height: 120,
    }}>{name}</Paper >
  </>)
}

const Item = styled(Paper)(({ theme }) => ({
  backgroundColor: green[500],
  ...theme.typography.body2,
  padding: theme.spacing(1),
  textAlign: 'center',
  color: theme.palette.text.secondary,
}));



export default function BadmintonDashboard() {

  const initRef = useRef(false);
  const stomp = useRef(null);
  const [connected, setConnected] = useState(false);

  const [courtCnt, setCourtCnt] = useState(8);

  const [levels, setLevels] = useState([]);

  const connectSockJS = () => {
    const socket = new SockJS("http://localhost:8080/ws");
    stomp.current = Stomp.over(socket);
    stomp.current.connect(
      {},
      connectSockJSCallBack,
      connectSockJSErrorCallback
    );
    window.stomp = stomp.current;
  }

  const connectSockJSCallBack = (frame) => {
    console.log("CallBack")
    setConnected(true);

    stomp.current.subscribe("/topic/greetings", (message) => {
      console.log(message)
      console.log(JSON.parse(message.body));
    });
    stomp.current.subscribe("/topic/levels", (message) => {
      const res = JSON.parse(message.body);
      setLevels(res.body.data);
    });

  }

  const connectSockJSErrorCallback = (error) => {
    console.log(error);
    connectSockJS();
  }


  useEffect(() => {
    if (initRef.current === true) return;
    if (stomp.current === null || !stomp.current.connected) {
      connectSockJS();
      initRef.current = true;
    }
  }, []);


  useEffect(() => {
    if (stomp.current !== null && stomp.current.connected) {
      console.log('Geting Levels')
      // get all levels
      stomp.current.send('/app/levels', JSON.stringify({}), {});
    }
  }, [connected])

  const [testref, settestref] = useState(0);
  useEffect(() => {
    console.log('SURE');

  }, [testref]);


  const [court, setCourt] = useState([null, null, null, null]);
  const [players, setPlayers] = useState(['陳X0', '陳X1', '陳X2', '陳X3', '陳X4', '陳X5', '陳X6', '陳X7']);

  const handleDragStart = (e, player) => {
    e.dataTransfer.setData('player', JSON.stringify(player));
  };

  const handleDragOver = (e, index) => {
    e.preventDefault();
  };

  const handleDrop = (e, index) => {
    e.preventDefault();
    const player = JSON.parse(e.dataTransfer.getData('player'));
    const newCourt = [...court];
    newCourt[index] = player;
    setCourt(newCourt);
  };

  const genCourt = (players, CourtNumber) => {
    return <>
      <Box
        display="flex"
        flexDirection="row"
        alignItems="center"
        justifyContent={"center"}
        sx={{ m: 3 }}
      >
        <Court players={players} CourtNumber={CourtNumber} sx={{ margin: 0 }}></Court>
      </Box>

    </>
  }

  const renderCourt = () => {
    let courts = [];

    for (let i = 0; i < courtCnt; i++) {
      courts.push(
        <Grid item key={i} xs={12} sm={6} md={4} lg={3}>
          {genCourt(players, "A")}
        </Grid>
      )
    }
    return courts;
  }

  return (
    <React.Fragment>
      <CssBaseline />
      <Container sx={{ width: '95%', marginTop: 2 }} >
        <DragDropContext onDragEnd={result => {
          console.log(result)
          const { source, destination, draggableId } = result;
          if (!destination) {
            return;
          }

          let arr = Array.from(players);
          const [remove] = arr.splice(source.index, 1);
          arr.splice(destination.index, 0, remove);
          setPlayers(arr);

        }}>
          <Typography variant="h3" noWrap>Badminon</Typography>
          <Grid container spacing={2} sx={{ minHeight: '80vh' }} >
            {renderCourt()}
          </Grid>


        </DragDropContext >

        <BadmintonRegister levels={levels}></BadmintonRegister>
      </Container>
    </React.Fragment >
  );
}