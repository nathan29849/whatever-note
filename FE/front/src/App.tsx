import { useEffect, useState } from "react";
// react router 공식문서에서 redirect 사용을 권유하나 redirect 작동안함 이유모름
import { Outlet, redirect, useNavigate } from "react-router-dom";
import LoginEmail from "./components/LoginEmail";
import { app } from "./util/realm";

function App() {
  const [user, setUser] = useState(app.currentUser);
  const navigate = useNavigate();

  useEffect(() => {
    console.log(user);
    if (user) {
      console.log("notelist");
      // redirect("/notelist");
      navigate("/notelist");
    }
  }, []);

  return (
    <>
      <h1>아무단어장</h1>
      {user ? <Outlet /> : <LoginEmail />}
    </>
  );
}

export default App;
