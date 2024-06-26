import React, { useRef, useState, useContext } from "react";

import { Box, Grid, Drawer, Toolbar, Container } from "@mui/material";
import { SimpleTreeView } from "@mui/x-tree-view/SimpleTreeView";
import { TreeItem } from "@mui/x-tree-view/TreeItem";
import { useNavigate, Outlet } from "react-router-dom";
import { makeStyles, useTheme } from "@mui/styles";
import { orange, blue, pink, yellow, grey } from "@mui/material/colors";

const useStyles = makeStyles((theme) => ({
    lable: {
        // css製作建立長方形，邊角圓弧
        border: "3px solid #f8f9fa",
    },
}));

const treeData = [
    {
        id: 1,
        itemId: "cos",
        label: "COS",
        subTree: [
            {
                itemId: "cdr",
                label: "CDR",
            },
            {
                itemId: "softphone",
                label: "Softphone",
            },
            {
                itemId: "dnd",
                label: "DND",
            },
            {
                itemId: "dialog",
                label: "Dialog",
            },
        ],
    },
    {
        id: 1,
        itemId: "Material UI",
        label: "Material UI",
        subTree: [
            {
                itemId: "autoComplete",
                label: "AutoComplete",
            },
        ],
    },
];

export const GalaxyTreeView = (props) => {
    const classes = useStyles();
    const navigate = useNavigate();
    const navigateHandler = (e) => {
        navigate(e.target.innerText);
    };

    return (
        <>
            <Drawer
                open={props.open}
                variant="permanent"
                sx={{
                    width: props.drawerWidth || 240,
                    flexShrink: 0,
                    [`& .MuiDrawer-paper`]: {
                        width: props.drawerWidth || 240,
                        boxSizing: "border-box",
                    },
                }}
                onClose={props.onClose}
                
                // anchor={props.anchor || "left"}
            >
                <Toolbar />
                <SimpleTreeView>
                    {treeData.map((tree) => (
                        <TreeItem
                            itemId={tree.itemId}
                            label={tree.label}
                            key={tree.itemId}
                        >
                            {tree.subTree.map((subTreeItem) => (
                                <TreeItem
                                    className={classes.lable}
                                    itemId={subTreeItem.itemId}
                                    label={subTreeItem.label}
                                    key={subTreeItem.itemId}
                                    onClick={navigateHandler}
                                ></TreeItem>
                            ))}
                        </TreeItem>
                    ))}
                </SimpleTreeView>
            </Drawer>
        </>
    );
};

export default GalaxyTreeView;
