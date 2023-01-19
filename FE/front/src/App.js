import { createBrowserRouter, RouterProvider } from "react-router-dom";
import Home from "./component/home";
import NoteInsider from "./component/noteInsider";
import ErrorPage from "./component/errorPage";
import ThemeList from "./component/themeList";
import NoteList from "./component/noteList";
import LoginEmailConfirm from "./component/loginEmailConfirm";

function App() {
  const router = createBrowserRouter([
    {
      path: "/",
      element: <Home />,
      errorElement: <ErrorPage />,
      children: [
        {
          path: "userInfo",
          element: <LoginEmailConfirm />,
        },
        {
          path: "notes",
          element: <NoteList />,
        },
        {
          path: "note/:id",
          element: <NoteInsider />,
        },
        {
          path: "theme",
          element: <ThemeList />,
        },
        {
          path: "loginemailconfirm",
          element: <LoginEmailConfirm />,
        },
      ],
    },
  ]);
  return (
    <>
      <RouterProvider router={router} />
    </>
  );
}

export default App;
