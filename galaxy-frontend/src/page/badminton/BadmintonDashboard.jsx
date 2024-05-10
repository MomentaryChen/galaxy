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
  const [stomp, setStomp] = useState();
  const [newResponse, setNewResponse] = useState();
  const [responses, setResponses] = useState([])
  const [connected, setConnected] = useState(false)

  const [courtCnt, setCourtCnt] = useState(8);

  useEffect(() => {
    const socket = new SockJS("http://localhost:8080/ws");
    const stompClient = Stomp.over(socket);
    stompClient.connect(
      {},
      frame => {
        setConnected(true)
        stompClient.subscribe("/topic/greetings", (message) => {
          console.log(JSON.parse(message.body));
        });
      },
      error => {
        console.log(error);
      }
    );
    window.stomp = stompClient;
    setStomp(stompClient)
  }, [])

  const send = () => {
    stomp.send('/app/hello', JSON.stringify({ name: "Victor" }), {});
  }

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
        sx={{m: 3}}
      >
        <Court players={players} CourtNumber={CourtNumber} sx={{ margin: 0 }}></Court>
      </Box>

    </>
  }

  const renderCourt = () => {
    let courts = [];
    for (let i = 0; i < courtCnt; i++) {
      courts.push(
        <Grid item   xs={12} sm={6} md={4} lg={3}>
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

        <BadmintonRegister></BadmintonRegister>
      </Container>
    </React.Fragment >
  );
}