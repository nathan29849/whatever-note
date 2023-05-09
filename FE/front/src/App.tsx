import { useEffect } from "react";

import { Outlet, useNavigate } from "react-router-dom";

import { app } from "./util/realm";

import IndexMenu from "./components/IndexMenu";
import LoginEmail from "./components/LoginEmail";

function App() {
  // const [user, setUser] = useState(app.currentUser);
  const user = app.currentUser;
  const navigate = useNavigate();

  useEffect(() => {
    if (user !== null && user.hasOwnProperty("_accessToken")) {
      navigate("/notelist");
    }
  }, []);

  return (
    <div className="app">
      <p>아무단어장 리팩토링</p>
      <IndexMenu />
      {user ? <Outlet /> : <LoginEmail />}
    </div>
  );
}

export { App };
