import React from "react";
import ReactDOM from "react-dom/client";
import { createBrowserRouter } from "react-router";
import { RouterProvider } from "react-router/dom";
import Home from "./pages/home"
import "./index.css"
import Leaderboard from "./pages/Leaderboard";
import GenerateMission from "./pages/GenerateMission";
import RegisterKill from "./pages/RegisterKill";

const router = createBrowserRouter([
  {
    path: "/",
    element: <Home/>,
  },
  {
    path: "/leaderboard",
    element: <Leaderboard/>,
  },
  {
    path: "/assign",
    element: <GenerateMission/>,
  },
  {
    path: "/register",
    element: <RegisterKill/>,
  }
]);

const root = document.getElementById("root");

ReactDOM.createRoot(root).render(
  <RouterProvider router={router} />,
);
