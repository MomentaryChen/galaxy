import * as React from "react";
import {
    Grid,
    Box,
    Card,
    CardHeader,
    CardContent,
    Avatar,
    IconButton,
    Typography,
    CardActions,
    Button,
    TextField,
    Checkbox,
    Link,
    Tooltip,
} from "@mui/material";
import { red } from "@mui/material/colors";
import MoreVertIcon from "@mui/icons-material/MoreVert";
import { makeStyles, useTheme } from "@mui/styles";
import { execLogin } from "./GalazyLoginApi";
import UserContext from "../UserContext";

const useStyles = makeStyles((theme) => ({
    cardTitle: {
        background: theme.palette.primary.main,
    },
    forget: {
        marginRight: 1,
    },
    register: {
        marginLeft: 1,
    },
}));

const GalaxyLogin = () => {
    const classes = useStyles();

    const {setAuthStatus, userInfo, setUserInfo, setUserToken} = React.useContext(UserContext);

    const [username, setUsername] = React.useState("");
    const [password, setPassword] = React.useState("");

    const remember = () => {
        return (
            <>
                <Box
                    sx={{
                        display: "flex",
                        alignItems: "center",
                        pl: 1,
                        pb: 1,
                    }}
                >
                    <Checkbox
                        {...{
                            inputProps: { "aria-label": "Checkbox demo" },
                        }}
                    />
                    <Typography>記住我</Typography>
                </Box>
            </>
        );
    };

    const login = async () => {
        // Validate
        if (username === "" || password === "") {
            return;
        }
        console.log(username, password);
        const data = await execLogin(username, password);
        console.log(data)

        if(data.token) {
            setAuthStatus(1);
            setUserToken(data.token);
            sessionStorage.setItem("GALAXY_TOKEN", data.token);
        }

        if(data.userInfo) {
            sessionStorage.setItem("userInfo", JSON.stringify(data.userInfo));
            setUserInfo(data.userInfo);
        }
    };

    const loginButton = () => {
        return (
            <>
                <Box
                    sx={{
                        display: "flex",
                        pl: 1,
                        pb: 1,
                        flexDirection: "column",
                    }}
                >
                    <Button
                        variant="contained"
                        onClick={login}
                    >
                        登入
                    </Button>
                </Box>
            </>
        );
    };

    const forgetPasswordLable = () => {
        return (
            <>
                <Grid container>
                    <Grid item xs={6}>
                        <Link href="#">忘記密碼</Link>
                    </Grid>
                    <Grid item xs={6}>
                        <Link href="#">註冊</Link>
                    </Grid>
                </Grid>
            </>
        );
    };

    const copyright = () => {
        return (
            <>
                <Box
                    sx={{
                        display: "flex",
                        alignItems: "center",
                        pl: 1,
                        pb: 1,
                        flexDirection: "column",
                    }}
                >
                    <Typography>Copyright © Galaxy 2024.</Typography>
                </Box>
            </>
        );
    };

    return (
        <>
            <Box
                display={"flex"}
                flexDirection="column"
                alignItems="center"
                justifyContent="center"
                minHeight="100vh"
            >
                <Card sx={{ maxWidth: 500 }}>
                    <CardHeader
                        title="登入Galaxy系統"
                        subheader=""
                        className={classes.cardTitle}
                    />
                    <CardContent>
                        <TextField
                            label="用戶名"
                            variant="outlined"
                            fullWidth
                            margin="normal"
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                            required
                        />
                        <TextField
                            label="密碼"
                            type="password"
                            variant="outlined"
                            fullWidth
                            margin="normal"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            onKeyDown={(e) => {
                                if (e.key === "Enter") login();
                            }}
                            required
                        />
                    </CardContent>
                    {remember()}
                    {loginButton()}
                    {forgetPasswordLable()}

                    {copyright()}
                </Card>
            </Box>
        </>
    );
};

export default GalaxyLogin;
