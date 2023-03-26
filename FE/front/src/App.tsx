import "./App.css";
import "./styles/style.css";
import Header from "./component/Header";
import { Outlet } from "react-router-dom";
import Home from "./component/Home";

function App() {
  return (
    <>
      <Header />
      <Outlet />
    </>
  );
}

export default App;
