import { useState } from "react";
import { Outlet } from "react-router-dom";
import LoginEmail from "./components/LoginEmail";
import { app } from "./util/realm";

function App() {
  const [user, setUser] = useState(app.currentUser);

  return (
    <>
      <h1>아무단어장</h1>
      {user ? (
        <button
          onClick={() => {
            console.log(user);
          }}
        >
          user
        </button>
      ) : (
        <LoginEmail />
      )}
      <Outlet />
    </>
  );
}

export default App;
