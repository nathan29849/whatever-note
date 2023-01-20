import { useEffect, useState } from "react";
import { Outlet, useNavigate, useParams } from "react-router-dom";
import { NoteProvider } from "../editor/noteContext";
import LabelList from "./labelList";
import { HomeContainer } from "../styled-component/homeStyle";
import { GlobalStyle } from "../styled-component/globalStyle";
import { ThemeProvider, useThemeState } from "../editor/themeContext";
import { NoteAddIndex } from "./noteExtraIndex";
import { LoginEmail } from "./loginEmail";
import * as Realm from "realm-web";
import { app } from "../util/realm";

function Login({ setUser }) {
  const loginAnonymous = async () => {
    const user = await app.logIn(Realm.Credentials.anonymous());
    setUser(user);
  };
  return <button onClick={loginAnonymous}>Log In</button>;
}

function UserDetail({ user }) {
  return (
    <div>
      <h1>Logged in with anonymous id: {user.id}</h1>
    </div>
  );
}

function HomeInsider() {
  const theme = useThemeState();
  const navigate = useNavigate();
  const [user, setUser] = useState(app.currentUser);

  useEffect(() => {
    if (user) {
      navigate("/notes");
    }
  }, [user]);

  return (
    <>
      <NoteAddIndex />
      <HomeContainer theme={theme}>
        <GlobalStyle theme={theme} />
        <LabelList />
        {user.id ? <Outlet /> : <LoginEmail />}
      </HomeContainer>
    </>
  );
}

export default function Home() {
  return (
    <ThemeProvider>
      <NoteProvider>
        <HomeInsider />
      </NoteProvider>
    </ThemeProvider>
  );
}
