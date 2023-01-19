import { useNavigate } from "react-router-dom";
import {
  LabelContainer,
  LabelListContainer,
} from "../styled-component/labelStyle";
import { useThemeState } from "../editor/themeContext";

export default function LabelList() {
  const navigate = useNavigate();
  const theme = useThemeState();

  const goHome = () => {
    navigate("/");
  };

  const goNotes = () => {
    navigate("/notes");
  };

  const goTheme = () => {
    navigate("/theme");
  };

  const goUserInfo = () => {
    navigate("/userInfo");
  };

  return (
    <LabelListContainer>
      <LabelContainer theme={theme} onClick={goHome}>
        Home
      </LabelContainer>
      <LabelContainer theme={theme} onClick={goNotes}>
        Notes
      </LabelContainer>
      <LabelContainer theme={theme} onClick={goUserInfo}>
        userInfo
      </LabelContainer>
      {/* <LabelContainer theme={theme} onClick={goTheme}>
        Theme
      </LabelContainer> */}
    </LabelListContainer>
  );
}
