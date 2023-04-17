import { useState, useEffect } from "react";
import { useRouter } from "next/router";

export default function CallBack() {
  const router = useRouter();
  const [loginData, setLoginData] = useState(null);
  const code = router.query.code;

  return <div>로그인성공</div>;
}
