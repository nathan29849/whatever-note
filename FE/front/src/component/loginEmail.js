import { useEffect, useState } from "react";
import { EmailLoginContainer } from "../styled-component/loginStyle";
// import * as Realm from "realm-web";
// import { app } from "../util/realm";

export function LoginEmail() {
  const [userEmail, setUserEmail] = useState("");
  const [userPassword, setUserPassword] = useState("");
  // const [user, setUser] = useState(app.currentUser);

  // const sendUserRegisterInfo = async () => {
  //   await app.emailPasswordAuth.registerUser({
  //     email: userEmail,
  //     password: userPassword,
  //   });
  // };

  const handleEmailAdress = (e) => {
    setUserEmail(e.target.value);
  };

  const handlePassword = (e) => {
    setUserPassword(e.target.value);
  };

  // const userLogin = async () => {
  //   const joeCredentials = Realm.Credentials.emailPassword(
  //     userEmail,
  //     userPassword
  //   );

  //   try {
  //     const joe = await app.logIn(joeCredentials);
  //     // The active user is now Joe
  //     console.assert(joe.id === app.currentUser.id);
  //   } catch (err) {
  //     console.error("Failed to log in", err);
  //   }
  // };

  // const userLogOut = async () => {
  //   await app.removeUser(user);

  //   // The user is no longer the active user
  //   if (app.currentUser) {
  //     // The active user is now the logged in user (if there still is one) that was
  //     // most recently active
  //     console.assert(user.id !== app.currentUser.id);
  //   }
  //   // The user is no longer on the device
  //   console.assert(
  //     Object.values(app.allUsers).find(({ id }) => id === user.id) === undefined
  //   );
  // };

  useEffect(() => {}, []);

  return (
    <EmailLoginContainer>
      <p>login</p>
      <button>카카오톡 로그인</button>
      {/* {user.id ? <p>{user.id}</p> : <p>유저없음</p>}
      <input
        type="text"
        placeholder="이메일 주소 sample1234@example.com"
        value={userEmail}
        onChange={handleEmailAdress}
      />
      <input
        type="text"
        placeholder="비밀번호 sample1234"
        value={userPassword}
        onChange={handlePassword}
      />
      <button onClick={sendUserRegisterInfo}>회원가입</button>
      <button onClick={userLogin}>로그인</button>
      <button onClick={userLogOut}>로그아웃</button>
      <button
        onClick={() => {
          console.log(user);
        }}
      >
        user info
      </button> */}
    </EmailLoginContainer>
  );
}
