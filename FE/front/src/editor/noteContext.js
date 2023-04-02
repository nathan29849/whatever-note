<<<<<<< HEAD
import { createContext, useReducer, useContext } from "react";
import { app } from "../util/realm";

const createUserNote = (noteTitle) => {
  app.currentUser.functions.createUserNote({
    userId: app.currentUser.id,
    noteTitle: noteTitle,
  });
};
const editUserNote = (data) => {
  app.currentUser.functions.editUserNote({
    userId: app.currentUser.id,
    noteId: data.noteId,
    noteTitle: data.noteTitle,
  });
};
const removeUserNote = (data) => {
  app.currentUser.functions.deleteUserCardPack({
    userId: app.currentUser.id,
    noteId: data.noteId,
  });
};
=======
import { createContext, useReducer, useContext, useEffect } from "react";
import { getNoteListFromNathan, removeNote } from "./api";
>>>>>>> ffc5dd9558301474b037acf9a6845a1e05edcb38

const NoteStateContext = createContext(null);
const NoteDispatchContext = createContext(null);

function noteReducer(state, action) {
  switch (action.type) {
    case "SET_NOTE_LIST":
<<<<<<< HEAD
      return action.data;
    case "ADD_NOTE":
      const newNoteIdForm = new Date();
      const newNoteId = newNoteIdForm
        .toString()
        .split(" ")
        .join("")
        .slice(3, 20);
      createUserNote(action.title);
      return [
        ...state,
        {
          noteTitle: action.title,
          noteId: newNoteId,
        },
      ];
    case "EDIT_NOTE_NAME":
      editUserNote({ noteId: action.noteId, noteTitle: action.noteTitle });
      return state.map((item) =>
        item.noteId === action.noteId
          ? {
              noteId: action.noteId,
              noteTitle: action.noteTitle,
            }
          : item
      );
    case "REMOVE_NOTE":
      console.log(action.noteId);
      removeUserNote({ noteId: action.noteId });
      return state.filter((item) => item.noteId !== action.noteId);
=======
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
>>>>>>> ffc5dd9558301474b037acf9a6845a1e05edcb38
    case "EDIT_NOTE_POSITION":
      return action.newNoteOrder;
    default:
      return state;
  }
}

export function NoteProvider({ children }) {
  const [state, dispatch] = useReducer(noteReducer, []);

<<<<<<< HEAD
=======
  useEffect(() => {
    getNoteListFromNathan(dispatch);
  }, []);

>>>>>>> ffc5dd9558301474b037acf9a6845a1e05edcb38
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
