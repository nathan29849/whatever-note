import { useNavigate } from "react-router-dom";

export default function IndexMenu() {
  const navigate = useNavigate();
  const theme = "black-white--";
  const className = {
    group: theme + "index-menu-group",
  };

  const goHome = () => {
    navigate("/");
  };

  const goNotes = () => {
    navigate("/notes");
  };

  return (
    <ul className={className.group}>
      <li>홈</li>
      <li>노트</li>
    </ul>
  );
}
