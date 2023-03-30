import React from "react";
import LabelList from "../src/component/labelList";
import KakaoLogin from "../src/component/logInKakao";

const HomePage = () => {
  return (
    <div>
      <h1>Hello, World!</h1>
      <LabelList />
      <div>
        <KakaoLogin />
      </div>
    </div>
  );
};

export default HomePage;
