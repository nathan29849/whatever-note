import { createContext, useContext, useReducer } from "react";

const ThemeStateContext = createContext(null);
const ThemeDispatchContext = createContext(null);

export const theme = {
  bodyColor: "#e9edc9",
  backgroundColor: "#fffdee",
  labelColor: "#ffffff",
  noteColor: "#e9edc9",
  noteEditColor: "#CCD5AE",
  fontColor: "#463F3A",
};

function themeReducer(state, action) {
  switch (action.type) {
    case "BEIGE_GREEN":
      return {
        bodyColor: "#e9edc9",
        backgroundColor: "#fffdee",
        labelColor: "#ffffff",
        noteColor: "#e9edc9",
        noteEditColor: "#CCD5AE",
        fontColor: "#463F3A",
      };
    case "PINK_GRADIENT":
      return {
        bodyColor: "#fff3f5",
        backgroundColor: "#ffc0cb",
        labelColor: "#ffc0cb",
        noteColor: "#ffffff",
        noteEditColor: "#FFC2D1",
        fontColor: "#fb6f92",
      };
    case "YELLOW_BLUE":
      return {
        bodyColor: "#c4ebf1",
        backgroundColor: "#ffffc2",
        labelColor: "#ffffc2",
        noteColor: "#ffffff",
        noteEditColor: "#CCD5AE",
        fontColor: "#000000",
      };
    default:
      break;
  }
}

export function ThemeProvider({ children }) {
  const [state, dispatch] = useReducer(themeReducer, theme);

  return (
    <ThemeStateContext.Provider value={state}>
      <ThemeDispatchContext.Provider value={dispatch}>
        {children}
      </ThemeDispatchContext.Provider>
    </ThemeStateContext.Provider>
  );
}

export function useThemeState() {
  return useContext(ThemeStateContext);
}

export function useThemeDispatch() {
  return useContext(ThemeDispatchContext);
}
