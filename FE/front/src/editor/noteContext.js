import { createContext, useReducer, useContext, useEffect } from "react";
import { getNoteListFromNathan, removeNote } from "./api";

const NoteStateContext = createContext(null);
const NoteDispatchContext = createContext(null);

function noteReducer(state, action) {
  switch (action.type) {
    case "SET_NOTE_LIST":
      console.log(action.data);
      return action.data;
    case "ADD_NOTE":
      const numForId = Math.floor(Math.random() * 10) + state.length * 10;
      const newNoteId = numForId.toString();
      const newNote = {
        id: newNoteId,
        seq: action.seq,
        title: action.title,
      };
      return [...state, newNote];
    case "EDIT_NOTE_NAME":
      const editedNoteData = {
        noteId: action.id,
        seq: action.seq,
        title: action.title,
      };
      return state.map((item) =>
        item.noteId === action.id ? editedNoteData : item
      );
    case "REMOVE_NOTE":
      removeNote({ id: action.id });
      return state.filter((item) => item.noteId !== action.id);
    case "EDIT_NOTE_POSITION":
      return action.newNoteOrder;
    default:
      return state;
  }
}

export function NoteProvider({ children }) {
  const [state, dispatch] = useReducer(noteReducer, []);

  useEffect(() => {
    getNoteListFromNathan(dispatch);
  }, []);

  return (
    <NoteStateContext.Provider value={state}>
      <NoteDispatchContext.Provider value={dispatch}>
        {children}
      </NoteDispatchContext.Provider>
    </NoteStateContext.Provider>
  );
}

export function useNoteState() {
  return useContext(NoteStateContext);
}

export function useNoteDispatch() {
  return useContext(NoteDispatchContext);
}
