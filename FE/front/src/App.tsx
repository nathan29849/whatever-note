import { useEffect, useState } from "react";

import { Outlet, useNavigate } from "react-router-dom";

import { app } from "./util/realm";

import IndexMenu from "./components/IndexMenu";
import LoginEmail from "./components/LoginEmail";

function App() {
  // const [user, setUser] = useState(app.currentUser);
  const user = app.currentUser;
  const navigate = useNavigate();

  useEffect(() => {
    console.log(user);
    if (user) {
      // redirect("/notelist");
      navigate("/notelist");
    }
  }, []);

  return (
    <>
      <p>아무단어장 리팩토링</p>
      <IndexMenu />
      {user ? <Outlet /> : <LoginEmail />}
    </>
  );
}

export default App;
