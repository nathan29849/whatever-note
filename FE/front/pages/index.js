import { useState } from "react";
import axios from "axios";
// import { useSWR } from "swr";

// import "../src/styles/style.css";
// import LabelList from "../src/component/labelList";
// import KakaoLogin from "../src/component/logInKakao";

const fetcher = (url) => axios.get(url).then((res) => res.data);

function HomePage() {
  // const { data, error } = useSWR("/api/data", fetcher);
  const [user, setUser] = useState(null);

  const kakaoLogin = () => {
    // 카카오로그인
  };

  return (
    <div>
      <h1>카카오 로그인 테스트</h1>

      <div>
        <img src="/images/kakao_login_medium_narrow.png" alt="Kakao Login" />
      </div>
    </div>
  );
}

export default HomePage;
