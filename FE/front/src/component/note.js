import { useState } from "react";
import {
  NoteEditDoneButton,
  NoteEditInput,
  NoteRemoveButton,
} from "../styled-component/noteStyle";
import { useNoteDispatch } from "../editor/noteContext";

export default function Note(props) {
  const noteId = props.id;
  const noteSeq = props.seq;
  const noteTitle = props.title;
  const cancelNoteEditor = props.cancelNoteEditor;
  const noteDispatch = useNoteDispatch();
  const [editing, setEditing] = useState(true);
  const [newNoteName, setNewNoteName] = useState(noteTitle);

  const handleChange = (event) => {
    setNewNoteName(event.target.value);
  };

  const editDone = () => {
    if (newNoteName.length > 0) {
      noteDispatch({
        type: "EDIT_NOTE_NAME",
        title: newNoteName,
        id: props.id,
        seq: props.seq,
      });
    } else {
      return;
    }
  };

  const removeNoteButton = () => {
    noteDispatch({ type: "REMOVE_NOTE", id: noteId });
  };

  return (
    <>
      <NoteEditInput
        type="text"
        name="note"
        onChange={handleChange}
        value={newNoteName}
        editing={editing}
      />
      <NoteEditDoneButton
        editing={editing}
        onClick={() => {
          editDone();
          cancelNoteEditor();
        }}
      >
        edit done
      </NoteEditDoneButton>
      <NoteEditDoneButton editing={editing} onClick={cancelNoteEditor}>
        edit cancel
      </NoteEditDoneButton>
      <NoteRemoveButton editing={editing} onClick={removeNoteButton}>
        remove
      </NoteRemoveButton>
    </>
  );
}
