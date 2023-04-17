import { useState, useEffect } from "react";

const whateverURL = `https://kauth.kakao.com/oauth/authorize?client_id=${process.env.NEXT_PUBLIC_KAKAO_CLIENT_ID}&redirect_uri=${process.env.NEXT_PUBLIC_KAKAO_REDIRECT_URL}&response_type=code`;

export default function HomePage() {
  const handleKakaoLogin = () => {
    window.location.href = whateverURL;
  };

  const handleGithubLogin = () => {
    window.location.href = githubURL;
  };

  return (
    <div>
      <h1>아무단어장</h1>
      <h2>카카오 로그인</h2>

      <img
        style={{ cursor: "pointer" }}
        onClick={() => {
          handleKakaoLogin();
        }}
        src="/images/kakao_login_medium_narrow.png"
        alt="Kakao Login"
      />

      <h2>깃헙 로그인</h2>
      <img
        style={{ cursor: "pointer" }}
        onClick={() => {
          handleGithubLogin();
        }}
        width="30px"
        height="30px"
        src="/images/github_login.png"
        alt="Github Login"
      />
    </div>
  );
}
