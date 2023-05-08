// drag and drop 기능 추가
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useNoteDispatch, useNoteState } from "../editor/noteContext";
import { useThemeState } from "../editor/themeContext";
import {
  NoteCollectionContainer,
  NoteContainer,
  NoteCover,
  NoteEditModeButton,
} from "../styled-component/noteStyle";
import NoteEditor from "./noteEditor";
import { NoteAddIndex } from "./noteExtraIndex";
import { app } from "../util/realm";

export default function NoteList() {
  // const [user, setUser] = useState(app.currentUser);
  const noteState = useNoteState();
  const notedispatch = useNoteDispatch();

  const theme = useThemeState();
  const navigate = useNavigate();

  const [currentEditing, setCurrentEditing] = useState({});

  const openNoteEditor = (id) => {
    const newEditList = { ...currentEditing };
    newEditList[id] = true;
    setCurrentEditing(newEditList);
    console.log(currentEditing);
  };

  const cancelNoteEditor = (id) => {
    if (currentEditing.hasOwnProperty(id) && currentEditing[id] === true) {
      const newEditList = { ...currentEditing };
      newEditList[id] = false;
      setCurrentEditing(newEditList);
      console.log(currentEditing);
    }
  };

  const openNote = (id) => {
    navigate(`/note/${id}`);
  };

  const getNoteList = async () => {
    try {
      const getUserNoteLists = await user.functions.getUserNoteList(
        app.currentUser.id
      );
      notedispatch({ type: "SET_NOTE_LIST", data: getUserNoteLists.data });
    } catch (err) {
      console.log(err);
    }
  };

  useEffect(() => {
    if (user) {
      getNoteList();
    }
  }, [user]);

  const showNoteList = () => {
    console.log(noteState);
  };

  if (noteState.length <= 0) {
    return (
      <>
        <NoteAddIndex />
        <NoteCollectionContainer theme={theme}>
          <NoteCover>노트없음</NoteCover>
        </NoteCollectionContainer>
      </>
    );
  }

  return (
    <>
      <NoteAddIndex />
      <NoteCollectionContainer theme={theme}>
        {noteState.map((nt, index) => (
          <NoteContainer
            style={{ display: "flex", justifyContent: "center" }}
            theme={theme}
            key={index}
          >
            {currentEditing.hasOwnProperty(nt.noteId) &&
            currentEditing[nt.noteId] === true ? (
              <>
                <NoteEditor
                  title={nt.noteTitle}
                  id={nt.noteId}
                  cancelNoteEditor={() => cancelNoteEditor(nt.noteId)}
                />
              </>
            ) : (
              <>
                <NoteCover
                  onClick={() => {
                    openNote(nt.noteId);
                  }}
                >
                  {nt.noteTitle}
                </NoteCover>
                <NoteEditModeButton onClick={() => openNoteEditor(nt.noteId)}>
                  edit mode
                </NoteEditModeButton>
              </>
            )}
          </NoteContainer>
        ))}
      </NoteCollectionContainer>
    </>
  );
}
