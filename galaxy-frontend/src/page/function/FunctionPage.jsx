import React, { useRef, useState, useContext } from "react";
import { useRouteError } from "react-router-dom";

import { GalaxyTreeView } from "../../compoment/index";
import { Box, Grid, Drawer, Toolbar, Container, Button } from "@mui/material";

import { useNavigate, Outlet } from "react-router-dom";
import { makeStyles, useTheme } from "@mui/styles";
import useMediaQuery from "@mui/material/useMediaQuery";
import AppContext from "../../AppContext";

export default function FunctionPage() {
    const error = useRouteError();

    const appContext = useContext(AppContext);
    const mdMediaUp = useMediaQuery((theme) => theme.breakpoints.up("md"));

    const onClose = () => {
        appContext.setFunctionMenuOpen(!appContext.functionMenuOpen)
    }
   

    return (
        <>
            <Grid container>
                <Grid item xs={mdMediaUp ? 3 : 12}>
                    {<GalaxyTreeView anchor={mdMediaUp? "left" : "top"} 
                        drawerWidth={mdMediaUp? 240 : "wh"}
                        open={appContext.functionMenuOpen}
                        onClose = {onClose}/>}
                </Grid>
                <Grid item xs={mdMediaUp ? 9 : 12}>
                    <Container sx={{ width: "95%", marginTop: 2 }}>
                        {/* <Button onClick={onClose}> TEST</Button> */}
                        <Outlet />
                    </Container>
                </Grid>
            </Grid>
        </>
    );
}
