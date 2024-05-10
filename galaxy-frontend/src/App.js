import logo from "./logo.svg";
import "./App.css";

import { useState } from "react";
import { ThemeProvider, createTheme } from "@mui/material/styles";
import CssBaseline from "@mui/material/CssBaseline";
import Layout from "./layout/Layout";
import { Outlet, Router } from "react-router-dom";
import AppContext from "./AppContext";

const theme = createTheme({
    palette: {
        primary: {
            main: "#FF8E15",
        },
        secondary: {
            main: "#6C757D",
        },
        background: {
            //default: "#FF00FFFF"
            default: "#F8F9FA",
        },
        text: {
            primary: "#070707",
            secondary: "#6C757D",
        },
        info: {
            main: "#fff",
        },
    },
    typography: {
        fontFamily: "roboto",
        fontWeightLight: 100,
        fontWeightRegular: 400,
        fontWeightMedium: 500,
        fontWeightBold: 900,
    },
});

function App() {
    const [menuOpen, setMenuOpen] = useState(false);
    return (
        <ThemeProvider theme={theme}>
            <div className="App">
                <CssBaseline />
                <AppContext.Provider value={{ menuOpen, setMenuOpen }}>
                    <Layout>
                        <Outlet />
                    </Layout>
                </AppContext.Provider>
            </div>
        </ThemeProvider>
    );
}

export default App;
