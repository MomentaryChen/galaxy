import logo from "./logo.svg";
import "./App.css";

import { useEffect, useState } from "react";
import { ThemeProvider, createTheme } from "@mui/material/styles";
import CssBaseline from "@mui/material/CssBaseline";
import Layout from "./layout/Layout";
import { Outlet, Router } from "react-router-dom";
import AppContext from "./AppContext";
import UserContext, { _userInfo } from "./UserContext";

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
    const [functionMenuOpen, setFunctionMenuOpen] = useState(false);

    /*
        0: 無登入
        1: 登入成功
    */
    const [authStatus, setAuthStatus] = useState(0);
    const [userInfo, setUserInfo] = useState(_userInfo);
    const [userToken, setUserToken] = useState(null);

    // Auto Login
    useEffect(() => {
        const __token = sessionStorage.getItem("GALAXY_TOKEN");
        if (!__token) {
            setAuthStatus(0);
            return;
        }
        setAuthStatus(1);

        const __userInfo = sessionStorage.getItem("userInfo");
        setUserInfo(JSON.parse(__userInfo));
    }, []);

    return (
        <ThemeProvider theme={theme}>
            <div className="App">
                <CssBaseline />
                <UserContext.Provider
                    value={{ authStatus, setAuthStatus, userInfo, setUserInfo, userToken, setUserToken }}
                >
                    <AppContext.Provider
                        value={{
                            menuOpen,
                            setMenuOpen,
                            functionMenuOpen,
                            setFunctionMenuOpen,
                        }}
                    >
                        <Layout>
                            <Outlet />
                        </Layout>
                    </AppContext.Provider>
                </UserContext.Provider>
            </div>
        </ThemeProvider>
    );
}

export default App;
