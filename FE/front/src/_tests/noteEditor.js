import { useState } from "react";
import {
  NoteEditDoneButton,
  NoteEditInput,
  NoteRemoveButton,
} from "../styled-component/noteStyle";
import { useNoteDispatch } from "../editor/noteContext";

export default function NoteEditor(props) {
  const noteId = props.id;
  const noteTitle = props.title;
  const cancelNoteEditor = props.cancelNoteEditor;
  const noteDispatch = useNoteDispatch();
  const [newNoteTitle, setNewNoteName] = useState(noteTitle);

  const handleChange = (event) => {
    setNewNoteName(event.target.value);
  };

  const editDone = () => {
    if (newNoteTitle.length > 0) {
      noteDispatch({
        type: "EDIT_NOTE_NAME",
        noteTitle: newNoteTitle,
        noteId: noteId,
      });
    } else {
      return;
    }
  };

  const removeNoteButton = () => {
    noteDispatch({ type: "REMOVE_NOTE", noteId: noteId });
  };

  return (
    <>
      <NoteEditInput type="text" onChange={handleChange} value={newNoteTitle} />
      <NoteEditDoneButton onClick={editDone}>edit done</NoteEditDoneButton>
      <NoteEditDoneButton onClick={cancelNoteEditor}>
        edit cancel
      </NoteEditDoneButton>
      <NoteRemoveButton onClick={removeNoteButton}>remove</NoteRemoveButton>
    </>
  );
}
