import { createContext, useContext, useReducer } from "react";

const ThemeStateContext = createContext(null);
const ThemeDispatchContext = createContext(null);

export const theme = {};

function themeReducer(state, action) {
  switch (action.type) {
    case "BEIGE_GREEN":
      return "beige-green";
    case "PINK_GRADIENT":
      return "pink-gradient";
    case "YELLOW_BLUE":
      return "yellow-blue'";
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
