import React, { useRef, useState, useEffect } from "react";
import {
    Box,
    Container,
    Grid,
    Paper,
    Stack,
    Divider,
    Button,
    Typography,
} from "@mui/material";
import { green, grey } from "@mui/material/colors";
import SpeekButton from "./SpeekButton";
import { Draggable, DragDropContext, Droppable } from "react-beautiful-dnd";

const WatingArea = ({ players }) => {
    return (
        <>
            <Box display={"flex"} flexDirection={"row"} >
                {players.map((player) => (
                    <Box key={player.id} sx={{ m: 3 }} 
                        // 化成一個圓圈
                        width={100}
                        height={20}
                        bgcolor={green[500]}
                        >
                        {player}
                    </Box>
                ))}
            </Box>
        </>
    );
};

export default WatingArea;
