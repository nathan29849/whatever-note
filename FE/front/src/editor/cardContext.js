import { createContext, useReducer, useContext } from "react";
import { createCard, editCard, removeCard } from "./api";

const CardStateContext = createContext(null);
const CardDispatchContext = createContext(null);

export function cardReducer(state, action) {
  switch (action.type) {
    case "SET_CARDS":
      return action.data;
    case "ADD_CARD":
      const newCard = { id: state.length, title: action.title };
      const createcardquery = { noteid: action.noteId, card: newCard };
      createCard(createcardquery);
      return [...state, newCard];
    case "EDIT_CARD":
      const editcardquery = {
        noteid: action.noteId,
        cardid: action.cardId,
        title: action.title,
      };
      editCard(editcardquery);
      return state.map((item) =>
        item.id === action.id ? { id: item.id, title: action.title } : item
      );
    case "REMOVE_CARD":
      const removecardquery = {
        noteid: action.noteId,
        cardid: action.cardId,
      };
      removeCard(removecardquery);
      return state.filter((item) => item.id !== action.cardId);

    default:
      return state;
  }
}

export function CardProvider({ children }) {
  const [state, dispatch] = useReducer(cardReducer, []);

  return (
    <CardStateContext.Provider value={state}>
      <CardDispatchContext.Provider value={dispatch}>
        {children}
      </CardDispatchContext.Provider>
    </CardStateContext.Provider>
  );
}

export function useCardState() {
  return useContext(CardStateContext);
}

export function useCardDispatch() {
  return useContext(CardDispatchContext);
}
