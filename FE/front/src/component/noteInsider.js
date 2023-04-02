import { useParams } from "react-router-dom";
import { useNoteState } from "../editor/noteContext";
import {
  NoteInsideContainer,
  NoteTitleContainer,
} from "../styled-component/noteStyle";
import { CardList } from "./cardList";
import { useThemeState } from "../editor/themeContext";
import { useEffect, useState } from "react";
import { CardProvider } from "../editor/cardContext";
import { CardAddIndex } from "./cardExtraIndex";

export default function NoteInsider() {
  const param = useParams();
  const theme = useThemeState();
  const [noteTitle, setNoteTitle] = useState("제목");
  const noteId = param.id;
  const convertedNoteId = parseInt(noteId);

  const noteState = useNoteState();

  const findNoteTitle = noteState.find(
    (item) => item.id === convertedNoteId
  ).title;

  useEffect(() => {
    setNoteTitle(findNoteTitle);
  }, []);

  return (
    <CardProvider>
      <NoteInsideContainer theme={theme}>
        <CardAddIndex />
        <NoteTitleContainer theme={theme}>{noteTitle}</NoteTitleContainer>
        <CardList />
      </NoteInsideContainer>
    </CardProvider>
  );
}
