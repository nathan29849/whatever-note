import { useState } from "react";

export default function KakaoLogin() {
  const [emailLogin, setEmailLogin] = useState(false);

  const openEmailLogin = () => {
    setEmailLogin(true);
  };

  return (
    <div>
      <button>카카오로그인</button>
      <button onClick={openEmailLogin}>이메일로 가입하기</button>
      {emailLogin ? (
        <div>
          <p>email login</p>
          <input type="text" />
          <button>가입메일 전송</button>
        </div>
      ) : (
        <></>
      )}
    </div>
  );
}
