import React, { useRef, useState } from 'react';
import { Draggable, DragDropContext, Droppable } from "react-beautiful-dnd";
import { Button, Box } from '@mui/material';

const DND = (props) => {
    const [items, setItems] = useState(["A", "B", "C"]);

    return (
        <DragDropContext onDragEnd={result => {
            const { source, destination, draggableId } = result;
            if (!destination) {
                return;
            }

            let arr = Array.from(items);
            const [remove] = arr.splice(source.index, 1);
            arr.splice(destination.index, 0, remove);
            setItems(arr);

        }}>
            <h1>Todo</h1>
            <Droppable droppableId="drop-id">
                {(provided) => (
                    <div ref={provided.innerRef} {...provided.droppableProps}>
                        {items.map((item, i) => (
                            <Draggable draggableId={item} index={i} key={item}>
                                {(provided) => (
                                    <div
                                        {...provided.draggableProps}
                                        {...provided.dragHandleProps}
                                        ref={provided.innerRef}
                                        key={item}
                                    >
                                        {item}
                                    </div>
                                )}
                            </Draggable>

                        ))}
                        {provided.placeholder}
                    </div>
                )}
            </Droppable>
        </DragDropContext>
    )
}

export default DND;