import { useEffect } from "react";
import {
  useNavigate,
  BrowserRouter,
  BrowserRouter as Routes,
  Route,
} from "react-router-dom";

type FlagListProps = {
  name: string;
};

FlagList.defaultProps = {
  name: "",
};

export default function FlagList({ name }: FlagListProps) {
  const navigate = useNavigate();

  const goHome = () => {
    navigate("/");
  };

  const goNotes = () => {
    navigate("/notes");
  };

  // const goTheme = () => {
  //   navigate("/theme");
  // };

  useEffect(() => {
    console.log("flags");
  }, []);

  return (
    <nav>
      <ul>
        <li onClick={goHome}>Home</li>
        <li onClick={goNotes}>Notes</li>
        <li>Theme</li>
      </ul>
    </nav>
  );
}
