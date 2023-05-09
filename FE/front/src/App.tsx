"use client";

import { useEffect } from "react";

import { app } from "./util/realm";

function App() {
  // const [user, setUser] = useState(app.currentUser);
  const user = app.currentUser;

  useEffect(() => {
    console.log("next test");
    console.log(user);
  }, []);

  return (
    <div className="app">
      <p>아무단어장 리팩토링</p>
    </div>
  );
}

export { App };
