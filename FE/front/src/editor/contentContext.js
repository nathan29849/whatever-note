import { createContext, useReducer, useContext } from "react";

const ContentStateContext = createContext(null);
const ContentDispatchContext = createContext(null);

export function contentReducer(state, action) {
  switch (action.type) {
    case "SET_CONTENTS":
      console.log({
        noteId: action.noteId,
        cardId: action.cardId,
        content: action.data,
      });
      return {
        noteId: action.noteId,
        cardId: action.cardId,
        content: action.data,
      };

    case "ADD_CONTENT":
      const content = [
        ...state.content,
        { id: 0, info: action.newContent, isImage: false, seq: 1000 },
      ];
      const newState = { ...state, content };
      console.log(content);
      console.log(newState);
      return newState;
    // const newContent = {
    //   id: 0,
    //   info: action.newContent,
    //   isImage: false,
    //   seq: 1000,
    // };
    // const newList = state.content.push(newContent);

    // return { ...state, content: newList };

    case "EDIT_CONTENT":
      const editedContent = {
        id: action.contentId,
        info: action.contentInfo,
        isImage: false,
        seq: 1000,
      };
      const newEditedContentList = state.content.map((item) =>
        item.id === action.contentId ? editedContent : item
      );
      const newEditedState = { ...state, content: newEditedContentList };
      return newEditedState;

    case "REMOVE_CONTENT":
      const target = state.content.findIndex(
        (item) => item.id === action.contentId
      );
      return state.content.splice(target, 1);

    default:
      break;
  }
}

export function ContentProvider({ children }) {
  const [state, dispatch] = useReducer(contentReducer, []);

  return (
    <ContentStateContext.Provider value={state}>
      <ContentDispatchContext.Provider value={dispatch}>
        {children}
      </ContentDispatchContext.Provider>
    </ContentStateContext.Provider>
  );
}

export function useContentState() {
  return useContext(ContentStateContext);
}

export function useContentDispatch() {
  return useContext(ContentDispatchContext);
}
