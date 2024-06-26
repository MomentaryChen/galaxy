import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import App from "./App";
import reportWebVitals from "./reportWebVitals";
import {
    createBrowserRouter,
    RouterProvider,
    // Route,
} from "react-router-dom";

import FunctionPage from "./page/function/FunctionPage";
import CDR from "./page/cdr/CDR";
import ErrorPage from "./page/error-page/ErrorPage";
import SoftPhone from "./page/cos/SoftPhone";
import BadmintonDashboard from "./page/badminton/BadmintonDashboard";
import CosAutoComplete from "./page/autoComplete/CosAutoComplete";
import { page } from "./data/pageData";
import DND from "./page/dnd/DND";
import GalaxyDialog from "./compoment/GalaxyDialog";

const root = ReactDOM.createRoot(document.getElementById("root"));

const router = createBrowserRouter([
    {
        path: "/",
        element: <App />,
        children: [
            {
                path: page["function"].path,
                element: <FunctionPage />,
                errorElement: <ErrorPage />,
                children: [
                    {
                        path: page.function.children.cdr.path,
                        element: <CDR />,
                        errorElement: <ErrorPage />,
                    },

                    {
                        path: page.function.children.softphone.path,
                        element: <SoftPhone />,
                        errorElement: <ErrorPage />,
                    },
                    {
                        path: page.function.children.autoComplete.path,
                        element: <CosAutoComplete />,
                        errorElement: <ErrorPage />,
                    }
                ],
            },

            {
                path: page["badminton"].path,
                element: <BadmintonDashboard />,
                errorElement: <ErrorPage />,
            },
        ],
        errorElement: <ErrorPage />,
    },
]);

root.render(<RouterProvider router={router} />);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
