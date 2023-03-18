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

const NoteStateContext = createContext(null);
const NoteDispatchContext = createContext(null);

function noteReducer(state, action) {
  switch (action.type) {
    case "SET_NOTE_LIST":
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
    case "EDIT_NOTE_POSITION":
      return action.newNoteOrder;
    default:
      return state;
  }
}

export function NoteProvider({ children }) {
  const [state, dispatch] = useReducer(noteReducer, []);

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
