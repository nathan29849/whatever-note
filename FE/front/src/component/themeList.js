import { useThemeDispatch } from "../editor/themeContext";

export default function ThemeList() {
  const themeDispatch = useThemeDispatch();

  const turnToBeigeGreen = () => {
    themeDispatch({ type: "BEIGE_GREEN" });
  };

  const turnToPinkGradient = () => {
    themeDispatch({ type: "PINK_GRADIENT" });
  };

  const turnToYellowBlue = () => {
    themeDispatch({ type: "YELLOW_BLUE" });
  };

  return (
    <div>
      <p>색 테마 선택 가능</p>
      <button onClick={turnToBeigeGreen}>beigegreen</button>
      <button onClick={turnToPinkGradient}>pinkgradient</button>
      <button onClick={turnToYellowBlue}>yellowblue</button>
    </div>
  );
}
