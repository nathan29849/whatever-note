import { createBrowserRouter, RouterProvider } from "react-router-dom";
import Home from "./component/home";
import NoteInsider from "./component/noteInsider";
import ErrorPage from "./component/errorPage";
import ThemeList from "./component/themeList";
import NoteList from "./component/noteList";
import UserInfo from "./component/userInfo";
import { LoginEmail } from "./component/loginEmail";

function App() {
  const router = createBrowserRouter([
    {
      path: "/",
      element: <Home />,
      errorElement: <ErrorPage />,
      children: [
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
          path: "userInfo",
          element: <LoginEmail />,
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
