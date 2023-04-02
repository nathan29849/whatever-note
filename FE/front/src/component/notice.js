import { useEffect, useState } from "react";
import { useThemeState } from "../editor/themeContext";
import { NoticeContainer } from "../styled-component/noticeStyle";

export default function Notice() {
  const theme = useThemeState();
  const [notice, setNotice] = useState("");

  useEffect(() => {
    async function getNotice() {
      await fetch(
        "https://data.mongodb-api.com/app/note-ppfia/endpoint/noticepoint"
      )
        .then((res) => res.json())
        .then((res) => setNotice(res))
        .catch((err) => console.log(err));
    }
    getNotice();
  }, []);

  return <NoticeContainer theme={theme}>{notice}</NoticeContainer>;
}
