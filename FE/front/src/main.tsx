import React from "react";
import ReactDOM from "react-dom/client";
import "./styles/style.css";
import { App } from "./App";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import NoteList from "./components/NoteList";

const router = createBrowserRouter([
  {
    path: "/",
    element: <App />,
    children: [
      {
        path: "notelist",
        element: <NoteList />,
      },
    ],
  },
]);

ReactDOM.createRoot(document.getElementById("root") as HTMLElement).render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>
);
