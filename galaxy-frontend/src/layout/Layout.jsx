import {
    Box, Container, Grid, Button, Toolbar, IconButton, Typography, MenuItem, Menu
    , Tooltip, Avatar
} from '@mui/material';
import React, { useState } from 'react';
import MenuAppBar from './MenuAppBar';
import NavMenu from './NavMenu';
import AppContext from '../AppContext'
import UserContext from '../UserContext'
import GalaxyLogin from '../compoment/GalaxyLogin';

const Layout = ({ children }) => {
    const appContext = React.useContext(AppContext);
    const userContext = React.useContext(UserContext);

    if (userContext.authStatus === 0) {
        return <>
            <GalaxyLogin />
        </>
    }
    else if(userContext.authStatus === 1) {
        return (<>

            <Grid container >
                <Grid item xs={12} style={{ height: '30px'}}>
                    <MenuAppBar MenuIconClick={() => appContext.setMenuOpen(!appContext.menuOpen)}/>
            </Grid>
            {appContext.menuOpen ?
                <Grid item xs={2} style={{  overflow: 'auto', marginTop: '35px' }}>
                    <NavMenu open={appContext.menuOpen} ></NavMenu>
                </Grid>
                : null
            }
            <Grid item xs={appContext.menuOpen ? 10 : 12} 
                style={{ height: '92vh', overflow: 'auto', marginTop: '35px',  }} >
                {children}
            </Grid>
        </Grid>


    </>)
    }                 
}

export default Layout;