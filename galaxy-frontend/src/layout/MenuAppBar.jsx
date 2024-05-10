import {
    Box, Container, Grid, Button, AppBar, Toolbar, IconButton, Typography, MenuItem, Menu
    , Tooltip, Avatar, Hidden
} from '@mui/material';
import React, { useState } from 'react';
import MenuIcon from '@mui/icons-material/Menu';
import AdbIcon from '@mui/icons-material/Adb';
import { useNavigate } from "react-router-dom";
import { page } from '../data/pageData';

const pages = Object.values(page).map((p) => p.name);
const settings = ['Profile', 'Account', 'Dashboard', 'Logout'];

const MenuAppBar = (props) => {
    const navigate = useNavigate();

    const [anchorElNav, setAnchorElNav] = React.useState(true);
    const [anchorElUser, setAnchorElUser] = React.useState(false);

    const handleOpenNavMenu = (event) => {
        setAnchorElNav(event.currentTarget);
    };
    const handleOpenUserMenu = (event) => {
        setAnchorElUser(event.currentTarget);
    };

    const handleCloseNavMenu = (e) => {
        setAnchorElNav(null);
        navigate(e.target.innerText)
    };

    const handleCloseUserMenu = () => {
        setAnchorElUser(null);
    };

    const renderIconButton = () => {
        return (<>
            <Hidden smUp >
                <IconButton
                    size="large"
                    edge="start"
                    color="inherit"
                    aria-label="menu"
                    onClick={props.MenuIconClick}
                >
                    <MenuIcon />
                </IconButton>
            </Hidden>
            <Typography
                variant="h5"
                noWrap
                component="a"
                href="/"
                align="center"

                sx={{
                    mr: 2,
                    display: { xs: 'block', md: 'flex' },
                    // fontFamily: 'monospace',
                    fontWeight: 700,
                    // letterSpacing: '.3rem',
                    color: 'inherit',
                    textDecoration: 'none',
                }}
            >
                Demo
            </Typography>
        </>
        )
    }

    const renderAppBarNavigator = () => {
        return (
            <>
                <Box sx={{ flexGrow: 1, display: { xs: 'flex' } }}
                    display="flex"
                    justifyContent="left"
                    alignItems="left"
                    flexDirection="row"
                    flexWrap="wrap"
                    m={1}
                >
                    <Hidden smDown >
                        {pages.map((page) => (
                            <MenuItem key={page} onClick={handleCloseNavMenu}>
                                <Typography textAlign="center">{page}</Typography>
                            </MenuItem>
                        ))}
                    </Hidden>
                </Box>

            </>
        )
    }

    const renderProfile = () => {
        return <>
            <Box sx={{}}
                display="flex"
                justifyContent="right"
                alignItems="right"
                flexDirection="row"
                flexWrap="wrap"
                m={1}
            >

                <Tooltip title="Open settings">
                    <IconButton onClick={handleOpenUserMenu} sx={{ p: 0 }}>
                        <Avatar alt="Remy Sharp" src="/static/images/avatar/2.jpg" />
                    </IconButton>
                </Tooltip>
                <Menu
                    sx={{ mt: '45px' }}
                    id="menu-appbar"
                    anchorEl={anchorElUser}
                    anchorOrigin={{
                        vertical: 'top',
                        horizontal: 'right',
                    }}
                    keepMounted
                    transformOrigin={{
                        vertical: 'top',
                        horizontal: 'right',
                    }}
                    open={Boolean(anchorElUser)}
                    onClose={handleCloseUserMenu}
                >
                    {settings.map((setting) => (
                        <MenuItem key={setting} onClick={handleCloseUserMenu}>
                            <Typography textAlign="center">{setting}</Typography>
                        </MenuItem>
                    ))}
                </Menu>
            </Box>
        </>
    }

    return (<>
        <Box sx={{ flexGrow: 1 }}  >
            <AppBar position="fixed" sx={{ zIndex: (theme) => theme.zIndex.drawer + 1 }}>
                <Toolbar>
                    {renderIconButton()}
                    {renderAppBarNavigator()}
                    {renderProfile()}
                </Toolbar>
            </AppBar>
        </Box>

    </>)
}

export default MenuAppBar;