import { createContext, useReducer, useContext } from "react";
<<<<<<< HEAD
import { app } from "../util/realm";

const createUserContent = (data) => {
  app.currentUser.functions.createUserContent({
    userId: app.currentUser.id,
    noteId: data.noteId,
    cardId: data.cardId,
    contentId: data.contentId,
    contentInfo: data.contentInfo,
    isImage: data.isImage,
  });
};
=======
>>>>>>> ffc5dd9558301474b037acf9a6845a1e05edcb38

const ContentStateContext = createContext(null);
const ContentDispatchContext = createContext(null);

export function contentReducer(state, action) {
  switch (action.type) {
    case "SET_CONTENTS":
<<<<<<< HEAD
      return action.data;
    case "ADD_CONTENT":
      const plaincontentidform = new Date();

      const plaincontentId = plaincontentidform
        .toString()
        .split(" ")
        .join("")
        .slice(3, 20);
      createUserContent({
        noteId: action.noteId,
        cardId: action.cardId,
        contentId: plaincontentId,
        contentInfo: action.contentInfo,
        isImage: false,
      });
      return [
        ...state,
        {
          contentId: plaincontentId,
          contentInfo: action.contentInfo,
          isImage: false,
        },
      ];
    case "ADD_IMAGE_CONTENT":
      const imagecontentidform = new Date();
      const imagecontentId = imagecontentidform
        .toString()
        .split(" ")
        .join("")
        .slice(3, 20);
      createUserContent({
        noteId: action.noteId,
        cardId: action.cardId,
        contentId: imagecontentId,
        contentInfo: action.contentInfo,
        isImage: true,
      });

      return [
        ...state,
        {
          contentId: imagecontentId,
          contentInfo: action.contentInfo,
          isImage: true,
        },
      ];
=======
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

>>>>>>> ffc5dd9558301474b037acf9a6845a1e05edcb38
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
