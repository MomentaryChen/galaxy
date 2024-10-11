import logo from "./logo.svg";
import "./App.css";

import { useEffect, useState } from "react";
import { ThemeProvider, createTheme } from "@mui/material/styles";
import CssBaseline from "@mui/material/CssBaseline";
import Layout from "./layout/Layout";
import { Outlet, Router } from "react-router-dom";
import AppContext from "./AppContext";
import UserContext, { _userInfo } from "./UserContext";
import axios from "axios";

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

    useEffect(() => {
        initAxios();
    }, [authStatus]);

    // Auto Login
    useEffect(() => {
        const __token = sessionStorage.getItem("GALAXY_TOKEN");
        if (!__token) {
            setAuthStatus(0);
            return;
        }
        setUserToken(__token);
        setAuthStatus(1);
        const __userInfo = sessionStorage.getItem("userInfo");
        setUserInfo(JSON.parse(__userInfo));
    }, []);

    const initAxios = async () => {
        // Add a request interceptor
        console.log("Init Axios")
        axios.interceptors.request.use(
            function (config) {
                // console.log("request OK intercept: " + JSON.stringify(config))
                //統一設置 GALAXY_API_TOKEN header
                config.headers["GALAXY_API_TOKEN"] = userInfo.token;
                //統一設置 Timeout 時間
                config.timeout = 60000;
                return config;
            },
            function (error) {
                console.log("request ERR intercept: " + JSON.stringify(error));
                return Promise.reject(error);
            }
        );

        // Add a response interceptor
        axios.interceptors.response.use(
            function (response) {
                return response;
            },
            function (error) {
                console.log("response ERR intercept: " + JSON.stringify(error));
                if (
                    error.message &&
                    error.message.indexOf("Network Error") !== -1
                ) {
                    setAuthStatus(3);
                }
                return Promise.reject(error);
            }
        );
    };

    return (
        <ThemeProvider theme={theme}>
            <div className="App">
                <CssBaseline />
                <UserContext.Provider
                    value={{
                        authStatus,
                        setAuthStatus,
                        userInfo,
                        setUserInfo,
                        userToken,
                        setUserToken,
                    }}
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
