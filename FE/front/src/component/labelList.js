import { useNavigate } from "react-router-dom";
import {
  LabelContainer,
  LabelListContainer,
} from "../styled-component/labelStyle";
import { useThemeState } from "../editor/themeContext";

export default function LabelList() {
  // const navigate = useNavigate();
  const theme = useThemeState();

  // const goHome = () => {
  //   navigate("/");
  // };

  // const goNotes = () => {
  //   navigate("/notes");
  // };

  // const goTheme = () => {
  //   navigate("/theme");
  // };

  // const goUserInfo = () => {
  //   navigate("/login");
  // };

  return (
    <LabelListContainer>
      <LabelContainer theme={theme}>Home</LabelContainer>
      <LabelContainer theme={theme}>Notes</LabelContainer>
      <LabelContainer theme={theme}>login</LabelContainer>
      <LabelContainer theme={theme}>Theme</LabelContainer>
    </LabelListContainer>
  );
}
