import * as React from "react";
import Button from "@mui/material/Button";
import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import DialogContentText from "@mui/material/DialogContentText";
import DialogTitle from "@mui/material/DialogTitle";

import useMediaQuery from "@mui/material/useMediaQuery";
import { useTheme, styled } from "@mui/material/styles";

const PrimaryActionButton = styled(Button)(({ theme }) => ({
    // color: "",
    fontSize: "17px",
    fontWeight: "bold",
    height: "48px",
    borderRadius: "9px",
}));

const SecondaryActionButton = styled(Button)(({ theme }) => ({
    // color: "#000",
    // backgroundColor: "#FFF",
    fontSize: "17px",
    fontWeight: "bold",
    height: "48px",
    borderRadius: "9px",
    // border: "1px solid #828282",
    // "&:focus": {
    //   color: "#000",
    //   backgroundColor: "#FFF",
    // },
}));

const GalaxyDialog = ({
    showDialog,
    setShowDialog,
    title,
    message,
    actionLabel,
    actionHandler,
    secondaryActionLabel,
    secondaryActionHandler,
    closeHandler,
}) => {
    const theme = useTheme();
    const fullScreen = useMediaQuery(theme.breakpoints.down("xl"));

    const handleClose = () => {
        // setShowDialog(false);
        // if (closeHandler) closeHandler();
    };

    return (
        <>
            <Dialog
                // fullScreen={fullScreen}
                fullWidth
                open={showDialog}
                onClose={handleClose}
                aria-labelledby="alert-dialog-title"
                aria-describedby="alert-dialog-description"
                maxWidth={"md"}
            >
                {title && (
                    <DialogTitle id="alert-dialog-title">{title}</DialogTitle>
                )}
                <DialogContent dividers>
                    {/* <DialogContentText id="alert-dialog-description"> */}
                    {message}
                    {/* </DialogContentText> */}
                </DialogContent>
                <DialogActions>
                    {secondaryActionLabel && (
                        <SecondaryActionButton onClick={secondaryActionHandler}>
                            {secondaryActionLabel}
                        </SecondaryActionButton>
                    )}
                    {actionLabel && (
                        <PrimaryActionButton onClick={actionHandler} autoFocus>
                            {actionLabel}
                        </PrimaryActionButton>
                    )}
                </DialogActions>
            </Dialog>
        </>
    );
};

export default GalaxyDialog;
