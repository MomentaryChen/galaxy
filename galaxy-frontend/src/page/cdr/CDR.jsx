import React, { useRef, useState } from 'react';
import AudioPlayer from 'react-audio-player';
import axios from 'axios';
import { Box, Container, Grid, Paper, Stack, Divider, Button, Typography } from '@mui/material';

const CDR = (props) => {

    const [CDRSource, setCDRSource] = useState('');
    const audioRef = useRef();
    const audioRef1 = useRef();

    const getAudio = async () => {
        axios.get('http://localhost:9000/cdr/audio', { responseType: 'arraybuffer' })
            .then((res) => {
                console.log(res);
                if (res.status === 200) {
                    const url = window.URL.createObjectURL(new Blob([res.data], { type: 'audio/mp3' }));
                    console.log(url);
                    setCDRSource(url);
                }
            })
    }

    const renderAudio = () => {
        return <>


        </>
    }

    return <>
        <Box
            display="flex"
            flexDirection="column"
            alignItems="center"
            justifyContent={"center"}
            >
            <AudioPlayer
                src={CDRSource}
                controls // 顯示音頻控制面板
                controlsList="nodownload"
            />
            <Button onClick={getAudio}>Click Me</Button>
        </Box>
    </>

}

export default CDR;