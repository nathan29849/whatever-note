// import { useEffect, useState } from "react";

// import { Outlet, useNavigate } from "react-router-dom";

// import { app } from "./util/realm";

// import IndexMenu from "./components/IndexMenu";
// import LoginEmail from "./components/LoginEmail";

// function App() {
//   const [user, setUser] = useState(null);
//   const storage = app.currentUser;
//   const navigate = useNavigate();

//   useEffect(() => {
//     if (storage !== null && storage.hasOwnProperty("_accessToken")) {
//       setUser(storage);
//       navigate("/notelist");
//     }
//   }, []);

//   return (
//     <div className="app">
//       <p>아무단어장 리팩토링</p>
//       <IndexMenu />
//       {user ? <Outlet /> : <LoginEmail />}
//     </div>
//   );
// }

function App() {
  return <div>app</div>;
}

export { App };
