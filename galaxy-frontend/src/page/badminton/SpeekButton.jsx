import { useSpeechSynthesis } from 'react-speech-kit';
import React from 'react';
import {Button} from '@mui/material';

export default function SpeekButton({text}) {
  const { speak } = useSpeechSynthesis();

  return (
    <div>
      <Button variant="contained"
        onClick={() => speak({ text: text })}>叫號</Button>
    </div>
  );
}