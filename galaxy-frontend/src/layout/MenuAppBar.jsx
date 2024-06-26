import {
    Box,
    Container,
    Grid,
    Button,
    AppBar,
    Toolbar,
    IconButton,
    Typography,
    MenuItem,
    Menu,
    Tooltip,
    Avatar,
    Hidden,
} from "@mui/material";
import React, { useState, useContext, useEffect } from "react";
import MenuIcon from "@mui/icons-material/Menu";
import AdbIcon from "@mui/icons-material/Adb";
import { useNavigate } from "react-router-dom";
import UserContext from "../UserContext";

const settings = ["Profile", "Account", "Dashboard", "Logout"];

const MenuAppBar = ({ pages, menuIconClick }) => {
    const navigate = useNavigate();

    const { setAuthStatus, userInfo } = useContext(UserContext);

    const [menu, setMenu ] = useState([]);

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
        navigate(e.target.innerText);
    };

    const handleCloseUserMenu = (event, name) => {
        setAnchorElUser(null);
        switch (event.target.innerText) {
            case "Profile":
                navigate("/profile");
                break;
            case "Account":
                navigate("/account");
                break;
            case "Dashboard":
                navigate("/dashboard");
                break;
            case "Logout":
                sessionStorage.clear("GALAXY_TOKEN");
                setAuthStatus(0);
                navigate("/");
                break;
            default:
                break;
        }
    };

    const renderIconButton = () => {
        return (
            <>
                <Hidden smUp>
                    <IconButton
                        size="large"
                        edge="start"
                        color="inherit"
                        aria-label="menu"
                        onClick={menuIconClick}
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
                        display: { xs: "block", md: "flex" },
                        // fontFamily: 'monospace',
                        fontWeight: 700,
                        // letterSpacing: '.3rem',
                        color: "inherit",
                        textDecoration: "none",
                    }}
                >
                    Demo
                </Typography>
            </>
        );
    };

    useEffect(() => {
        const names = pages.map((page) => page.name);
        setMenu([...new Set(names)]);
        console.log([...new Set(names)]);
    }, [pages, setMenu]);

    const renderAppBarNavigator = () => {
        return (
            <>
                <Box
                    sx={{ flexGrow: 1, display: { xs: "flex" } }}
                    display="flex"
                    justifyContent="left"
                    alignItems="left"
                    flexDirection="row"
                    flexWrap="wrap"
                    m={1}
                >
                    <Hidden smDown>
                        {menu &&
                            menu.map((page) => {
                                return (
                                    <MenuItem
                                        key={page}
                                        onClick={handleCloseNavMenu}
                                    >
                                        <Typography textAlign="center">
                                            {page}
                                        </Typography>
                                    </MenuItem>
                                );
                            })}
                    </Hidden>
                </Box>
            </>
        );
    };

    const renderProfile = () => {
        return (
            <>
                <Box
                    sx={{}}
                    display="flex"
                    justifyContent="center"
                    alignItems="center"
                    flexDirection="row"
                    flexWrap="wrap"
                    m={1}
                >
                    <Typography textAlign="center" m={1}>
                        {userInfo.userName}
                    </Typography>
                    <Tooltip title="Open settings">
                        <IconButton onClick={handleOpenUserMenu} sx={{ p: 0 }}>
                            <Avatar
                                alt="Remy Sharp"
                                src="/static/images/avatar/2.jpg"
                            />
                        </IconButton>
                    </Tooltip>
                    <Menu
                        sx={{ mt: "45px" }}
                        id="menu-appbar"
                        anchorEl={anchorElUser}
                        anchorOrigin={{
                            vertical: "top",
                            horizontal: "right",
                        }}
                        keepMounted
                        transformOrigin={{
                            vertical: "top",
                            horizontal: "right",
                        }}
                        open={Boolean(anchorElUser)}
                        onClose={handleCloseUserMenu}
                    >
                        {settings.map((setting) => (
                            <MenuItem
                                key={setting}
                                onClick={handleCloseUserMenu}
                            >
                                <Typography textAlign="center">
                                    {setting}
                                </Typography>
                            </MenuItem>
                        ))}
                    </Menu>
                </Box>
            </>
        );
    };

    return (
        <>
            <Box sx={{ flexGrow: 1 }}>
                <AppBar
                    position="fixed"
                    sx={{ zIndex: (theme) => theme.zIndex.drawer + 1 }}
                >
                    <Toolbar>
                        {renderIconButton()}
                        {renderAppBarNavigator()}
                        {renderProfile()}
                    </Toolbar>
                </AppBar>
            </Box>
        </>
    );
};

export default MenuAppBar;
