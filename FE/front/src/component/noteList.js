// drag and drop 기능 추가
import { useRef, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useNoteDispatch, useNoteState } from "../editor/noteContext";
import { useThemeState } from "../editor/themeContext";
import {
  NoteCollectionContainer,
  NoteContainer,
  NoteCover,
  NoteEditDoneButton,
  NoteEditMode,
  NoteEditModeButton,
} from "../styled-component/noteStyle";
import Note from "./note";
import { NoteAddIndex } from "./noteExtraIndex";

export default function NoteList() {
  const noteState = useNoteState();
  const noteDispatch = useNoteDispatch();

  const theme = useThemeState();
  const navigate = useNavigate();
  const dragRef = useRef();
  const dragOverRef = useRef();

  const [currentEditing, setCurrentEditing] = useState({});

  const startDrag = (e, position) => {
    dragRef.current = position;
  };

  const enterDrag = (e, position) => {
    dragOverRef.current = position;
  };

  const drop = (e) => {
    const shallowCopyNoteList = [...noteState];
    const dragedContent = shallowCopyNoteList[dragRef.current];
    shallowCopyNoteList.splice(dragRef.current, 1);
    shallowCopyNoteList.splice(dragOverRef.current, 0, dragedContent);

    dragRef.current = null;
    dragOverRef.current = null;

    noteDispatch({
      type: "EDIT_NOTE_POSITION",
      newNoteOrder: shallowCopyNoteList,
    });
  };

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

  if (noteState.length <= 0) {
    return <p>노트리스트 없음</p>;
  }

  return (
    <>
      <NoteAddIndex />
      <NoteCollectionContainer theme={theme}>
        {noteState.map((nt, index) => (
          <NoteContainer
            style={{ display: "flex", justifyContent: "center" }}
            theme={theme}
            key={nt.id}
            onDragStart={(e) => startDrag(e, index)}
            onDragEnter={(e) => enterDrag(e, index)}
            onDragEnd={(e) => drop(e)}
            draggable
          >
            {currentEditing.hasOwnProperty(nt.id) &&
            currentEditing[nt.id] === true ? (
              <>
                <Note
                  title={nt.title}
                  id={nt.id}
                  seq={nt.seq}
                  cancelNoteEditor={() => cancelNoteEditor(nt.id)}
                />
              </>
            ) : (
              <>
                <NoteCover
                  onClick={() => {
                    openNote(nt.id);
                  }}
                >
                  {nt.title}
                </NoteCover>
                <NoteEditModeButton onClick={() => openNoteEditor(nt.id)}>
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
