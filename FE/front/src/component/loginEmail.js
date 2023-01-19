import { useState } from "react";
import { EmailLoginContainer } from "../styled-component/loginStyle";
import { app } from "../util/realm";

export function LoginEmail() {
  const [userEmail, setUserEmail] = useState("");
  const [userPassword, setUserPassword] = useState("");

  const sendUserRegisterInfo = async () => {
    await app.emailPasswordAuth.registerUser({
      email: userEmail,
      userPassword: userPassword,
    });
  };

  const handleEmailAdress = (e) => {
    setUserEmail(e.target.value);
  };

  const handlePassword = (e) => {
    setUserPassword(e.target.value);
  };

  return (
    <EmailLoginContainer>
      <p>login</p>
      <input
        type="text"
        placeholder="이메일 주소"
        value={userEmail}
        onChange={handleEmailAdress}
      />
      <input
        type="text"
        placeholder="비밀번호"
        value={userPassword}
        onChange={handlePassword}
      />
      <button onClick={sendUserRegisterInfo}>회원가입</button>
      <button>로그인</button>
    </EmailLoginContainer>
  );
}
