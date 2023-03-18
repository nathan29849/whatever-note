import { createContext, useContext, useReducer } from "react";

const StickerStateContext = createContext(null);
const StickerDispatchContext = createContext(null);

export const sticker = {
  stickerName: "sticker",
  stickerPosition: { x: 0, y: 0 },
};

function stickerReducer(state, action) {
  switch (action.type) {
    case "SET_NEW_STICKER":
      return {
        stickerName: "sticker",
        stickerPosition: action.stickerPosition,
      };
    default:
      break;
  }
}

export function StickerProvider({ children }) {
  const [state, dispatch] = useReducer(stickerReducer, sticker);

  return (
    <StickerStateContext.Provider value={state}>
      <StickerDispatchContext.Provider value={dispatch}>
        {children}
      </StickerDispatchContext.Provider>
    </StickerStateContext.Provider>
  );
}

export function useStickerState() {
  return useContext(StickerStateContext);
}

export function useStickerDispatch() {
  return useContext(StickerDispatchContext);
}
