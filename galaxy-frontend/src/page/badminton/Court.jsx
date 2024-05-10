import React, { useRef, useState, useEffect } from 'react';
import { Box, Container, Grid, Paper, Stack, Divider, Button, Typography } from '@mui/material';
import { green, grey } from '@mui/material/colors';
import SpeekButton from './SpeekButton';
import { Draggable, DragDropContext, Droppable } from "react-beautiful-dnd";

function Court({ CourtName, players }) {

  const speakRef = useRef();

  const location = (name, idx) => {
    return <>
      <Draggable draggableId={name} index={idx} key={name}>
        {(provided) => (
          <div
            {...provided.draggableProps}
            {...provided.dragHandleProps}
            ref={provided.innerRef}
            key={name}
          >
            <Box 
              display={'flex'}
              alignItems="center"
              justifyContent={"center"}
              sx={{
                backgroundColor: green[500],
                border: "3px solid white",
                textAlign: "center",
                width: {
                  xs: 120,
                  sx: 120,
                  md: 140,
                  lg: 120,
                },
                height: 120,
              }}
            >{name}</Box>
          </div>
        )}
      </Draggable>
    </>
  }


  return (
    <>
      
      <Droppable droppableId="drop-id">
        {(provided) => (
          <div ref={provided.innerRef} {...provided.droppableProps}>
            <Typography variant="h6" noWrap>Court: {CourtName}</Typography> 
            <Box
              alignItems="center"
              justifyContent={"center"}
              sx={{
                backgroundColor: green[500],
                border: "2px solid white",
                padding: "10px",
                margin: "5px",
              }}>


              <Box
                display="flex"
                flexDirection="row"
                alignItems="center"
                justifyContent={"center"}
                fullWidth

                sx={{
                  backgroundColor: green[500],
                }}
              >
                {location(players[0], 0)}
                {location(players[1], 1)}

              </Box>



              <Divider component='div' light textAlign='center' sx={{ backgroundColor: grey[100] }} />

              <Box
                display="flex"
                flexDirection="row"
                alignItems="center"
                justifyContent={"center"}
                fullWidth
                mt={1}
                sx={{
                  backgroundColor: green[500],
                }}
              >
                {location(players[2], 2)}
                {location(players[3], 3)}
              </Box>
              <Stack sx={{ marginTop: 1 }}>
                <SpeekButton ref={speakRef} text={`請以下四位選手上場, ${players.join(' ')}`}></SpeekButton>
              </Stack>
            </Box>
          </div>
        )
        }

      </Droppable >


    </>
  )
}

export default Court;