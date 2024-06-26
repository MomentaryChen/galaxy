import React from 'react';
import { Drawer, Box, List, ListItem, ListItemText, CssBaseline, AppBar, Toolbar, Collapse, Hidden } from '@mui/material';
import ExpandMore from '@mui/icons-material/ExpandMore';
import StarBorder from '@mui/icons-material/StarBorder';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemButton from '@mui/material/ListItemButton';
import InboxIcon from '@mui/icons-material/MoveToInbox';
import ExpandLess from '@mui/icons-material/ExpandLess';

import { useNavigate } from "react-router-dom";

const drawerWidth = 240;


const NavMenu = (props) => {
    const [open, setOpen] = React.useState(true);
    const navigate = useNavigate();
    const handleClick = () => {
        setOpen(!open);
    };

    const navigateHandler = (e) => {
        navigate(e.target.innerText)
    };

    return (
        <Hidden smUp >
            <Box sx={{ display: 'flex' }}>
                <Drawer
                    variant="permanent"
                    sx={{
                        width: drawerWidth,
                        flexShrink: 0,
                        [`& .MuiDrawer-paper`]: { width: drawerWidth, boxSizing: 'border-box' },
                    }}
                    open={props.open}
                >
                    <Toolbar />
                    <Box sx={{ overflow: 'auto' }}>
                        <List>
                            {props.pages.map((text, index) => (

                                <ListItemButton key={text} onClick={navigateHandler}>
                                    <ListItemIcon >
                                        <InboxIcon />
                                    </ListItemIcon>
                                    <ListItemText primary={text} />
                                </ListItemButton>
                            ))}
                            <ListItemButton onClick={handleClick}>
                                <ListItemIcon>
                                    <InboxIcon />
                                </ListItemIcon>
                                <ListItemText primary="Inbox" />
                                {open ? <ExpandLess /> : <ExpandMore />}
                            </ListItemButton>
                            <Collapse in={open} timeout="auto" unmountOnExit>
                                <List omponent="div" disablePadding>
                                    {['world', 'test', 'node'].map((text, index) => (
                                        <>
                                            <ListItem button key={text} sx={{ pl: 4 }}>
                                                <ListItemIcon>
                                                    <StarBorder />
                                                </ListItemIcon>
                                                <ListItemText primary={text} />
                                            </ListItem>
                                        </>

                                    ))}
                                </List>
                            </Collapse>
                        </List>
                    </Box>
                </Drawer >
            </Box >
        </Hidden>
    );
};

export default NavMenu;