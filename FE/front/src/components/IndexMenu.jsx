import { useNavigate } from "react-router-dom";

export default function IndexMenu() {
  const navigate = useNavigate();
  const theme = "black-white--";
  const className = theme + "index-menu";

  const goHome = () => {
    navigate("/");
  };

  const goNotes = () => {
    navigate("/notes");
  };

  return (
    <ul className={className}>
      <li>Home</li>
      <li>Notes</li>
    </ul>
  );
}
